package com.anon.backend.payload.vo.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserAuthPhoneVo {
  private @NotBlank(message = "must provide school") String authType;
  private @NotBlank(message = "must provide phone") String phone;
}
