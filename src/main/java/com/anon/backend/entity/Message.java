package com.anon.backend.entity;

import java.io.Serial;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* 
*
* @author anon
* @since 2024-10-30
*/
@Getter
@Setter
@Schema(name = "Message", description = "")
public class Message implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private Integer sender;

  private Integer receiver;

  private String content;

  private Boolean isRead;

  private LocalDateTime createAt;
}
