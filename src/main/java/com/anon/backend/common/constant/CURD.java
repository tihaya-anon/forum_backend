package com.anon.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CURD{
  CREATE("insert"),
  UPDATE("update"),
  READ("select"),
  DELETE("delete");
  private final String type;
}
