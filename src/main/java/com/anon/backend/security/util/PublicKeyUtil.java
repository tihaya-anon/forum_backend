package com.anon.backend.security.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PublicKeyUtil {
  public static PublicKey decodePubKey(String base64PublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return keyFactory.generatePublic(keySpec);
  }
  public static String encodePubKey(PublicKey pubKey) {
    return Base64.getEncoder().encodeToString(pubKey.getEncoded());
  }
}
