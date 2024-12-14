package com.anon.backend.service;

import com.anon.backend.common.CustomException;

public interface IStudentAuthService {
  boolean authenticate(String authType, String token) throws CustomException;
}
