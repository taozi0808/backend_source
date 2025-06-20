package com.daitoj.tkms.modules.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;

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

    if (StringUtils.isBlank(dateString)) {
      return "";
    }

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

  /**
   * YYYYMMDD日付はInstantに変換.
   *
   * @param inDateString 日付
   * @return 変換後の日付
   */
  public static Instant stringToInstant(String inDateString) {
    if (inDateString == null || inDateString.isEmpty()) {
      return null;
    }
    return LocalDate.parse(inDateString, DateTimeFormatter.ofPattern(DATE_FORMAT))
        .atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant();
  }

  /**
   * YYYYMMDD日付はOffsetDateTimeに変換.
   *
   * @param dateString 日付
   * @return 変換後の日付
   */
  public static OffsetDateTime stringToOffsetDateTime(String dateString) {
    if (dateString == null || dateString.isEmpty()) {
      return null;
    }
    return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT))
        .atStartOfDay()
        .atOffset(ZonedDateTime.now().getOffset());
  }
    /**
     * YYYYMMDD日付はEndOfDayOffsetDateTimeに変換.
     *
     * @param dateString 日付
     * @return 変換後の日付
     */
  public static OffsetDateTime stringToEndOfDayOffsetDateTime(String dateString) {
    if (dateString == null || dateString.isEmpty()) {
      return null;
    }
    return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT))
        .atTime(23, 59, 59, 999000000) // 23:59:59.999
        .atOffset(ZonedDateTime.now().getOffset());
  }
}
