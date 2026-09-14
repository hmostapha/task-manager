package com.demo.tasks.adapter.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SafeStringValidator.class)
public @interface SafeString {

    String message() default "must contain only letters, numbers, '-' or '_'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
