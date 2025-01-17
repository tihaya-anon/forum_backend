package com.anon.backend.payload.vo.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
public class PostPersistVo {
  private Long id;
  private String title;
  private List<String> tagList;
  private Long author;
  private String username;
  private Integer commentCount;
  private Integer likes;
  private Integer dislikes;
  private LocalDateTime createAt;
  private LocalDateTime updateAt;
}
