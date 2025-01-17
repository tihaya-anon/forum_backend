package com.anon.backend.payload.vo.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserLoginVo {
  private @NotBlank String authType;
  private @NotBlank String phone;
  private @NotBlank String password;
  private @NotBlank String token;
}
