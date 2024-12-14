package com.anon.backend.model.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageSendModel {
  private Integer sender;
  private Integer receiver;
  private String content;
}
