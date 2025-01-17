package com.anon.backend.payload.dto.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MessageSendDto {
  private Long sender;
  private Long receiver;
  private String content;
}
