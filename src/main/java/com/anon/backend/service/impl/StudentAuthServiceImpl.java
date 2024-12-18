package com.anon.backend.service.impl;

import com.anon.backend.common.CustomException;
import com.anon.backend.common.constant.StatusCodeEnum;
import com.anon.backend.security.auth.StudentAuth;
import com.anon.backend.service.IStudentAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentAuthServiceImpl implements IStudentAuthService {
  private final Map<String, StudentAuth> authStrategy;

  @Autowired
  public StudentAuthServiceImpl(List<StudentAuth> auths) {
    authStrategy = new HashMap<>();
    auths.forEach(auth -> authStrategy.put(auth.getClass().getAnnotation(Component.class).value(), auth));
  }
  @Override
  public boolean authenticate(String authType, String token) throws CustomException {
    StudentAuth auth = authStrategy.get(authType);
    if (auth == null) {
      throw new CustomException(StatusCodeEnum.UNSUPPORTED_AUTHENTICATION);
    }
    return auth.authenticate(token);
  }
}
