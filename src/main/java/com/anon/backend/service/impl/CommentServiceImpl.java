package com.anon.backend.service.impl;

import com.anon.backend.entity.Comment;
import com.anon.backend.mapper.CommentMapper;
import com.anon.backend.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * integrated comment on post and comment on comment, support nested comments Service Implement
 *
 * @author anon
 * @since 2024-10-22
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
