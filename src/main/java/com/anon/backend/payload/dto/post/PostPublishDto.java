package com.anon.backend.payload.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class PostPublishDto {
  private Long author;
  private String[] tags;
  private @NotBlank String title;
  private Boolean isAnonymous;
}
