package com.anon.backend.service.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anon.backend.common.constant.CURD;
import com.anon.backend.common.req.PageReq;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;

import java.util.List;

@AllArgsConstructor
public class PageOperation<T> {
  private final Logger logger;
  private final BaseMapper<T> baseMapper;

  public List<T> paginate(@NotNull PageReq pageReq, QueryWrapper<T> queryWrapper) {
    IPage<T> page = new Page<>(pageReq.getPageIdx(), pageReq.getPageSize());
    IPage<T> resultPage =
        new DBOperation(logger).perform(CURD.READ, () -> baseMapper.selectPage(page, queryWrapper));

    return resultPage.getRecords();
  }
}
