package com.anon.backend.payload.vo.message;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MessageHistoryVo {
  private Integer sender;
  private Integer receiver;
  private String content;
  private LocalDateTime createAt;
}
