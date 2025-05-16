package com.daitoj.tkms.modules.common.service.dto;

import java.util.List;

public class ErrorResponse {
    private ErrorDetail error;

    // 添加无参构造函数
    public ErrorResponse() {
    }

    // 带参数的构造函数
    public ErrorResponse(String code, String message, String detail, String path, List<ValidationError> validationErrors) {
        this.error = new ErrorDetail(code, message, detail, path, validationErrors);
    }

    // 公共的 getter 方法
    public ErrorDetail getError() {
        return error;
    }

    public static class ErrorDetail {
        private String code;
        private String message;
        private String detail;
        private String path;
        private List<ValidationError> validationErrors;

        // 添加无参构造函数
        public ErrorDetail() {
        }

        // 带参数的构造函数
        public ErrorDetail(String code, String message, String detail, String path, List<ValidationError> validationErrors) {
            this.code = code;
            this.message = message;
            this.detail = detail;
            this.path = path;
            this.validationErrors = validationErrors;
        }

        // 公共的 getter 方法
        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getDetail() {
            detail = detail;
            return detail;
        }

        public String getPath() {
            return path;
        }

        public List<ValidationError> getValidationErrors() {
            return validationErrors;
        }
    }

    public static class ValidationError {
        private String field;
        private String message;

        // 添加无参构造函数
        public ValidationError() {
        }

        // 带参数的构造函数
        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        // 公共的 getter 方法
        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
