package com.anon.backend.common.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PageReq {
  private int pageIdx = 1;
  private int pageSize = 10;
}
