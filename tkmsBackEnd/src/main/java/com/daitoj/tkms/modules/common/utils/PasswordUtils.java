package com.daitoj.tkms.modules.common.utils;

import java.security.SecureRandom;

/** パスワード処理クラス */
public class PasswordUtils {
  /**
   * パスワードを生成
   *
   * @return パスワード
   */
  public static String createPassword() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder(6);
    SecureRandom random = new SecureRandom();

    // パスワード作成
    for (int i = 0; i < 6; i++) {
      int index = random.nextInt(characters.length());
      sb.append(characters.charAt(index));
    }

    return sb.toString();
  }
}
