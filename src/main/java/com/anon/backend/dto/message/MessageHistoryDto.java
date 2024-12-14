package com.anon.backend.dto.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MessageHistoryDto {
  private Integer sender;
  private Integer receiver;
  private String content;
  private LocalDateTime createAt;
}
