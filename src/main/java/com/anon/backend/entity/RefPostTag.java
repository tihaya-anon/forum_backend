package com.anon.backend.entity;

import java.io.Serial;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* 
*
* @author anon
* @since 2025-01-17
*/
@Getter
@Setter
@TableName("ref_post_tag")
@Schema(name = "RefPostTag", description = "")
public class RefPostTag implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private Long postId;

  private Long tagId;

  @TableLogic
  private Boolean isDelete;
}
