package com.daitoj.tkms.modules.apir0010.service;

import com.daitoj.tkms.domain.MOrgMenuItem;
import com.daitoj.tkms.domain.MOrgMenuItemId;
import com.daitoj.tkms.modules.apir0010.repository.R0010Repository;
import com.daitoj.tkms.modules.apir0010.repository.mapper.R0010Mapper;
import com.daitoj.tkms.modules.apir0010.service.dto.*;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MOrgMenuItemRepository;
import com.daitoj.tkms.modules.common.repository.MOrgRevRepository;
import com.daitoj.tkms.modules.common.repository.MPositionRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MOrgRevMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MPositionMapper;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import com.daitoj.tkms.domain.MOrgRev;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 概算一覧ビジネスロジック */
@Service
@Transactional(rollbackFor = Throwable.class)
public class R0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(R0010Service.class);

  /** 概算一覧のクエリ */
  private final R0010Repository r0010Repository;

  /** 組織改定情報のリポジトリ */
  private final MOrgRevRepository morgRevRepository;

  /** 組織改定情報のリポジトリ */
  private final MOrgRevMapper morgRevMapper;

  /** 役職情報リポジトリ */
  private final MPositionRepository mpositionRepository;

  private final MOrgMenuItemRepository mOrgMenuItemRepository;
  private final MPositionMapper mPositionMapper;

  /** 役職のDTOマッパー */
  private final R0010Mapper r0010Mapper;

  /** メッセージ */
  private final MessageSource messageSource;

  /** レポートサービス */
  private final ReportService reportService;

  /** fasterxml.jacksonのObjectMapper */
  private final ObjectMapper objectMapper;

  /** CSVファイル名 */
  public static final String APP_NAME = "概算一覧";

  /** レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebC0020.jasper";

  /** コンストラクタ */
  public R0010Service(
      R0010Repository r0010Repository,
      MOrgRevRepository morgRevRepository,
      MOrgRevMapper morgRevMapper,
      MPositionRepository mpositionRepository,
      MOrgMenuItemRepository mOrgMenuItemRepository,
      MPositionMapper mPositionMapper,
      R0010Mapper r0010Mapper,
      MessageSource messageSource,
      ReportService reportService,
      ObjectMapper objectMapper) {
    this.r0010Repository = r0010Repository;
    this.morgRevRepository = morgRevRepository;
    this.morgRevMapper = morgRevMapper;
    this.mpositionRepository = mpositionRepository;
    this.mOrgMenuItemRepository = mOrgMenuItemRepository;
    this.mPositionMapper = mPositionMapper;
    this.r0010Mapper = r0010Mapper;
    this.messageSource = messageSource;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
  }

  /**
   * 初期表示
   *
   * @param effectiveStartDt 適用開始日
   * @return 権限選択条件取得結果
   */
  public ApiResult<R0010ReturnData> getKengenSentakuKoumoku(String effectiveStartDt) {
    try {
      // 適用開始日リストを取得
      List<MOrgRev> orgRevList = morgRevRepository.findAllByOrderByEffectiveStartDtDesc();
      List<MorGInfoDto> morGInfoList =
          r0010Repository.getMorgRevInfoDto(
              effectiveStartDt == null
                  ? orgRevList.get(0).getEffectiveStartDt()
                  : effectiveStartDt);
      // 役職リストを取得
      List<MPositionDto> positionList =
          r0010Repository.findByDelFlgOrderByPositionCd(CommonConstants.DELETE_FLAG_VALID);
      List<R0010S01Dto> mOrgMenuItemInfofoList =
          r0010Repository.getMOrgMenuItemInfoDto(orgRevList.get(0).getEffectiveStartDt());
      // 戻り値
      R0010ReturnData returnData = new R0010ReturnData();
      // 適用開始日リストを設定
      returnData.setStartDtInfo(morgRevMapper.toOrgRevSearchDtoList(orgRevList));
      // 部署選択肢
      returnData.setMorGInfoDtoInfo(morGInfoList);
      // 役職リストを設定
      returnData.setR0010S02DtoInfo(r0010Mapper.toR0010S02DtoList(positionList));
      // 部署権限リスト
      returnData.setR0010S01DtoInfo(mOrgMenuItemInfofoList);
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 初期表示
   *
   * @param effectiveStartDt 適用開始日
   * @return 概算情報取得結果
   */
  public ApiResult<R0020ReturnData> getkengenInfo(String effectiveStartDt) {
    try {
      // 役職リストを取得
      List<MPositionDto> positionList =
          r0010Repository.findByDelFlgOrderByPositionCd(CommonConstants.DELETE_FLAG_VALID);
      List<R0010S01Dto> mOrgMenuItemInfofoList =
          r0010Repository.getMOrgMenuItemInfoDto(effectiveStartDt);
      // 戻り値
      R0020ReturnData returnData = new R0020ReturnData();

      // 役職リストを設定
      returnData.setR0010S02DtoInfo(r0010Mapper.toR0010S02DtoList(positionList));
      // 部署権限リスト
      returnData.setR0010S01DtoInfo(mOrgMenuItemInfofoList);
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 権限情報保存
   *
   * @param inDto パラメータ
   * @return 権限情報
   */
  public ApiResult<R0020ReturnData> savekengenInfo(R0010S03Dto inDto) {
    try {
      // 保存前に削除を実施する
      // パラメータ．部署権限リストのデータを対象テーブルに登録する。
      List<MOrgMenuItem> mOrgMenuItemList1 =
          r0010Mapper.toMOrgMenuItemList(inDto.getR0010S01DtoInfo());
      List<MOrgMenuItemId> mOrgMenuItemIdList1 = new ArrayList<>();
      mOrgMenuItemList1.forEach(entity -> mOrgMenuItemIdList1.add(entity.getId()));

      mOrgMenuItemRepository.deleteAllById(mOrgMenuItemIdList1);

      mOrgMenuItemRepository.saveAll(mOrgMenuItemList1);
      // パラメータ.役職情報リストに存在しないデータについて、削除フラグのUpdateを行う。
      List<R0010S04Dto> r0010S04DtoList = r0010Repository.findGetFlg();

      List<MPositionDto> MPositionList = r0010Mapper.toMPositionList(inDto.getR0010S02DtoInfo());
      for (int i = 0; i < MPositionList.size(); i++) {
        for (int x = 0; x < r0010S04DtoList.size(); x++) {
          if (MPositionList.get(i).getPositionCd().equals(r0010S04DtoList.get(x).getPositionCd())) {
            MPositionList.get(i).setRegUserId(r0010S04DtoList.get(x).getRegUserId());
            MPositionList.get(i).setRegTs(r0010S04DtoList.get(x).getRegTs());
          }
        }
      }
      // 権限管理画面の情報を役職情報TBLに保存する。
      r0010Repository.saveAll(MPositionList);

      List<String> positionCdList = new ArrayList<>();
      List<R0010S04Dto> difference =
          r0010S04DtoList.stream()
              .filter(element -> !inDto.getR0010S02DtoInfo().contains(element.getPositionCd()))
              .toList();
      boolean flg = false;
      for (int i = 0; i < difference.size(); i++) {
        if (difference.get(i).getFlg().equals("1")) {
          flg = true;
        }
        positionCdList.add(difference.get(i).getPositionCd());
      }
      if (flg) {
        String msg =
            messageSource.getMessage(Message.MSGID_R00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_R00001, msg);
      }

      List<MPositionDto> mPositionDtoList = r0010Repository.findAllById(positionCdList);

      mPositionDtoList.forEach(itme -> itme.setDelFlg(CommonConstants.DELETE_FLAG_DELETE));
      r0010Repository.saveAll(mPositionDtoList);
      // パラメータ.役職情報リストに存在しないデータについて、削除フラグのUpdateを行う。

      String msg =
          messageSource.getMessage(Message.MSGID_K00003, null, LocaleContextHolder.getLocale());

      LOG.info(msg);

      // 結果情報
      return ApiResult.error(Message.MSGID_K00003, msg);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }
}
