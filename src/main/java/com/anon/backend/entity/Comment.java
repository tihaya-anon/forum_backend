package com.anon.backend.entity;

import java.io.Serial;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
* integrated comment on post and comment on comment, support nested comments
*
* @author anon
* @since 2025-01-18
*/
@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "Comment", description = "integrated comment on post and comment on comment, support nested comments")
public class Comment implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @Schema(description = "can be plain text, url")
  private String content;

  private String type;

  private Long author;

  @Schema(description = "0: post parent; 1: comment parent")
  private Boolean parentType;

  @Schema(description = "the id of which the comment replay to, can be post or comment")
  private Long parent;

  private Integer childCount;

  private Integer likes;

  private Integer dislikes;

  @TableLogic
  private Boolean isDelete;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;
}
