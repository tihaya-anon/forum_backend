package com.anon.backend.common.constant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AuthTypeConst {
  public static final String MOCK_VALUE = "mock";
  public static final String SCHOOL0_VALUE = "school0";
  public static final String SCHOOL1_VALUE = "school1";
  public static final String SCHOOL2_VALUE = "school2";

  public static final String MOCK_LABEL = "Mock Authentication";
  public static final String SCHOOL0_LABEL = "School 0 Authentication";
  public static final String SCHOOL1_LABEL = "School 1 Authentication";
  public static final String SCHOOL2_LABEL = "School 2 Authentication";

  public static @NotNull List<AuthType> getAuthTypes() {
    List<AuthType> authTypes = new ArrayList<>();
    authTypes.add(new AuthType(MOCK_VALUE, MOCK_LABEL));
    authTypes.add(new AuthType(SCHOOL0_VALUE, SCHOOL0_LABEL));
    authTypes.add(new AuthType(SCHOOL1_VALUE, SCHOOL1_LABEL));
    authTypes.add(new AuthType(SCHOOL2_VALUE, SCHOOL2_LABEL));
    return authTypes;
  }
}
