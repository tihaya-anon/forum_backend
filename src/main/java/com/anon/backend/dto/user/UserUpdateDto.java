package com.anon.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserUpdateDto {
  private String username;
  private String password;
  private String phone;
  private String pubKey;
  private String oldPassword;
}
