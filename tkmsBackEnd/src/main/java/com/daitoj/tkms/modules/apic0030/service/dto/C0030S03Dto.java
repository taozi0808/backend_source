package com.daitoj.tkms.modules.apic0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

/** 添付ファイル明細パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0030S03Dto", description = "添付ファイル明細パラメータ")
public class C0030S03Dto extends BaseDto {

    /** 連番 */
    @Schema(name = "seqNo", description = "連番")
    private Integer seqNo;

    /** 添付ファイル */
    @Schema(name = "files", description = "添付ファイル")
    MultipartFile files;

    /** ファイルURL */
    @Schema(name = "fileUrl", description = "添付ファイルURL")
    private String fileUrl;

    /** ファイル名 */
    @Schema(name = "fileName", description = "添付ファイル名")
    private String fileName;


}
