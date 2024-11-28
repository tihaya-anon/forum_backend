package com.anon.backend.payload.vo.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class PostPersistVo {
  private Integer id;
  private String title;
  private List<String> tagList;
  private Integer author;
  private Integer commentCount;
  private Integer likes;
  private Integer dislikes;
  private LocalDateTime createAt;
  private LocalDateTime updateAt;
}
