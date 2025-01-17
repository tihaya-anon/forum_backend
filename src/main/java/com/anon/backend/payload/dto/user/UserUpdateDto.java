package com.anon.backend.payload.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserUpdateDto {
  private Long id;
  private String username;
  private String password;
  private String phone;
  private String oldPassword;
}
