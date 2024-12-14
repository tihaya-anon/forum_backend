package com.anon.backend.service;

import com.anon.backend.common.req.PageReq;
import com.anon.backend.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.anon.backend.dto.post.PostPersistDto;
import com.anon.backend.dto.post.PostPublishDto;

import java.util.List;

/**
 * post is different from comment, for it is required to search posts fast, but it doesn't matter if
 * user wait when loading the posts' details Service
 *
 * @author anon
 * @since 2024-10-22
 */
public interface IPostService extends IService<Post> {

  void create(int author, PostPublishDto vo);

  void delete(int id);

  List<PostPersistDto> filterByAuthor(int author, PageReq pageReq);

  List<PostPersistDto> filterByTag(String tag, PageReq pageReq);
}
