package com.anon.backend.service.util;

import com.anon.backend.common.CustomException;
import com.anon.backend.common.constant.CURD;
import com.anon.backend.common.constant.StatusCodeEnum;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class DBOperation {

  public static void performWithCheck(Logger logger, CURD curd, Supplier<Boolean> operation) {
    try {
      boolean result = operation.get();
      if (!result) {
        logger.error("{} failed", curd.getType());
        throw new CustomException(StatusCodeEnum.SYSTEM_ERROR);
      }
    } catch (Exception e) {
      logger.error("Exception during {} operation: {}", curd.getType(), e.getMessage());
      throw new CustomException(StatusCodeEnum.SYSTEM_ERROR);
    }
  }

  public static <T> T perform(Logger logger, CURD curd, Supplier<T> operation) {
    try {
      return operation.get();
    } catch (Exception e) {
      logger.error("Exception during {} operation: {}", curd.getType(), e.getMessage());
      throw new CustomException(StatusCodeEnum.SYSTEM_ERROR);
    }
  }
}

