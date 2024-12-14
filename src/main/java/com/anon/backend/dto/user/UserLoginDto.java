package com.anon.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserLoginDto {
  @NotBlank
  private String phone;
  @NotBlank
  private String password;
  @NotBlank
  private String token;
}
