package com.daitoj.tkms.modules.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/** 日付処理クラス */
public class DateUtils {

  /** DB日付フォーマット */
  public static final String DATE_FORMAT = "yyyyMMdd";

  /** DB日付フォーマット */
  public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

  /** DB日付フォーマット */
  public static final String DATE_FORMAT_YYMM = "yyMM";

  /**
   * 日付フォーマット
   *
   * @param dateString 日付
   * @param pattern フォーマット
   * @return フォーマットした日付
   */
  public static String formatDateFromYYYYMMDD(String dateString, String pattern) {

    LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.BASIC_ISO_DATE);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

    return date.format(formatter);
  }

  /**
   * フォーマット指定して、システム日付を取得する
   *
   * @param pattern フォーマット
   * @return フォーマットした日付
   */
  public static String formatNow(String pattern) {
    return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
  }

  /**
   * 日付をフォーマット
   *
   * @param pattern フォーマット
   * @return フォーマットした日付
   */
  public static String formatDateTime(Instant date, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneOffset.UTC);

    return formatter.format(date);
  }

  /**
   * DBの日付に変換
   *
   * @return DBの日付
   */
  public static Instant getDbDateTime() {

    return Instant.now();
  }
}
