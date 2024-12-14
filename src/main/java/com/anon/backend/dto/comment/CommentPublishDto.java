package com.anon.backend.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentPublishDto {
  @NotBlank private String content;
  @NotNull private String type;
  @NotNull private Integer author;
  @NotNull private Boolean parentType;
  @NotNull private Integer parent;
}
