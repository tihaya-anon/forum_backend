package com.anon.backend;

import com.anon.backend.security.KeyPairGen;
import com.anon.backend.security.util.PublicKeyUtil;

import java.security.KeyPair;
import java.util.Base64;

public class TestPubKey {
  private static final KeyPair keyPair = KeyPairGen.get("RSA");
  private static final String pubKey = PublicKeyUtil.encodePubKey(keyPair.getPublic());

  public static void main(String[] args) {
    System.out.println(pubKey);
    System.out.println(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
  }
}
