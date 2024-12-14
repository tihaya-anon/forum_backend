package com.anon.backend.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostPublishDto {
  private String[] tags;
  @NotBlank private String title;
}
