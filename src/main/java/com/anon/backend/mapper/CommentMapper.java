package com.anon.backend.mapper;

import com.anon.backend.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * integrated comment on post and comment on comment, support nested comments Mapper Interface
 *
 * @author anon
 * @since 2024-10-24
 */
public interface CommentMapper extends BaseMapper<Comment> {

}
