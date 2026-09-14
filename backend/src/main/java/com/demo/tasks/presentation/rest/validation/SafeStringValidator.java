package com.demo.tasks.adapter.rest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class SafeStringValidator
        implements ConstraintValidator<SafeString, String> {

    private static final Pattern SAFE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9 _-]+$");

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return SAFE_PATTERN.matcher(value).matches();
    }
}