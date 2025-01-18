package com.anon.backend.payload.dto.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class PostTagDto {
  private Long id;
  private String[] tags;
}
