package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.domain.MBusinessType;
import com.daitoj.tkms.domain.TBusinessNewestAppr;
import com.daitoj.tkms.domain.TWfApprStep;
import com.daitoj.tkms.domain.TWfApprStepFiles;
import com.daitoj.tkms.domain.TWfRequest;
import com.daitoj.tkms.domain.TWfRequestFiles;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.repository.MBusinessTypeRepository;
import com.daitoj.tkms.modules.common.repository.TBusinessNewestApprRepository;
import com.daitoj.tkms.modules.common.repository.TWfApprStepFilesRepository;
import com.daitoj.tkms.modules.common.repository.TWfApprStepRepository;
import com.daitoj.tkms.modules.common.repository.TWfRequestFilesRepository;
import com.daitoj.tkms.modules.common.repository.TWfRequestRepository;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/** WF申請処理ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class WfRequestService {
  private static final Logger LOG = LoggerFactory.getLogger(WfRequestService.class);

  /** WF申請情報リポジトリ. */
  private final TWfRequestRepository twfRequestRepository;

  /** WF申請添付ファイル情報リポジトリ. */
  private final TWfRequestFilesRepository twfRequestFilesRepository;

  /** WF承認添付ファイル情報リポジトリ. */
  private final TWfApprStepFilesRepository twfApprStepFilesRepository;

  /** 業務種類情報リポジトリ. */
  private final MBusinessTypeRepository mbusinessTypeRepository;

  /** 業務データ最新承認情報リポジトリ. */
  private final TBusinessNewestApprRepository tbusinessNewestApprRepository;

  /** WF承認ステップ情報リポジトリ. */
  private final TWfApprStepRepository twfApprStepRepository;

  /** 採番サービス. */
  private final NumberService numberRuleService;

  /** コンストラクタ. */
  public WfRequestService(
      TWfRequestRepository twfRequestRepository,
      TWfRequestFilesRepository twfRequestFilesRepository,
      TWfApprStepFilesRepository twfApprStepFilesRepository,
      MBusinessTypeRepository mbusinessTypeRepository,
      TBusinessNewestApprRepository tbusinessNewestApprRepository,
      TWfApprStepRepository twfApprStepRepository,
      NumberService numberRuleService) {
    this.twfRequestRepository = twfRequestRepository;
    this.twfRequestFilesRepository = twfRequestFilesRepository;
    this.twfApprStepFilesRepository = twfApprStepFilesRepository;
    this.mbusinessTypeRepository = mbusinessTypeRepository;
    this.tbusinessNewestApprRepository = tbusinessNewestApprRepository;
    this.twfApprStepRepository = twfApprStepRepository;
    this.numberRuleService = numberRuleService;
  }

  /**
   * WF申請処理.
   *
   * @param businessTypeCd 業務種類コード
   * @param businessDataId 業務データID
   * @param requestAppCd 申請者コード
   * @param appAccountK 申請者アカウント区分
   * @param requestComment 申請コメント
   * @param fileList 申請ファイルIDリスト
   * @param apprEmpCdList 承認者従業員コードリスト
   * @return 申請ID
   */
  public Long wfRequest(
      String businessTypeCd,
      Long businessDataId,
      String requestAppCd,
      String appAccountK,
      String requestComment,
      List<UUID> fileList,
      List<String> apprEmpCdList) {
    // システム日付
    Instant systemDate = numberRuleService.getSystemDate();

    // 業務種類コードをキーに、業務種類テーブルから業務種類情報を取得
    MBusinessType businessTypeEntity =
        mbusinessTypeRepository
            .findById(businessTypeCd)
            .orElseThrow(BusinessTypeNotExistException::new);

    // WF申請情報
    TWfRequest wfRequest =
        getTwfRequest(
            businessDataId,
            requestAppCd,
            appAccountK,
            requestComment,
            businessTypeEntity,
            systemDate);

    // WF申請情報を登録
    TWfRequest newWfRequest = twfRequestRepository.save(wfRequest);

    // 業務種類情報.業務テーブルID、引数.業務データIDをキーに、業務データ最新承認情報テーブルを検索
    Optional<TBusinessNewestAppr> apprData =
        tbusinessNewestApprRepository.findByBusinessTblIdAndBusinessDataId(
            businessTypeEntity.getBusinessTblId(), businessDataId);

    // 業務データ最新承認情報を新規作成または更新する
    createOrUpdateBusinessNewestAppr(
        apprData,
        newWfRequest,
        businessTypeEntity,
        businessDataId,
        requestAppCd,
        appAccountK,
        requestComment,
        systemDate);

    if (!CollectionUtils.isEmpty(fileList)) {
      List<TWfRequestFiles> requestFilesEntityList = new ArrayList<>();

      // 引数.申請ファイルIDリストを繰り返し
      for (int i = 0; i < fileList.size(); i++) {
        TWfRequestFiles requestFilesEntity = new TWfRequestFiles();

        // 申請ID：WF申請情報の申請ID
        requestFilesEntity.setWfRequest(newWfRequest);
        // 連番：１から
        requestFilesEntity.setSeqNo(i + 1);
        // 業務データID：引数.業務データID
        // TODO
        // 添付ファイルID：ループしながら引数.申請ファイルIDリストから取得
        requestFilesEntity.setFileId(fileList.get(i));

        requestFilesEntityList.add(requestFilesEntity);
      }
      // WF申請添付ファイル情報を登録
      twfRequestFilesRepository.saveAll(requestFilesEntityList);
    }

    if (!CollectionUtils.isEmpty(apprEmpCdList)) {
      List<TWfApprStep> apprStepEntityList = new ArrayList<>();

      // 引数.承認社従業員コードリストを繰り返し
      for (int i = 0; i < apprEmpCdList.size(); i++) {
        TWfApprStep apprStepEntity = new TWfApprStep();

        // 申請ID：WF申請情報の申請ID
        apprStepEntity.setWfRequest(newWfRequest);
        // 承認順序：１から
        apprStepEntity.setApprStepOrder(i + 1);
        // 承認者従業員コード：ループしながら引数.承認社従業員コードリストから取得
        apprStepEntity.setApprEmpCd(apprEmpCdList.get(i));
        // 承認状態：'1'(承認待)
        apprStepEntity.setApprSt(CommonConstants.APPR_ST_PENDING_APPROVAL);

        apprStepEntityList.add(apprStepEntity);
      }
      // WF承認ステップ情報を登録
      twfApprStepRepository.saveAll(apprStepEntityList);
    }

    // WF申請情報の申請IDを返却
    return newWfRequest.getId();
  }

  /**
   * WF承認処理
   *
   * @param requestId 申請ID
   * @param apprEmpCd 承認者従業員コード
   * @param apprSts 承認状態
   * @param apprComment 承認コメント
   * @param fileList 承認添付ファイルリスト
   * @return 業務データステータス
   */
  public String wfAppr(
      Long requestId, String apprEmpCd, String apprSts, String apprComment, List<UUID> fileList) {
    // WF申請情報
    TWfRequest wfRequest =
        twfRequestRepository.findById(requestId).orElseThrow(RequestNotExistException::new);

    // WF承認ステップ情報
    TWfApprStep wfApprStep =
        twfApprStepRepository
            .findByWfRequest_IdAndApprStepOrder(requestId, wfRequest.getCurrentStep())
            .orElseThrow(WfApprNotExistException::new);

    // 承認者従業員コードが承認ステップ情報の承認者従業員コードと同じではない場合
    if (!apprEmpCd.equals(wfApprStep.getApprEmpCd())) {
      throw new WfApprNotExistException();
    }

    // システム日付
    Instant systemDate = numberRuleService.getSystemDate();

    // 承認状態
    wfApprStep.setApprSt(apprSts);
    // 承認日時
    wfApprStep.setDecisionTs(systemDate);
    // 承認コメント
    wfApprStep.setDecisionComment(apprComment);

    if (!CollectionUtils.isEmpty(fileList)) {
      List<TWfApprStepFiles> apprFilesEntityList = new ArrayList<>();

      // 引数.申請ファイルIDリストを繰り返し
      for (int i = 0; i < fileList.size(); i++) {
        TWfApprStepFiles requestFilesEntity = new TWfApprStepFiles();
        // 承認ステップID
        requestFilesEntity.setWfApprStepId(wfApprStep.getId());
        // 連番：１から
        requestFilesEntity.setSeqNo(i + 1);

        // 添付ファイルID：ループしながら引数.申請ファイルIDリストから取得
        requestFilesEntity.setFileId(fileList.get(i));

        apprFilesEntityList.add(requestFilesEntity);
      }
      // WF承認添付ファイル情報を登録
      twfApprStepFilesRepository.saveAll(apprFilesEntityList);
    }

    // 承認状態が差戻
    if (CommonConstants.APPR_ST_RETURN.equals(apprSts)) {
      // 業務データステータス:差戻
      wfRequest.setBusinessDataSt(CommonConstants.APPR_ST_RETURN);
      // 最終承認日時
      wfRequest.setFinalApprTs(null);
      // 最終承認者従業員コード
      wfRequest.setFinalApprEmpCd(null);

      // 業務データ最新承認情報
      Optional<TBusinessNewestAppr> newestAppr =
          tbusinessNewestApprRepository.findByNewestWfRequestId(requestId);

      if (newestAppr.isPresent()) {
        // 業務データステータス:差戻
        newestAppr.get().setBusinessDataSt(CommonConstants.APPR_ST_RETURN);
        // 最終承認日時
        newestAppr.get().setFinalApprDt(null);
        // 最終承認者従業員コード
        newestAppr.get().setFinalApprEmpCd(null);
      }
    } else {
      // WF承認ステップ情報
      List<TWfApprStep> wfApprStepList = twfApprStepRepository.findByWfRequest_Id(requestId);

      // 承認順序 >=  承認ステップ情報の件数
      if (wfRequest.getCurrentStep() >= wfApprStepList.size()) {
        // 最終承認
        // 承認待順序
        wfRequest.setCurrentStep(wfRequest.getCurrentStep() + 1);
        // 業務データステータス:承認済
        wfRequest.setBusinessDataSt(CommonConstants.APPR_ST_PASS);
        // 最終承認日時
        wfRequest.setFinalApprTs(OffsetDateTime.from(systemDate));
        // 最終承認者従業員コード
        wfRequest.setFinalApprEmpCd(apprEmpCd);

        // 業務データ最新承認情報
        Optional<TBusinessNewestAppr> newestAppr =
            tbusinessNewestApprRepository.findByNewestWfRequestId(wfRequest.getId());

        if (newestAppr.isPresent()) {
          // 業務データステータス:承認済
          newestAppr.get().setBusinessDataSt(CommonConstants.APPR_ST_PASS);
          // 最終承認日時
          newestAppr
              .get()
              .setFinalApprDt(DateUtils.formatDateTime(systemDate, DateUtils.DATE_FORMAT));
          // 最終承認者従業員コード
          newestAppr.get().setFinalApprEmpCd(apprEmpCd);
        }
      } else {
        // 中間承認
        // 承認待順序
        wfRequest.setCurrentStep(wfRequest.getCurrentStep() + 1);
      }
    }

    return wfRequest.getBusinessDataSt();
  }

  /**
   * 業務データ最新承認情報を新規作成または更新する。
   *
   * @param apprData 既存の業務データ最新承認情報。存在しない場合は新規作成される。
   * @param newWfRequest 申請ID
   * @param businessTypeEntity 業務テーブルID
   * @param businessDataId 業務データID
   * @param requestAppCd 申請者コード
   * @param appAccountK 申請者アカウント区分
   * @param requestComment 申請コメント
   * @param systemDate システム日時
   */
  private void createOrUpdateBusinessNewestAppr(
      Optional<TBusinessNewestAppr> apprData,
      TWfRequest newWfRequest,
      MBusinessType businessTypeEntity,
      Long businessDataId,
      String requestAppCd,
      String appAccountK,
      String requestComment,
      Instant systemDate) {
    TBusinessNewestAppr apprEntity = apprData.orElseGet(TBusinessNewestAppr::new);

    // 最新申請ID：WF申請情報の申請ID
    apprEntity.setNewestWfRequestId(newWfRequest.getId());
    // 業務テーブルID：業務種類情報.業務テーブルID
    apprEntity.setBusinessTblId(businessTypeEntity.getBusinessTblId());
    // 業務データID：引数.業務データID
    apprEntity.setBusinessDataId(businessDataId);
    // 申請者従業員コード：引数.申請者従業員コード
    apprEntity.setRequestAppCd(requestAppCd);
    // 申請者アカウント区分：引数.申請者アカウント区分
    apprEntity.setAppAccountK(appAccountK);
    // 申請コメント：引数.申請コメント
    apprEntity.setRequestComment(requestComment);
    // 申請日時：システム日時（共通部品）
    apprEntity.setRequestTs(systemDate);
    // 業務データステータス：'2'(申請中)
    apprEntity.setBusinessDataSt(CommonConstants.BUSINESS_DATA_ST_REQ);
    // 最終承認日時：NULL
    apprEntity.setFinalApprDt(null);
    // 最終承認者従業員コード：NULL
    apprEntity.setFinalApprEmpCd(null);

    // 新規作成時は保存
    if (apprData.isEmpty()) {
      tbusinessNewestApprRepository.save(apprEntity);
    }
  }

  /**
   * WF申請情報を作成
   *
   * @param businessDataId 業務データID
   * @param requestAppCd 申請者コード
   * @param appAccountK 申請者アカウント区分
   * @param requestComment 申請コメント
   * @param businessTypeEntity 業務種類情報
   * @param systemDate システム日時
   * @return WF申請情報
   */
  private TWfRequest getTwfRequest(
      Long businessDataId,
      String requestAppCd,
      String appAccountK,
      String requestComment,
      MBusinessType businessTypeEntity,
      Instant systemDate) {
    TWfRequest wfRequest = new TWfRequest();

    // 業務種類コード：引数.業務種類コード
    wfRequest.setBusinessTypeCd(businessTypeEntity);
    // 業務テーブルID：業務種類情報.業務テーブルID
    wfRequest.setBusinessTblId(businessTypeEntity.getBusinessTblId());
    // 業務データID：引数.業務データID
    wfRequest.setBusinessDataId(businessDataId);
    // 申請者従業員コード：引数.申請者従業員コード
    wfRequest.setRequestAppCd(requestAppCd);
    // 申請者アカウント区分：引数.申請者アカウント区分
    wfRequest.setAppAccountK(appAccountK);
    // 申請コメント：引数申請コメント
    wfRequest.setRequestComment(requestComment);
    // 承認待順序：1
    wfRequest.setCurrentStep(1);
    // 申請日時：システム日時（共通部品）
    wfRequest.setRequestTs(OffsetDateTime.from(systemDate));
    // 業務データステータス：'2'(申請中)
    wfRequest.setBusinessDataSt(CommonConstants.BUSINESS_DATA_ST_REQ);
    return wfRequest;
  }
}
