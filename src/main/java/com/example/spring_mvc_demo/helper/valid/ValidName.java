package com.example.spring_mvc_demo.helper.valid;

import com.example.spring_mvc_demo.helper.exception.ErrorCode;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NameValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {

    String message() default "Name must be between 6 and 50 characters and start with 'ITS'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}