package com.anon.backend.payload.vo.message;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MessageSendVo {
  private @NotBlank String content;
}
