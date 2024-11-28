package com.anon.backend.security.auth;

import com.anon.backend.common.CustomException;

public interface StudentAuth {
  boolean authenticate(String token) throws CustomException;
}
