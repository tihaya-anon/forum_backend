package com.anon.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegisterDto {
  @NotBlank
  private String username;
  @NotBlank
  private String password;
  @NotBlank
  private String pubKey;
  @NotBlank
  private String token;
}
