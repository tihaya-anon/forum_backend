package com.anon.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.anon.backend.common.CustomException;
import com.anon.backend.common.constant.AuthType;
import com.anon.backend.common.constant.AuthTypeConst;
import com.anon.backend.common.constant.CURD;
import com.anon.backend.common.constant.StatusCodeEnum;
import com.anon.backend.model.user.UserRegisterModel;
import com.anon.backend.entity.User;
import com.anon.backend.map.UserMap;
import com.anon.backend.mapper.UserMapper;
import com.anon.backend.model.user.UserUpdateModel;
import com.anon.backend.dto.user.UserAuthPhoneDto;
import com.anon.backend.dto.user.UserLoginDto;
import com.anon.backend.dto.user.UserPersistDto;
import com.anon.backend.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anon.backend.service.util.DBOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * when registering, user must provide its session id. Once session validation passed, use session
 * id to generate a pair of keys Service Implement
 *
 * @author anon
 * @since 2024-10-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${server.ssl.enabled}")
  private boolean serverSSLEnabled;

  @Value("${server.address}")
  private String serverAddress;

  @Value("${server.port}")
  private String serverPort;

  @Override
  public User getUserByPhone(String phone) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("phone", phone);
    List<User> userList = baseMapper.selectList(queryWrapper);

    if (userList.isEmpty()) {
      return null;
    }

    return userList.get(0);
  }

  @Override
  public void authPhone(UserAuthPhoneDto vo) {
    String serverProtocol = serverSSLEnabled ? "https" : "http";
    //    String path = serverProtocol + "://" + serverAddress + ":" + serverPort + "/register";
    String path = serverProtocol + "://" + serverAddress + ":" + 3000 + "/register";
    String url =
        UriComponentsBuilder.fromHttpUrl(path)
            .queryParam("authType", vo.getAuthType())
            .queryParam("phone", vo.getPhone())
            .toUriString();
    sendMessage(vo.getPhone(), url);
  }

  @Override
  public UserPersistDto register(UserRegisterModel dto) {
    User user = getUserByPhone(dto.getPhone());
    if (user != null) {
      throw new CustomException(StatusCodeEnum.EXISTED_PHONE);
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    user = UserMap.INSTANCE.registerDto2user(dto);
    List<AuthType> authTypes = AuthTypeConst.getAuthTypes();
    String school = "";
    for (AuthType authType : authTypes) {
      if (authType.value().equals(dto.getAuthType())) {
        school = authType.label();
      }
    }
    user.setSchool(school);

    User finalUser = user;
    DBOperation.performWithCheck(logger, CURD.CREATE, () -> this.save(finalUser));
    UserPersistDto userPersistVo = UserMap.INSTANCE.user2persistVo(user);
    userPersistVo.setToken("<token>");

    return userPersistVo;
  }

  @Override
  public UserPersistDto login(UserLoginDto vo) {
    User user = getUserByPhone(vo.getPhone());
    if (user == null) {
      throw new CustomException(StatusCodeEnum.USER_NOT_FOUND);
    }

    String providedPassword = vo.getPassword();
    String storedPassword = user.getPassword();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (!passwordEncoder.matches(providedPassword, storedPassword)) {
      throw new CustomException(StatusCodeEnum.PASSWORD_WRONG);
    }

    UserPersistDto userPersistVo = UserMap.INSTANCE.user2persistVo(user);
    userPersistVo.setToken("<token>");

    return userPersistVo;
  }

  @Override
  public UserPersistDto update(UserUpdateModel dto) {
    User user = this.getById(dto.getId());
    if (user == null) {
      throw new CustomException(StatusCodeEnum.USER_NOT_FOUND);
    }

    String providedPassword = dto.getOldPassword();
    if (providedPassword != null) {
      String storedPassword = user.getPassword();
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      if (!passwordEncoder.matches(providedPassword, storedPassword)) {
        throw new CustomException(StatusCodeEnum.PASSWORD_WRONG);
      }
      dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    UserMap.INSTANCE.updateDto2user(dto, user);
    DBOperation.performWithCheck(logger, CURD.UPDATE, () -> this.updateById(user));

    return UserMap.INSTANCE.user2persistVo(user);
  }

  @Override
  public void delete(int id) {
    DBOperation.performWithCheck(logger, CURD.DELETE, () -> this.removeById(id));
  }

  @Override
  public void sendMessage(String phone, String content) {
    System.out.println("send message `" + content + "` to " + phone);
  }

  @Override
  public List<AuthType> authTypes() {
    return AuthTypeConst.getAuthTypes();
  }
}
