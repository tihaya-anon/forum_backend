package com.anon.backend.payload.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class UserUpdateDto {
  private Long id;
  private String username;
  private String password;
  private String phone;
  private String oldPassword;
}
