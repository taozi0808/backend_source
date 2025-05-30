package com.daitoj.tkms.modules.common.utils;

import com.ibm.icu.text.Transliterator;
import org.apache.commons.lang3.StringUtils;

/** 文字処理クラス */
public class TextUtils {
  private static final Transliterator HALF_TO_FULL =
      Transliterator.getInstance("Halfwidth-Fullwidth");

  /**
   * DBの文字列とキーワードが一致するかを半角全角を無視して判定する
   *
   * @param dbText DBに格納されている文字列
   * @param keyword 検索キーワード
   * @return 全角変換後に部分一致するかを返す
   */
  public static boolean matchesIgnoringKanaWidth(String dbText, String keyword) {

    // キーワードが入力されないの場合、検索しない（一致とする）
    if (StringUtils.isBlank(keyword)) {
      return true;
    }

    // キーワードが入力される場合、DBの文字列がnullの場合、一致しないとする
    if (StringUtils.isBlank(dbText)) {
      return false;
    }

    // DBの文字列とキーワードの両方を全角に変換する
    String fullDbText = HALF_TO_FULL.transliterate(dbText);
    String fullKeyword = HALF_TO_FULL.transliterate(keyword);

    // キーワードがDBの文字列に含まれているかどうかを判定する
    return fullDbText.contains(fullKeyword);
  }
}
