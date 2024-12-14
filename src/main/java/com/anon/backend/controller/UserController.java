package com.anon.backend.controller;

import com.anon.backend.common.constant.AuthType;
import com.anon.backend.common.constant.AuthTypeConst;
import com.anon.backend.common.constant.StatusCodeEnum;
import com.anon.backend.common.resp.RestResp;
import com.anon.backend.dto.user.*;
import com.anon.backend.entity.User;
import com.anon.backend.map.UserMap;
import com.anon.backend.model.user.UserRegisterModel;
import com.anon.backend.model.user.UserUpdateModel;
import com.anon.backend.service.IStudentAuthService;
import com.anon.backend.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
  public RestResp<?> authPhone(@Valid @RequestBody UserAuthPhoneDto dto) {
    User user = userService.getUserByPhone(dto.getPhone());
    if (user != null) {
      return RestResp.fail(StatusCodeEnum.EXISTED_PHONE);
    }
    userService.authPhone(dto);
    return RestResp.success().setMsg("a confirm message has been sent, please check your phone");
  }

  @Operation(summary = "register, authenticate by the authType")
  @PostMapping("/register")
  public RestResp<?> register(
      @Valid @RequestBody UserRegisterDto dto,
      @Parameter(description = "authType Header", example = AuthTypeConst.MOCK_VALUE) @RequestParam
          String authType,
      @RequestParam String phone) {
    boolean authenticated = studentAuthService.authenticate(authType, dto.getToken());
    if (!authenticated) {
      return RestResp.fail(StatusCodeEnum.SCHOOL_AUTH_FAILED);
    }
    UserRegisterModel model = UserMap.INSTANCE.registerVo2registerDto(dto);
    model.setAuthType(authType);
    model.setPhone(phone);
    UserPersistDto userPersistVo = userService.register(model);
    return RestResp.success(userPersistVo).setMsg("welcome " + userPersistVo.getUsername());
  }

  @Operation(summary = "login, can add session expire")
  @PostMapping("/login")
  public RestResp<?> login(
      @Parameter(description = "authType Header", example = AuthTypeConst.MOCK_VALUE)
          @RequestHeader("authType")
          String authType,
      @Valid @RequestBody UserLoginDto dto) {
    boolean authenticated = studentAuthService.authenticate(authType, dto.getToken());
    if (!authenticated) {
      return RestResp.fail(StatusCodeEnum.SESSION_EXPIRED);
    }
    UserPersistDto userPersistDto = userService.login(dto);
    return RestResp.success(userPersistDto).setMsg("welcome " + userPersistDto.getUsername());
  }

  @Operation(summary = "update user info")
  @PatchMapping("/{id}")
  public RestResp<?> update(@PathVariable int id, @RequestBody UserUpdateDto dto) {
    boolean isOldPasswordEmpty = StringUtils.isEmpty(dto.getOldPassword());
    boolean isNewPasswordProvided = !StringUtils.isEmpty(dto.getPassword());
    boolean isPhoneProvided = !StringUtils.isEmpty(dto.getPhone());
    boolean isPubKeyProvided = !StringUtils.isEmpty(dto.getPubKey());
    boolean isKeyProvided = isNewPasswordProvided || isPhoneProvided || isPubKeyProvided;
    if (isKeyProvided && isOldPasswordEmpty) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR, "must provide old password");
    }
    UserUpdateModel userUpdateModel = UserMap.INSTANCE.updateVo2updateDto(dto);
    userUpdateModel.setId(id);
    UserPersistDto userPersistVo = userService.update(userUpdateModel);
    return RestResp.success(userPersistVo);
  }

  @Operation(summary = "delete user")
  @DeleteMapping("/{id}")
  public RestResp<Void> delete(@PathVariable int id) {
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
