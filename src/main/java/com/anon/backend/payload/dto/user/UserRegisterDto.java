package com.anon.backend.payload.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRegisterDto {
  private String username;
  private String password;
  private String authType;
  private String phone;
  private String pubKey;
}
