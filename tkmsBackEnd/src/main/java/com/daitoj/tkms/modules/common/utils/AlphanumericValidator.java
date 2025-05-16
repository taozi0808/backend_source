package com.daitoj.tkms.modules.common.utils;

import com.daitoj.tkms.modules.common.constants.Message;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class AlphanumericValidator implements ConstraintValidator<Alphanumeric, String> {

    private Alphanumeric annotation; // 保存注解实例
    @Autowired
    private MessageSource messageSource;

    @Override
    public void initialize(Alphanumeric constraintAnnotation) {
        this.annotation = constraintAnnotation; // 初始化注解
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // 允许 null（由 @NotNull 处理）
        if (!value.matches("^[a-zA-Z0-9]*$")) {
            // 通过注解参数获取动态值
            String param = this.annotation.param();
            // 加载消息模板并替换参数
            String message = messageSource.getMessage(
                Message.MSGID_K00021,
                new Object[]{param},
                LocaleContextHolder.getLocale()
            );
            // 设置动态消息
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
