package com.anon.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageEnum {
  CONTACT_SELF_FORBID("cannot contact with self"),
  NO_MESSAGE_RECEIVED("no message received"),
  NO_MESSAGE_HISTORY("no message history"),
  NO_POST_FOUND("no post found"),
  NO_OLD_PASSWORD("must provide old password"),
  AUTH_CONFIRM("a confirm message has been sent, please check your phone"),
  WELCOME("welcome, ")
  ;
  private final String msg;
}
