package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.domain.MNumber;
import com.daitoj.tkms.domain.MNumberId;
import com.daitoj.tkms.domain.MNumberRule;
import com.daitoj.tkms.modules.common.constants.NumberPattern;
import com.daitoj.tkms.modules.common.repository.MNumberRepository;
import com.daitoj.tkms.modules.common.repository.MNumberRuleRepository;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import java.time.Instant;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 採番サービス */
@Service
public class NumberService {
  private static final Logger LOG = LoggerFactory.getLogger(NumberService.class);

  /** 採番ルールリポジトリ */
  private final MNumberRuleRepository mnumberRuleRepository;

  /** 採番リポジトリ */
  private final MNumberRepository mnumberRepository;

  /** 主番 */
  public static final String NUMBER_FIX = "FIX";

  /** システム日付 */
  @Value("${app.sys-time:#{null}}")
  private String systemDate;

  /** コンストラクタ */
  public NumberService(
      MNumberRuleRepository mnumberRuleRepository, MNumberRepository mnumberRepository) {
    this.mnumberRuleRepository = mnumberRuleRepository;
    this.mnumberRepository = mnumberRepository;
  }

  /**
   * 採番処理
   *
   * @param fieldId 項目ID
   * @param prefix 接頭辞
   * @param number 主番
   * @return 採番結果
   */
  @Transactional(rollbackFor = Throwable.class)
  public String getNextNumberByFieldId(String fieldId, String prefix, String number) {
    // 連番
    int newNumber = 0;
    // 接頭辞
    String prefixStr = null;
    // 主番
    String numberStr = null;
    // システム日付
    Instant sysDate = null;

    // 採番ルール情報を取得
    MNumberRule numberRule =
        mnumberRuleRepository.findById(fieldId).orElseThrow(InvalidFieldException::new);

    // 引数の接頭辞あり
    if (StringUtils.isNotBlank(prefix)) {
      // 引数の接頭辞
      prefixStr = prefix;
    } else {
      // 採番の接頭辞
      prefixStr = StringUtils.defaultString(numberRule.getPrefix());
    }

    // 連番（接頭辞なし）の場合
    if (NumberPattern.SEQUENCE_ONLY.getPattern().equals(numberRule.getNumberPat())) {
      // 引数の主番あり
      if (StringUtils.isNotBlank(number)) {
        // 主番
        numberStr = number;
      } else {
        // 主番 = FIX
        numberStr = NUMBER_FIX;
      }
      // 接頭辞＋連番
    } else if (NumberPattern.PREFIX_AND_SEQUENCE.getPattern().equals(numberRule.getNumberPat())) {
      // 主番 = 接頭辞
      numberStr = prefixStr;
      // 接頭辞＋年月(YYYYMM)＋連番
    } else if (NumberPattern.PREFIX_AND_YEAR_MONTH_YYYYMM_AND_SEQUENCE
        .getPattern()
        .equals(numberRule.getNumberPat())) {
      // システム日付
      sysDate = getSystemDate();

      // 主番
      numberStr = prefixStr + DateUtils.formatDateTime(sysDate, DateUtils.DATE_FORMAT_YYYYMM);

      // 接頭辞＋年月(YYMM)＋連番
    } else if (NumberPattern.PREFIX_AND_YEAR_MONTH_YYMM_AND_SEQUENCE
        .getPattern()
        .equals(numberRule.getNumberPat())) {
      // システム日付
      sysDate = getSystemDate();

      // 主番
      numberStr = prefixStr + DateUtils.formatDateTime(sysDate, DateUtils.DATE_FORMAT_YYMM);
    }

    // 採番情報
    Optional<MNumber> mnumber = mnumberRepository.findById_FieldIdAndId_PkNo(fieldId, numberStr);

    // データ存在する
    if (mnumber.isPresent()) {
      // 連番
      int seq = 0;
      if (mnumber.get().getSeqNo() != null) {
        seq = mnumber.get().getSeqNo();
      }

      // 連番 + 1
      newNumber = seq + 1;

      // 連番 > 採番ルール情報.連番TO
      if (newNumber > numberRule.getSeqTo()) {
        // 採番上限超過
        throw new SequenceLimitException();
      }

      // 連番更新
      mnumber.get().setSeqNo(newNumber);
    } else {
      // 連番 = 連番FROM
      newNumber = numberRule.getSeqFrom();

      // 採番情報ID
      MNumberId id = new MNumberId();
      id.setFieldId(fieldId);
      id.setPkNo(numberStr);

      // 採番情報
      MNumber numberEntity = new MNumber();
      numberEntity.setId(id);
      numberEntity.setSeqNo(newNumber);
      // 連番更新
      mnumberRepository.save(numberEntity);
    }

    // 編集済連番
    String editedNumber = null;
    // 採番結果
    String retNumber = null;

    // 桁数
    int length = String.valueOf(numberRule.getSeqTo()).length();
    // 先頭にゼロを埋め
    editedNumber = String.format("%0" + length + "d", newNumber);

    // 連番（接頭辞なし）の場合
    if (NumberPattern.SEQUENCE_ONLY.getPattern().equals(numberRule.getNumberPat())) {
      retNumber = editedNumber;
      // 接頭辞＋連番
    } else if (NumberPattern.PREFIX_AND_SEQUENCE.getPattern().equals(numberRule.getNumberPat())) {
      retNumber = prefixStr + editedNumber;
      // 接頭辞＋年月(YYYYMM)＋連番
    } else if (NumberPattern.PREFIX_AND_YEAR_MONTH_YYYYMM_AND_SEQUENCE
        .getPattern()
        .equals(numberRule.getNumberPat())) {
      retNumber = prefixStr + DateUtils.formatDateTime(sysDate, DateUtils.DATE_FORMAT_YYYYMM) + editedNumber;

      // 接頭辞＋年月(YYMM)＋連番
    } else if (NumberPattern.PREFIX_AND_YEAR_MONTH_YYMM_AND_SEQUENCE
        .getPattern()
        .equals(numberRule.getNumberPat())) {
      retNumber = prefixStr + DateUtils.formatDateTime(sysDate, DateUtils.DATE_FORMAT_YYMM) + editedNumber;
    }

    return retNumber;
  }

  /**
   * システム日付を取得
   *
   * @return システム日付
   */
  public Instant getSystemDate() {
    // システム日付
    Instant date;

    // 設定ファイルからシステム日付設定値を判断
    if (systemDate != null) {
      // システム日付設定値
      date = Instant.parse(systemDate);

      return date;
    }

    // DBから、システム日付を取得
    date = mnumberRepository.getCurrentDbTime();

    return date;
  }
}
