package com.daitoj.tkms.modules.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 半角カタカナから全角カタカナへの変換処理.
 */
public class EnhancedFullWidthConverterUtils {

  // 半角→全角の単一文字マッピング（濁音・半濁音を除く）
  private static final Map<Character, Character> KANA_MAPPING = new HashMap<>();
  // 結合文字マッピング（濁音記号ﾞ･半濁音記号ﾟとの組み合わせ）
  private static final Map<String, Character> COMBINED_CHAR_MAP = new HashMap<>();

  static {
    // 基本文字初期化処理
    char[] half = "ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝｧｨｩｪｫｬｭｮｯｰ".toCharArray();
    char[] full =
            "アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンァィゥェォャュョッー".toCharArray();
    for (int i = 0; i < half.length; i++) {
      KANA_MAPPING.put(half[i], full[i]);
    }
    // 結合文字初期化処理（濁音・半濁音）
    String[][] combinedChars = {
            {"ｶﾞ", "ガ"}, {"ｷﾞ", "ギ"}, {"ｸﾞ", "グ"}, {"ｹﾞ", "ゲ"}, {"ｺﾞ", "ゴ"},
            {"ｻﾞ", "ザ"}, {"ｼﾞ", "ジ"}, {"ｽﾞ", "ズ"}, {"ｾﾞ", "ゼ"}, {"ｿﾞ", "ゾ"},
            {"ﾀﾞ", "ダ"}, {"ﾁﾞ", "ヂ"}, {"ﾂﾞ", "ヅ"}, {"ﾃﾞ", "デ"}, {"ﾄﾞ", "ド"},
            {"ﾊﾞ", "バ"}, {"ﾋﾞ", "ビ"}, {"ﾌﾞ", "ブ"}, {"ﾍﾞ", "ベ"}, {"ﾎﾞ", "ボ"},
            {"ﾊﾟ", "パ"}, {"ﾋﾟ", "ピ"}, {"ﾌﾟ", "プ"}, {"ﾍﾟ", "ペ"}, {"ﾎﾟ", "ポ"}
    };
    for (String[] pair : combinedChars) {
      COMBINED_CHAR_MAP.put(pair[0], pair[1].charAt(0));
    }
  }

  /**
   * 文字列内の半角カタカナを全角に変換.
   *
   * @param input 変換対象文字列
   * @return 変換済み文字列（全角カタカナ含む）
   */
  public static String convert(String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }
    StringBuilder sb = new StringBuilder();
    char[] chars = input.toCharArray();
    int length = chars.length;

    // 文字単位走査処理
    for (int i = 0; i < length; ) {
      // 結合文字パターンチェック（例: ﾀﾞ → ダ）
      if (i < length - 1 && COMBINED_CHAR_MAP.containsKey("" + chars[i] + chars[i + 1])) {
        sb.append(COMBINED_CHAR_MAP.get("" + chars[i] + chars[i + 1]));
        i += 2; // 2文字分インデックスを進める
      } else if (KANA_MAPPING.containsKey(chars[i])) {
        // 通常の半角カタカナ変換
        sb.append(KANA_MAPPING.get(chars[i]));
        i += 1;
      } else {
        // 変換不要な文字はそのまま出力
        sb.append(chars[i]);
        i += 1;
      }
    }
    return sb.toString();
  }
}
