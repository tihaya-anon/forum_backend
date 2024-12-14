package com.anon.backend.dto.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class PostUpdateDto {
  private Integer id;
  private Integer commentCount;
  private Integer likes;
  private Integer dislikes;
  private LocalDateTime updateAt;
}
