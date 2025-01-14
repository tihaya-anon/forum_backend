package com.anon.backend.controller;

import com.anon.backend.common.constant.MessageEnum;
import com.anon.backend.common.req.PageReq;
import com.anon.backend.common.resp.RestResp;
import com.anon.backend.payload.vo.post.PostPersistVo;
import com.anon.backend.payload.vo.post.PostPublishVo;
import com.anon.backend.service.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * post is different from comment, for it is required to search posts fast, but it doesn't matter if
 * user wait when loading the posts' details Controller
 *
 * @author anon
 * @since 2024-10-22
 */
@RestController
@RequestMapping("/post")
public class PostController {
  private final IPostService postService;

  public PostController(IPostService postService) {
    this.postService = postService;
  }

  @Operation(summary = "publish new post")
  @PostMapping("/{id}")
  public RestResp<Void> publish(@PathVariable int id, @Valid @RequestBody PostPublishVo vo) {
    postService.create(id, vo);
    return RestResp.success();
  }

  @Operation(summary = "delete post")
  @DeleteMapping("/{id}")
  public RestResp<Void> delete(@PathVariable int id) {
    postService.delete(id);
    return RestResp.success();
  }

  @Operation(summary = "filter by author")
  @GetMapping("/author/{id}")
  public RestResp<?> filterAuthor(@PathVariable int id, PageReq pageReq) {
    List<PostPersistVo> postPersistVoList = postService.filterByAuthor(id, pageReq);
    return RestResp.allowNull(postPersistVoList, MessageEnum.NO_POST_FOUND);
  }

  @Operation(summary = "filter by tag")
  @GetMapping("/tag/{tag}")
  public RestResp<?> filterTag(@PathVariable String tag, PageReq pageReq) {
    List<PostPersistVo> postPersistVoList = postService.filterByTag(tag, pageReq);
    return RestResp.allowNull(postPersistVoList, MessageEnum.NO_POST_FOUND);
  }
}
