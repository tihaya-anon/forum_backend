package com.anon.backend.payload.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateDto {
  private Integer id;
  private String username;
  private String password;
  private String phone;
  private String oldPassword;
}
