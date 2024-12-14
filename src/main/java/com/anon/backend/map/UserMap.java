package com.anon.backend.map;

import com.anon.backend.entity.User;
import com.anon.backend.dto.user.UserPersistDto;
import com.anon.backend.dto.user.UserRegisterDto;
import com.anon.backend.dto.user.UserUpdateDto;
import com.anon.backend.model.user.UserRegisterModel;
import com.anon.backend.model.user.UserUpdateModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMap {
  UserMap INSTANCE = Mappers.getMapper(UserMap.class);

  UserRegisterModel registerVo2registerDto(UserRegisterDto vo);
  User registerDto2user(UserRegisterModel dto);
  UserPersistDto user2persistVo(User user);
  UserUpdateModel updateVo2updateDto(UserUpdateDto vo);
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateDto2user(UserUpdateModel dto, @MappingTarget User user);
}
