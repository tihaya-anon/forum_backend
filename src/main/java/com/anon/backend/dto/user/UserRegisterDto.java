package com.anon.backend.payload.vo.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegisterVo {
  @NotBlank private String authType;
  @NotBlank private String phone;
  @NotBlank private String verifyCode;
  @NotBlank private String username;
  @NotBlank private String password;
  @NotBlank private String pubKey;
  @NotBlank private String token;
}
