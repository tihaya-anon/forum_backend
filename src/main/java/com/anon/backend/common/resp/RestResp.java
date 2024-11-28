package com.anon.backend.common.resp;

import com.anon.backend.common.constant.MessageEnum;
import com.anon.backend.common.constant.StatusCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author anon
 * @since 2024-10-24
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class RestResp<T> {
  private String code;
  private String msg;
  private T data;

  private RestResp() {
    this.code = StatusCodeEnum.SUCCESS.getCode();
    this.msg = StatusCodeEnum.SUCCESS.getMsg();
  }

  private RestResp(@NotNull StatusCodeEnum statusCodeEnum) {
    this.code = statusCodeEnum.getCode();
    this.msg = statusCodeEnum.getMsg();
  }

  private RestResp(T data) {
    this();
    this.data = data;
  }

  @Contract(" -> new")
  public static @NotNull RestResp<Void> success() {
    return new RestResp<>(StatusCodeEnum.SUCCESS);
  }

  @Contract("_ -> new")
  public static <T> @NotNull RestResp<T> success(T data) {
    return new RestResp<>(data);
  }

  @Contract("_ -> new")
  public static @NotNull RestResp<Void> fail(StatusCodeEnum statusCodeEnum) {
    return new RestResp<>(statusCodeEnum);
  }

  @Contract("_,_ -> new")
  public static <T> @NotNull RestResp<T> fail(StatusCodeEnum statusCodeEnum, T data) {
    return new RestResp<T>(statusCodeEnum).setData(data);
  }

  public RestResp<T> setMsg(@NotNull MessageEnum messageEnum) {
    this.msg = messageEnum.getMsg();
    return this;
  }

  public RestResp<T> setMsg(String msg) {
    this.msg = msg;
    return this;
  }

  public static @NotNull RestResp<?> allowNull(Object data, String msg) {
    return RestResp.success(Objects.requireNonNullElse(data, msg));
  }

  @Contract("_, _ -> new")
  public static @NotNull RestResp<?> allowNull(Object data, MessageEnum messageEnum) {
    return allowNull(data, messageEnum.getMsg());
  }
}
