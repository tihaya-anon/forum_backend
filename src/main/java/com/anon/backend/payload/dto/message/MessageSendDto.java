package com.anon.backend.payload.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageSendDto {
  private Integer sender;
  private Integer receiver;
  private String content;
}
