package com.anon.backend.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anon.backend.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * post is different from comment, for it is required to search posts fast, but it doesn't matter if
 * user wait when loading the posts' details Mapper Interface
 *
 * @author anon
 * @since 2024-10-24
 */
public interface PostMapper extends BaseMapper<Post> {
  List<String> readPostTags(Long id);

  IPage<Post> readPostByTag(Page<Post> page, String tag);
}
