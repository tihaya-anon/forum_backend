package com.anon.backend.map;

import com.anon.backend.entity.Comment;
import com.anon.backend.payload.vo.comment.CommentPublishVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMap {
  CommentMap INSTANCE = Mappers.getMapper(CommentMap.class);

  Comment publishVo2Comment(CommentPublishVo vo);
}
