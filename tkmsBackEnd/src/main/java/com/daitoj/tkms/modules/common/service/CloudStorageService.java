package com.daitoj.tkms.modules.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

/** ファイルサービス */
public interface CloudStorageService {
  /**
   * ファイルアップロード
   *
   * @param files ファイル
   * @return ファイルIDリスト
   */
  List<UUID> upload(MultipartFile[] files);

  /**
   * ファイルをダウンロードする
   *
   * @param objectName オブジェクト名
   * @return ファイル
   */
  InputStream download(String objectName);

  /**
   * ファイルUrlを取得する
   *
   * @param objectName オブジェクト名
   * @return ファイルUrl
   */
  String createUrl(String objectName);

  /**
   * ファイルを削除する
   *
   * @param objectName オブジェクト名
   * @return 削除結果
   */
  boolean delete(String objectName);
}
