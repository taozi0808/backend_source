package com.daitoj.tkms.modules.common.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AlphanumericValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Alphanumeric {
    String message() default "{0}只能包含半角英数字";
    String param();
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
