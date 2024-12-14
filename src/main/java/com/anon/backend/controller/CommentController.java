package com.anon.backend.controller;

import com.anon.backend.common.resp.RestResp;
import com.anon.backend.dto.comment.CommentPublishDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * integrated comment on post and comment on comment, support nested comments Controller
 *
 * @author anon
 * @since 2024-10-22
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
  @Operation(summary = "publish new comment")
  @PostMapping("/{authorId}")
  public RestResp<Void> publish(@PathVariable int authorId, CommentPublishDto dto) {
    return RestResp.success();
  }
}
