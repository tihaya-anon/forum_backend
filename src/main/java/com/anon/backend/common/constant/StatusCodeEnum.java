package com.anon.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * C: customer S: system T: third party
 *
 * @author anon
 * @since 2024-10-24
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {
  SUCCESS("00000", "success"),

  CUSTOMER_ERROR("C0000", "customer error"),
  EXISTED_PHONE("C0001", "existed phone"),
  USER_NOT_FOUND("C0002", "user not found"),
  VALIDATION_ERROR("C0003", "validation error"),
  SESSION_EXPIRED("C0004", "session expired"),
  PASSWORD_WRONG("C0005", "password wrong"),

  SYSTEM_ERROR("S0000", "system error"),

  THIRD_PARTY_ERROR("T0000", "third party error"),
  FAIL_TO_SEND_MSG("T0001", "fail to send message"),
  UNSUPPORTED_AUTHENTICATION("T0002", "unsupported authentication"),
  SCHOOL_AUTH_FAILED("T0003", "school auth failed"),
  ;
  private final String code;
  private final String msg;
}
