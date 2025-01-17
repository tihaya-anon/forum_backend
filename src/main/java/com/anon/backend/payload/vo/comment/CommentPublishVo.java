package com.anon.backend.payload.vo.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommentPublishVo {
  private @NotBlank String content;
  private @NotNull String type;
  private @NotNull Long author;
  private @NotNull Boolean parentType;
  private @NotNull Long parent;
  private @NotNull Long post;
}
