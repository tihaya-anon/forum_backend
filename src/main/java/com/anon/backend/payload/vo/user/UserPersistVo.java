package com.anon.backend.payload.vo.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserPersistVo {
  private Long id;
  private String username;
  private String school;
  private String token;
}
