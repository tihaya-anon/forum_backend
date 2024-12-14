package com.anon.backend.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateModel {
  private Integer id;
  private String username;
  private String password;
  private String phone;
  private String oldPassword;
}
