package com.anon.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserAuthPhoneDto {
  @NotBlank(message = "must provide school")
  private String authType;

  @NotBlank(message = "must provide phone")
  private String phone;
}
