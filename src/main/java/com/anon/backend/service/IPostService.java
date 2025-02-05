package com.anon.backend.service;

import com.anon.backend.common.req.PageReq;
import com.anon.backend.entity.Post;
import com.anon.backend.payload.dto.post.PostPublishDto;
import com.baomidou.mybatisplus.extension.service.IService;
import com.anon.backend.payload.vo.post.PostPersistVo;

import java.util.List;

/**
 * post is different from comment, for it is required to search posts fast, but it doesn't matter if
 * user wait when loading the posts' details Service
 *
 * @author anon
 * @since 2024-10-22
 */
public interface IPostService extends IService<Post> {

  void create(PostPublishDto dto);

  void delete(long id);

  List<PostPersistVo> listRecent(PageReq pageReq);

  List<PostPersistVo> filterByAuthor(long author, PageReq pageReq);

  List<PostPersistVo> filterByTag(String tag, PageReq pageReq);
}
