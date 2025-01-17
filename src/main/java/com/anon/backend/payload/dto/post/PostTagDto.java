package com.anon.backend.payload.dto.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostTagDto {
  private Long id;
  private String[] tags;
}
