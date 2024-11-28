package com.anon.backend.exception;

import com.anon.backend.common.CustomException;
import com.anon.backend.common.constant.StatusCodeEnum;
import com.anon.backend.common.resp.RestResp;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * handle all the validation exception
   * @param ex the exception
   * @return the structural response
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public RestResp<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    StringBuilder errorMsg = new StringBuilder();
    ex.getBindingResult()
      .getFieldErrors()
      .forEach(error -> errorMsg.append("`")
        .append(error.getField())
        .append("` - ")
        .append(error.getDefaultMessage())
        .append("; "));
    return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR, errorMsg.toString());
  }


  /**
   * handle all the validation exception
   * @param ex the exception
   * @return the structural response
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  public RestResp<Void> handleConstraintViolation(ConstraintViolationException ex) {
    StringBuilder errorMsg = new StringBuilder();
    ex.getConstraintViolations()
      .forEach(violation -> errorMsg.append("`")
        .append(violation.getPropertyPath())
        .append("` - ")
        .append(violation.getMessage())
        .append("; "));
    return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(errorMsg.toString());
  }


  /**
   * handle all the custom exception
   * @param ex the exception
   * @return the structural response
   */
  @ExceptionHandler(CustomException.class)
  @ResponseBody
  public RestResp<Void> handleCustomException(CustomException ex) {
    return RestResp.fail(ex.getStatusCodeEnum());
  }
}
