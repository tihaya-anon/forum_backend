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
import lombok.experimental.Accessors;

/**
* 
*
* @author anon
* @since 2025-01-18
*/
@Getter
@Setter
@Accessors(chain = true)
@TableName("ref_follow")
@Schema(name = "RefFollow", description = "")
public class RefFollow implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private Long following;

  private Long follower;

  @TableLogic
  private Boolean isDelete;
}
