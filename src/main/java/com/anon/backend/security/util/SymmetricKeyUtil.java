package com.anon.backend.security.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Base64;

public class SymmetricKeyUtil {
  public static byte[] encodeByPubKey(SecretKey symmetricKey, PublicKey publicKey)
      throws Exception {
    Cipher rsaCipher = Cipher.getInstance("RSA");
    rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
    return rsaCipher.doFinal(symmetricKey.getEncoded());
  }

  public static String encodeSymKey(byte[] data) {
    return Base64.getEncoder().encodeToString(data);
  }
}
