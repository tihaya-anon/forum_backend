package com.anon.backend.service;

import com.anon.backend.common.constant.AuthType;
import com.anon.backend.entity.User;
import com.anon.backend.payload.dto.user.UserRegisterDto;
import com.anon.backend.payload.dto.user.UserUpdateDto;
import com.anon.backend.payload.vo.user.UserAuthPhoneVo;
import com.anon.backend.payload.vo.user.UserLoginVo;
import com.anon.backend.payload.vo.user.UserPersistVo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * when registering, user must provide its session id. Once session validation passed, use session
 * id to generate a pair of keys Service
 *
 * @author anon
 * @since 2024-10-22
 */
public interface IUserService extends IService<User> {

  User getUserByPhone(String phone);

  void authPhone(UserAuthPhoneVo vo);

  UserPersistVo register(UserRegisterDto dto);

  UserPersistVo login(UserLoginVo vo);

  UserPersistVo update(UserUpdateDto dto);

  void delete(long id);

  void sendMessage(String phone, String content);

  List<AuthType> authTypes();
}
