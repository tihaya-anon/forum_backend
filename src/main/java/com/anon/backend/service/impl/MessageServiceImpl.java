package com.anon.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anon.backend.common.CustomException;
import com.anon.backend.common.constant.CURD;
import com.anon.backend.common.constant.StatusCodeEnum;
import com.anon.backend.common.req.PageReq;
import com.anon.backend.entity.User;
import com.anon.backend.entity.Message;
import com.anon.backend.map.MessageMap;
import com.anon.backend.mapper.MessageMapper;
import com.anon.backend.model.message.MessageSendModel;
import com.anon.backend.dto.message.MessageHistoryDto;
import com.anon.backend.dto.message.MessageReceiveDto;
import com.anon.backend.security.SymmetricKeyGen;
import com.anon.backend.security.util.PublicKeyUtil;
import com.anon.backend.security.util.SymmetricKeyUtil;
import com.anon.backend.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anon.backend.service.IUserService;
import com.anon.backend.service.util.DBOperation;
import com.anon.backend.service.util.PageOperation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Service Implement
 *
 * @author anon
 * @since 2024-10-22
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements IMessageService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final IUserService userService;

  MessageServiceImpl(IUserService userService) {
    this.userService = userService;
  }

  @Override
  public void create(@NotNull MessageSendModel dto) {
    getRecipient(dto.getSender(), dto.getReceiver());
    Message message = MessageMap.INSTANCE.sendDto2Message(dto);
    DBOperation.performWithCheck(logger, CURD.CREATE, () -> this.save(message));
  }

  @Override
  public List<MessageReceiveDto> read(int id, boolean all, PageReq pageReq) {
    QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("receiver", id);
    if (!all) {
      queryWrapper.eq("is_read", 0);
    }
    queryWrapper.orderByDesc("create_at");
    List<Message> messageList = PageOperation.paginate(logger, pageReq, queryWrapper, baseMapper);
    if (messageList.isEmpty()) {
      return null;
    }
    List<MessageReceiveDto> messageReceiveVoList =
        messageList.stream()
            .map(MessageMap.INSTANCE::messageVo2receiveDto)
            .collect(Collectors.toCollection(ArrayList::new));
    Collections.reverse(messageReceiveVoList);
    return messageReceiveVoList;
  }

  @Override
  public List<MessageHistoryDto> history(int id, int with, PageReq pageReq) {
    Page<Message> page = new Page<>(pageReq.getPageIdx(), pageReq.getPageSize());
    List<Message> messageList = baseMapper.readRecipientMessage(page, id, with).getRecords();
    if (messageList == null || messageList.isEmpty()) {
      return null;
    }
    List<MessageHistoryDto> messageHistoryVoList;
    messageHistoryVoList =
        messageList.stream()
            .map(MessageMap.INSTANCE::messageVo2historyDto)
            .sorted(Comparator.comparing(MessageHistoryDto::getCreateAt))
            .toList();
    return messageHistoryVoList;
  }

  @Override
  public String readRecipientKey(int id, int to) {
    User[] recipient = getRecipient(id, to);
    User userA = recipient[0];
    User userB = recipient[1];
    int idA = userA.getId();
    int idB = userB.getId();
    String keyA;
    String keyB;
    Map<String, Object> key =
        DBOperation.perform(logger, CURD.READ, () -> baseMapper.readRecipientKey(idA, idB));

    if (key == null || key.isEmpty()) {
      try {
        SecretKey secretKey = SymmetricKeyGen.get();
        keyA = encodeSecretKeyByUser(secretKey, userA);
        keyB = encodeSecretKeyByUser(secretKey, userB);
        System.out.println(keyA.length());
        DBOperation.performWithCheck(
            logger, CURD.CREATE, () -> baseMapper.createRecipientKey(idA, idB, keyA, keyB) > 0);
      } catch (Exception e) {
        logger.error("Exception during insert recipient({}, {}) key: {}", idA, idB, e.getMessage());
        throw new CustomException(StatusCodeEnum.SYSTEM_ERROR);
      }
    } else {
      keyA = (String) key.get("key_a");
      keyB = (String) key.get("key_b");
    }

    if (idA == id) {
      return keyA;
    }
    return keyB;
  }

  private static String encodeSecretKeyByUser(SecretKey secretKey, @NotNull User user)
      throws Exception {
    PublicKey publicKey = PublicKeyUtil.decodePubKey(user.getPubKey());
    byte[] encodedSecretKey = SymmetricKeyUtil.encodeByPubKey(secretKey, publicKey);
    return SymmetricKeyUtil.encodeSymKey(encodedSecretKey);
  }

  @Contract("_, _ -> new")
  private User @NotNull [] getRecipient(int userAId, int userBId) {
    int tmpId;
    if (userAId > userBId) {
      tmpId = userAId;
      userAId = userBId;
      userBId = tmpId;
    }
    User userA = userService.getById(userAId);
    if (userA == null) {
      throw new CustomException(StatusCodeEnum.USER_NOT_FOUND);
    }
    User userB = userService.getById(userBId);
    if (userB == null) {
      throw new CustomException(StatusCodeEnum.USER_NOT_FOUND);
    }
    return new User[] {userA, userB};
  }
}
