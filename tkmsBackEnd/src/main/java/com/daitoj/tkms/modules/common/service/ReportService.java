package com.daitoj.tkms.modules.common.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/** レポートサービス */
@Service
public class ReportService {
  private static final Logger LOG = LoggerFactory.getLogger(ReportService.class);

  // テンプレートのテンプレートフォルダ
  @Value("${jasper.template.path}")
  private String templatePath;

  private final ResourceLoader resourceLoader;

  /** コンストラクタ */
  public ReportService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  /**
   * レポートを生成するメソッド
   *
   * @param fileName レポートファイル名
   * @param parameters レポートに渡すパラメータ
   * @return 生成したレポートのバイト配列（PDFなど）
   */
  public byte[] exportReportToPdf(
      String fileName, Map<String, Object> parameters, JRDataSource dataSource)
      throws JRException, IOException {

    Resource resource = resourceLoader.getResource(templatePath + fileName);

    // ファイルチェック
    if (!resource.exists()) {
      throw new IOException();
    }

    try (InputStream inputStream = resource.getInputStream()) {
      // テンプレートにパラメータをセットして、レポートを生成する
      JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
      // 生成したレポートをPDFとしてエクスポートする
      return JasperExportManager.exportReportToPdf(jasperPrint);
    }
  }
}
