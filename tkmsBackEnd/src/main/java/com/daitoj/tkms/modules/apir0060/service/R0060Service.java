package com.daitoj.tkms.modules.apir0060.service;

import com.daitoj.tkms.domain.MCustomer;
import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.modules.apir0045.service.dto.*;
import com.daitoj.tkms.modules.apir0060.repository.R0060Repository;
import com.daitoj.tkms.modules.apir0060.repository.mapper.R0060Mapper;
import com.daitoj.tkms.modules.apir0060.service.dto.CustomerBankBankBanchInfoDto;
import com.daitoj.tkms.modules.apir0060.service.dto.CustomerInfoDto;
import com.daitoj.tkms.modules.apir0060.service.dto.R0060S01ReturnData;
import com.daitoj.tkms.modules.apis0120.service.dto.CustomerApprInfoDto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.constants.TemplateName;
import com.daitoj.tkms.modules.common.service.ConflictException;
import com.daitoj.tkms.modules.common.service.NumberService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.PasswordUtils;
import jakarta.mail.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailSendException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/** 顧客情報登録ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class R0060Service {

  private static final Logger LOG = LoggerFactory.getLogger(R0060Service.class);

  /** 顧客情報登録のリポジトリ. */
  private final R0060Repository r0060Repository;

  private final R0060Mapper r0060Mapper;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** 採番サービス */
  private final NumberService numberRuleService;

  /** 採番項目ID(顧客コード) */
  private static final String FIELD_ID_CUSTOMER_CD = "CUSTOMER_CD";

  /** 採番項目ID(顧客支店コード) */
  private static final String FIELD_ID_CUSTOMER_BRANCH_CD = "CUSTOMER_BRANCH_CD";

  /** コンストラクタ. */
  public R0060Service(
      R0060Repository r0060Repository,
      R0060Mapper r0060Mapper,
      MessageSource messageSource,
      NumberService numberRuleService) {
    this.r0060Repository = r0060Repository;
    this.r0060Mapper = r0060Mapper;
    this.messageSource = messageSource;
    this.numberRuleService = numberRuleService;
  }

  /**
   * 初期表示
   *
   * @return 選択項目の選択肢を取得結果
   */
  public ApiResult<R0060S01ReturnData> getInitInfo() {
    try {

      return ApiResult.success(null);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 初期表示
   *
   * @return 顧客情報を取得結果
   */
  public ApiResult<R0060S01ReturnData> getKokyaku(String customerCd) {
    try {
      Optional<CustomerBankBankBanchInfoDto> customerApprInfo =
          r0060Repository.getKokyaku(customerCd);
      if (!customerApprInfo.isPresent()) {
        String msg =
            messageSource.getMessage(Message.MSGID_K00020, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00020, msg);
      }
      R0060S01ReturnData result = new R0060S01ReturnData();
      result.setCustomerBankBankBanchInfoDto(customerApprInfo.get());
      result.setListProjectSiteInfoDto(
          r0060Repository.getTProjectSiteInfoDto(
              customerCd, DateUtils.formatNow(DateUtils.DATE_FORMAT)));
      return ApiResult.success(result);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 社員管理登録情報保存
   *
   * @param inDto 社員管理登録情報保存パラメータ
   * @return 社員コード
   */
  public ApiResult<R0060S01ReturnData> saveKokyaku(CustomerBankBankBanchInfoDto inDto) {
    try {

      Optional<CustomerInfoDto> customerInfo =
          r0060Repository.findByMCustomer(inDto.getCustomerCd());
      MCustomer mCustomer = r0060Mapper.toMCustomerEntity(inDto);
      mCustomer.setDelFlg(CommonConstants.DELETE_FLAG_VALID);

      if (!customerInfo.isPresent()
          || customerInfo
              .get()
              .getBusinessDataSt()
              .equals(CommonConstants.APPR_ST_PENDING_APPROVAL)) {
        // 顧客コード
        String customerCd = mCustomer.getCustomerCd();

        List<String> customerCdList =
            r0060Repository.findAll().stream().map(MCustomer::getCustomerCd).toList();
        if (customerCd == null) {
          // 顧客コードを採番
          for (int i = 0; i < 200; i++) {
            customerCd = numberRuleService.getNextNumberByFieldId(FIELD_ID_CUSTOMER_CD, null, null);
            if (!customerCdList.contains(customerCd)) {
              break;
            }
            if (i == 199) {
              throw new SystemException(
                  messageSource.getMessage(
                      Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
            }
          }
          mCustomer.setCustomerCd(customerCd);
        }
        String customerBranchCd =
            numberRuleService.getNextNumberByFieldId(FIELD_ID_CUSTOMER_BRANCH_CD, null, null);
        mCustomer.setBranchCd(customerBranchCd);
        // 顧客支店コード
        mCustomer.setCustomerBranchCd(customerCd + customerBranchCd);
        mCustomer.setHisNo(1);
        mCustomer.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
        r0060Repository.save(mCustomer);
      } else if (customerInfo.get().getBusinessDataSt().equals(CommonConstants.BUSINESS_DATA_ST_REQ)
          || customerInfo.get().getBusinessDataSt().equals(CommonConstants.APPR_ST_RETURN)) {
        mCustomer.setId(customerInfo.get().getId());
        mCustomer.setHisNo(customerInfo.get().getHisNo());
        mCustomer.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
        r0060Repository.save(mCustomer);
      } else if (customerInfo.get().getBusinessDataSt().equals(CommonConstants.APPR_ST_PASS)) {
        // 承認状態が「承認」の場合は、最新履歴の最新フラグを更新する
        Optional<MCustomer> mc = r0060Repository.findById(customerInfo.get().getId());
        mc.get().setNewestFlg(CommonConstants.NEWEST_FLAG_HIS);
        r0060Repository.save(mc.get());
        // 画面の情報を最新履歴として追加する。
        String customerBranchCd =
            numberRuleService.getNextNumberByFieldId(FIELD_ID_CUSTOMER_BRANCH_CD, null, null);
        mCustomer.setBranchCd(customerBranchCd);
        // 顧客支店コード
        mCustomer.setCustomerBranchCd(mCustomer.getCustomerCd() + customerBranchCd);
        mCustomer.setHisNo(customerInfo.get().getHisNo() + 1);
        mCustomer.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
        r0060Repository.save(mCustomer);
      }
      String msg =
          messageSource.getMessage(Message.MSGID_K00003, null, LocaleContextHolder.getLocale());

      LOG.info(msg);

      // 結果情報
      return ApiResult.error(Message.MSGID_K00003, msg);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_K00006, null, LocaleContextHolder.getLocale()));
    }
  }
}
