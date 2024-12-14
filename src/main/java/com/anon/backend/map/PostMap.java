package com.anon.backend.map;

import com.anon.backend.entity.Post;
import com.anon.backend.dto.post.PostPersistDto;
import com.anon.backend.dto.post.PostPublishDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMap {
  PostMap INSTANCE = Mappers.getMapper(PostMap.class);

  Post publishVo2Post(PostPublishDto vo);

  PostPersistDto post2PersistVo(Post post);
}
