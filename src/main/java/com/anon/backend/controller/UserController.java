package com.anon.backend.controller;

import com.anon.backend.common.constant.AuthType;
import com.anon.backend.common.constant.StatusCodeEnum;
import com.anon.backend.common.resp.RestResp;
import com.anon.backend.entity.User;
import com.anon.backend.map.UserMap;
import com.anon.backend.payload.dto.user.UserRegisterDto;
import com.anon.backend.payload.dto.user.UserUpdateDto;
import com.anon.backend.payload.vo.user.*;
import com.anon.backend.service.IUserService;
import com.anon.backend.service.IStudentAuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * when registering, user must provide its session id. Once session validation passed, use session
 * id to generate a pair of keys Controller
 *
 * @author anon
 * @since 2024-10-22
 */
@RestController
@RequestMapping("/user")
public class UserController {
  private final IUserService userService;
  private final IStudentAuthService studentAuthService;

  public UserController(IUserService userService, IStudentAuthService studentAuthService) {
    this.userService = userService;
    this.studentAuthService = studentAuthService;
  }

  @Operation(summary = "authenticate the phone by sending SMS")
  @PostMapping("/auth_phone")
  public RestResp<?> authPhone(@Valid @RequestBody UserAuthPhoneVo vo) {
    User user = userService.getUserByPhone(vo.getPhone());
    if (user != null) {
      return RestResp.fail(StatusCodeEnum.EXISTED_PHONE);
    }
    userService.authPhone(vo);
    return RestResp.success().setMsg("a confirm message has been sent, please check your phone");
  }

  @Operation(summary = "register, authenticate by the authType")
  @PostMapping("/register")
  public RestResp<?> register(@Valid @RequestBody UserRegisterVo vo) {
    boolean authenticated = studentAuthService.authenticate(vo.getAuthType(), vo.getToken());
    if (!authenticated) {
      return RestResp.fail(StatusCodeEnum.SCHOOL_AUTH_FAILED);
    }
    UserRegisterDto dto = UserMap.INSTANCE.registerVo2RegisterDto(vo);
    UserPersistVo userPersistVo = userService.register(dto);
    return RestResp.success(userPersistVo).setMsg("welcome " + userPersistVo.getUsername());
  }

  @Operation(summary = "login, can add session expire")
  @PostMapping("/login")
  public RestResp<?> login(@Valid @RequestBody UserLoginVo vo) {
    boolean authenticated = studentAuthService.authenticate(vo.getAuthType(), vo.getToken());
    if (!authenticated) {
      return RestResp.fail(StatusCodeEnum.SESSION_EXPIRED);
    }
    UserPersistVo userPersistVo = userService.login(vo);
    return RestResp.success(userPersistVo).setMsg("welcome " + userPersistVo.getUsername());
  }

  @Operation(summary = "update user info")
  @PatchMapping("/{id}")
  public RestResp<?> update(@PathVariable long id, @RequestBody UserUpdateVo vo) {
    boolean isOldPasswordEmpty = StringUtils.isEmpty(vo.getOldPassword());
    boolean isNewPasswordProvided = !StringUtils.isEmpty(vo.getPassword());
    boolean isPhoneProvided = !StringUtils.isEmpty(vo.getPhone());
    boolean isPubKeyProvided = !StringUtils.isEmpty(vo.getPubKey());
    boolean isKeyProvided = isNewPasswordProvided || isPhoneProvided || isPubKeyProvided;
    if (isKeyProvided && isOldPasswordEmpty) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR, "must provide old password");
    }
    UserUpdateDto userUpdateDto = UserMap.INSTANCE.updateVo2UpdateDto(vo).setId(id);
    UserPersistVo userPersistVo = userService.update(userUpdateDto);
    return RestResp.success(userPersistVo);
  }

  @Operation(summary = "delete user")
  @DeleteMapping("/{id}")
  public RestResp<Void> delete(@PathVariable long id) {
    userService.delete(id);
    return RestResp.success();
  }

  @Operation(summary = "fetch auth types")
  @GetMapping("/auth_type")
  public RestResp<?> authTypes() {
    List<AuthType> authTypes = userService.authTypes();
    return RestResp.success(authTypes);
  }
}
