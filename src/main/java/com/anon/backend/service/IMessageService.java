package com.anon.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.anon.backend.common.req.PageReq;
import com.anon.backend.entity.Message;
import com.anon.backend.model.message.MessageSendModel;
import com.anon.backend.dto.message.MessageHistoryDto;
import com.anon.backend.dto.message.MessageReceiveDto;

import java.util.List;

/**
 * Service
 *
 * @author anon
 * @since 2024-10-22
 */
public interface IMessageService extends IService<Message> {
  void create(MessageSendModel dto);

  List<MessageReceiveDto> read(int id, boolean all, PageReq pageReq);

  List<MessageHistoryDto> history(int id, int with, PageReq pageReq);

  String readRecipientKey(int id, int to);
}
