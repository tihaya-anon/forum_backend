package com.anon.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.anon.backend.common.req.PageReq;
import com.anon.backend.entity.Message;
import com.anon.backend.payload.dto.message.MessageSendDto;
import com.anon.backend.payload.vo.message.MessageHistoryVo;
import com.anon.backend.payload.vo.message.MessageReceiveVo;

import java.util.List;

/**
 * Service
 *
 * @author anon
 * @since 2024-10-22
 */
public interface IMessageService extends IService<Message> {
  void create(MessageSendDto dto);

  List<MessageReceiveVo> read(int id, boolean all, PageReq pageReq);

  List<MessageHistoryVo> history(int id, int with, PageReq pageReq);

  String readRecipientKey(int id, int to);
}
