package com.anon.backend.payload.vo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserUpdateVo {
  private String username;
  private String password;
  private String phone;
  private String pubKey;
  private String oldPassword;
}
