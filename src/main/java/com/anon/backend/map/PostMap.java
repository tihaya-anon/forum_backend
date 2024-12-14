package com.anon.backend.map;

import com.anon.backend.dto.post.PostUpdateDto;
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

  Post publishDto2Post(PostPublishDto dto);

  PostPersistDto post2PersistVo(Post post);

  Post updateDto2Post(PostUpdateDto dto);
}
