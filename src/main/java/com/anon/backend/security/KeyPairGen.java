package com.anon.backend.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyPairGen {
  public static KeyPair get(String algorithm) {
    try {
      KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
      return generator.generateKeyPair();
    } catch (Exception e){
      throw new RuntimeException(e);
    }
  }
}
