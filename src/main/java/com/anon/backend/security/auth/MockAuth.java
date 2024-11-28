package com.anon.backend.security.auth;

import com.anon.backend.common.CustomException;
import com.anon.backend.common.constant.AuthTypeConst;
import org.springframework.stereotype.Component;

@Component(AuthTypeConst.MOCK_VALUE)
public class MockAuth implements StudentAuth {
  @Override
  public boolean authenticate(String token) throws CustomException {
    return true;
  }
}
