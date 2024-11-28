package com.anon.backend.map;

import com.anon.backend.entity.Post;
import com.anon.backend.payload.vo.post.PostPersistVo;
import com.anon.backend.payload.vo.post.PostPublishVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMap {
  PostMap INSTANCE = Mappers.getMapper(PostMap.class);

  Post publishVo2Post(PostPublishVo vo);

  PostPersistVo post2PersistVo(Post post);
}
