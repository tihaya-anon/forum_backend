package com.anon.backend.payload.dto.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class MessageSendDto {
  private Long sender;
  private Long receiver;
  private String content;
}
