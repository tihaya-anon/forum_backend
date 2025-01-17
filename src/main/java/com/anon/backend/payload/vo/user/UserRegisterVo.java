package com.anon.backend.payload.vo.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRegisterVo {
  private @NotBlank String authType;
  private @NotBlank String phone;
  private @NotBlank String verifyCode;
  private @NotBlank String username;
  private @NotBlank String password;
  private @NotBlank String pubKey;
  private @NotBlank String token;
}
