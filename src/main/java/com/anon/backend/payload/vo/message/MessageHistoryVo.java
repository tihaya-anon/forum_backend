package com.anon.backend.payload.vo.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class MessageHistoryVo {
  private Long sender;
  private Long receiver;
  private String content;
  private LocalDateTime createAt;
}
