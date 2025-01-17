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

/**
* post is different from comment, for it is required to search posts fast, but it doesn't matter if user wait when loading the posts' details
*
* @author anon
* @since 2025-01-17
*/
@Getter
@Setter
@Schema(name = "Post", description = "post is different from comment, for it is required to search posts fast, but it doesn't matter if user wait when loading the posts' details")
public class Post implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private String title;

  private Long author;

  private Integer commentCount;

  private Integer likes;

  private Integer dislikes;

  @TableLogic
  private Boolean isDelete;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  private Integer tagCount;
}
