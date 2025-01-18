package com.anon.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.anon.backend.common.CustomException;
import com.anon.backend.common.constant.AuthType;
import com.anon.backend.common.constant.AuthTypeConst;
import com.anon.backend.common.constant.CURD;
import com.anon.backend.common.constant.StatusCodeEnum;
import com.anon.backend.payload.dto.user.UserRegisterDto;
import com.anon.backend.entity.User;
import com.anon.backend.map.UserMap;
import com.anon.backend.mapper.UserMapper;
import com.anon.backend.payload.dto.user.UserUpdateDto;
import com.anon.backend.payload.vo.user.UserAuthPhoneVo;
import com.anon.backend.payload.vo.user.UserLoginVo;
import com.anon.backend.payload.vo.user.UserPersistVo;
import com.anon.backend.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anon.backend.service.util.DBOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * when registering, user must provide its session id. Once session validation passed, use session
 * id to generate a pair of keys Service Implement
 *
 * @author anon
 * @since 2024-10-22
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final DBOperation dbOperation = new DBOperation(logger);

  @Override
  public User getUserByPhone(String phone) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("phone", phone);
    List<User> userList = baseMapper.selectList(queryWrapper);
    if (userList.isEmpty()) {
      return null;
    }
    return userList.get(0);
  }

  @Override
  public void authPhone(UserAuthPhoneVo vo) {
    sendMessage(vo.getPhone(), "verify code mock");
  }

  @Override
  public UserPersistVo register(UserRegisterDto dto) {
    User user = getUserByPhone(dto.getPhone());
    if (user != null) {
      throw new CustomException(StatusCodeEnum.EXISTED_PHONE);
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    user = UserMap.INSTANCE.registerDto2User(dto);
    List<AuthType> authTypes = AuthTypeConst.getAuthTypes();
    String school = "";
    for (AuthType authType : authTypes) {
      if (authType.value().equals(dto.getAuthType())) {
        school = authType.label();
      }
    }
    user.setSchool(school);

    User finalUser = user;
    dbOperation.performWithCheck(CURD.CREATE, () -> this.save(finalUser));

    return UserMap.INSTANCE.user2PersistVo(user).setToken("<token>");
  }

  @Override
  public UserPersistVo login(UserLoginVo vo) {
    User user = getUserByPhone(vo.getPhone());
    if (user == null) {
      throw new CustomException(StatusCodeEnum.USER_NOT_FOUND);
    }

    String providedPassword = vo.getPassword();
    String storedPassword = user.getPassword();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (!passwordEncoder.matches(providedPassword, storedPassword)) {
      throw new CustomException(StatusCodeEnum.PASSWORD_WRONG);
    }

    return UserMap.INSTANCE.user2PersistVo(user).setToken("<token>");
  }

  @Override
  public UserPersistVo update(UserUpdateDto dto) {
    User user = this.getById(dto.getId());
    if (user == null) {
      throw new CustomException(StatusCodeEnum.USER_NOT_FOUND);
    }

    String providedPassword = dto.getOldPassword();
    if (providedPassword != null) {
      String storedPassword = user.getPassword();
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      if (!passwordEncoder.matches(providedPassword, storedPassword)) {
        throw new CustomException(StatusCodeEnum.PASSWORD_WRONG);
      }
      dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    UserMap.INSTANCE.updateDto2User(dto, user);
    dbOperation.performWithCheck(CURD.UPDATE, () -> this.updateById(user));

    return UserMap.INSTANCE.user2PersistVo(user);
  }

  @Override
  public void delete(long id) {
    dbOperation.performWithCheck(CURD.DELETE, () -> this.removeById(id));
  }

  @Override
  public void sendMessage(String phone, String content) {
    System.out.println("send message `" + content + "` to " + phone);
  }

  @Override
  public List<AuthType> authTypes() {
    return AuthTypeConst.getAuthTypes();
  }
}
