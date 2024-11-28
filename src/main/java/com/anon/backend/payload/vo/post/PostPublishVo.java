package com.anon.backend.payload.vo.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostPublishVo {
  private String[] tags;
  @NotBlank private String title;
}
