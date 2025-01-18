package com.anon.backend.payload.vo.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class UserPersistVo {
  private Long id;
  private String username;
  private String school;
  private String token;
}
