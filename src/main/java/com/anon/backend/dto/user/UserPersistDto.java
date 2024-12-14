package com.anon.backend.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserPersistDto {
  private Integer id;
  private String username;
  private String school;
  private String token;
}
