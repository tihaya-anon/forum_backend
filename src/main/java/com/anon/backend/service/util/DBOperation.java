package com.anon.backend.service.util;

import com.anon.backend.common.CustomException;
import com.anon.backend.common.constant.CURD;
import com.anon.backend.common.constant.StatusCodeEnum;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;

import java.util.function.Supplier;

@AllArgsConstructor
public class DBOperation {
  private final Logger logger;

  public void performWithCheck(CURD curd, Supplier<Boolean> operation) {
    try {
      boolean result = operation.get();
      if (!result) {
        this.logger.error("{} failed", curd.getType());
        throw new CustomException(StatusCodeEnum.SYSTEM_ERROR);
      }
    } catch (Exception e) {
      handleException(curd, e);
    }
  }

  public <T> T perform(CURD curd, Supplier<T> operation) {
    try {
      return operation.get();
    } catch (Exception e) {
      handleException(curd, e);
      return null;
    }
  }

  private void handleException(CURD curd, Exception e) {
    this.logger.error("Exception during {} operation: {}", curd.getType(), e.getMessage());
    throw new CustomException(StatusCodeEnum.SYSTEM_ERROR);
  }
}
