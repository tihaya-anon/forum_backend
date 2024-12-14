package com.anon.backend.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRegisterModel {
  private String username;
  private String password;
  private String authType;
  private String phone;
  private String pubKey;
}
