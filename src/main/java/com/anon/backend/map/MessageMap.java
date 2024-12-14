package com.anon.backend.map;

import com.anon.backend.entity.Message;
import com.anon.backend.dto.message.MessageHistoryDto;
import com.anon.backend.dto.message.MessageReceiveDto;
import com.anon.backend.dto.message.MessageSendDto;
import com.anon.backend.model.message.MessageSendModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MessageMap {
  MessageMap INSTANCE = Mappers.getMapper(MessageMap.class);

  MessageSendModel sendVo2sendDto(MessageSendDto vo);

  Message sendDto2Message(MessageSendModel dto);

  MessageReceiveDto messageVo2receiveDto(Message message);

  MessageHistoryDto messageVo2historyDto(Message message);
}
