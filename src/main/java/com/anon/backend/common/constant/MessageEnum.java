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
  ;
  private final String msg;
}
