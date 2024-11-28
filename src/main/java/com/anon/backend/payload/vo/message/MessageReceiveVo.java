package com.anon.backend.payload.vo.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MessageReceiveVo {
  private Integer sender;
  private String content;
  private LocalDateTime createAt;
}
