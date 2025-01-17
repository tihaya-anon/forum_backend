package com.anon.backend.payload.vo.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostPublishVo {
  private String[] tags;
  private @NotBlank String title;
  private Boolean isAnonymous;
}
