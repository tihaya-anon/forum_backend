package com.anon.backend.service.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anon.backend.common.constant.CURD;
import com.anon.backend.common.req.PageReq;
import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;

import java.util.List;

public class PageOperation {

  public static <T> List<T> paginate(
      Logger logger,
      @NotNull PageReq pageReq,
      QueryWrapper<T> queryWrapper,
      BaseMapper<T> baseMapper) {
    IPage<T> page = new Page<>(pageReq.getPageIdx(), pageReq.getPageSize());
    IPage<T> resultPage =
        DBOperation.perform(logger, CURD.READ, () -> baseMapper.selectPage(page, queryWrapper));
    return resultPage.getRecords();
  }
}
