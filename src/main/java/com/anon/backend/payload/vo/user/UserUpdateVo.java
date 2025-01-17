package com.anon.backend.payload.vo.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserUpdateVo {
  private String username;
  private String password;
  private String phone;
  private String pubKey;
  private String oldPassword;
}
