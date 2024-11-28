package com.anon.backend.service;

import com.anon.backend.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * integrated comment on post and comment on comment, support nested comments Service
 *
 * @author anon
 * @since 2024-10-22
 */
public interface ICommentService extends IService<Comment> {

}
