package com.example.spring_mvc_demo.helper.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

//    @Override
//    public void initialize(ValidName constraintAnnotation) {
//        // Khởi tạo nếu cần thiết
//    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value.length() < 6 || value.length() > 50) {
            return false;
        }
        return value.startsWith("ITS");
    }
}
