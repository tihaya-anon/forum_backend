package com.anon.backend.common;

import com.anon.backend.common.constant.StatusCodeEnum;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
  private final StatusCodeEnum statusCodeEnum;

  public CustomException(StatusCodeEnum statusCodeEnum) {
    super(statusCodeEnum.getMsg());
    this.statusCodeEnum = statusCodeEnum;
  }
}
