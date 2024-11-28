package com.anon.backend.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SymmetricKeyGen {
  public static SecretKey get() throws Exception {
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(256);
    return keyGen.generateKey();
  }
}
