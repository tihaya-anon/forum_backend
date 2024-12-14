package com.anon.backend.dto.message;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageSendDto {
  @NotBlank private String content;
}
