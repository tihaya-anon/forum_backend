package com.anon.backend.payload.vo.message;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageSendVo {
  @NotBlank private String content;
}
