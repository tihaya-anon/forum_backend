package com.anon.backend.map;

import com.anon.backend.entity.Message;
import com.anon.backend.payload.dto.message.MessageSendDto;
import com.anon.backend.payload.vo.message.MessageHistoryVo;
import com.anon.backend.payload.vo.message.MessageReceiveVo;
import com.anon.backend.payload.vo.message.MessageSendVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MessageMap {
  MessageMap INSTANCE = Mappers.getMapper(MessageMap.class);

  MessageSendDto sendVo2SendDto(MessageSendVo vo);

  Message sendDto2Message(MessageSendDto dto);

  MessageReceiveVo message2ReceiveVo(Message message);

  MessageHistoryVo message2HistoryVo(Message message);
}
