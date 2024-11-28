package com.anon.backend.payload.vo.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserAuthPhoneVo {
  @NotBlank(message = "must provide school")
  private String authType;

  @NotBlank(message = "must provide phone")
  private String phone;
}
