package com.anon.backend.entity;

import java.io.Serial;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* tag can be filters and traced, so it is required to be an independent table
*
* @author anon
* @since 2025-01-17
*/
@Getter
@Setter
@Schema(name = "Tag", description = "tag can be filters and traced, so it is required to be an independent table")
public class Tag implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private String content;
}
