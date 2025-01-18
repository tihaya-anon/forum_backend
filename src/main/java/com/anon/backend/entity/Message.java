package com.anon.backend.entity;

import java.io.Serial;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Schema(name = "Message", description = "")
public class Message implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private Long sender;

  private Long receiver;

  private String content;

  private Boolean isRead;

  private LocalDateTime createAt;
}
