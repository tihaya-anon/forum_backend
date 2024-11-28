package com.anon.backend.map;

import com.anon.backend.entity.User;
import com.anon.backend.payload.dto.user.UserRegisterDto;
import com.anon.backend.payload.dto.user.UserUpdateDto;
import com.anon.backend.payload.vo.user.UserPersistVo;
import com.anon.backend.payload.vo.user.UserRegisterVo;
import com.anon.backend.payload.vo.user.UserUpdateVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMap {
  UserMap INSTANCE = Mappers.getMapper(UserMap.class);

  UserRegisterDto registerVo2registerDto(UserRegisterVo vo);
  User registerDto2user(UserRegisterDto dto);
  UserPersistVo user2persistVo(User user);
  UserUpdateDto updateVo2updateDto(UserUpdateVo vo);
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateDto2user(UserUpdateDto dto, @MappingTarget User user);
}
