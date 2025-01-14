package com.anon.backend.service;

import com.anon.backend.common.constant.AuthType;
import com.anon.backend.model.user.UserRegisterModel;
import com.anon.backend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.anon.backend.model.user.UserUpdateModel;
import com.anon.backend.dto.user.UserAuthPhoneDto;
import com.anon.backend.dto.user.UserLoginDto;
import com.anon.backend.dto.user.UserPersistDto;
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

  void authPhone(UserAuthPhoneDto vo);

  UserPersistDto register(UserRegisterModel dto);

  UserPersistDto login(UserLoginDto vo);

  UserPersistDto update(UserUpdateModel dto);

  void delete(long id);

  void sendMessage(String phone, String content);

  List<AuthType> authTypes();
}
