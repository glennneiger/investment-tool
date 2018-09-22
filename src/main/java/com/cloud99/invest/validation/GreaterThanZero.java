package com.cloud99.invest.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, CONSTRUCTOR, PARAMETER, ANNOTATION_TYPE })
@Constraint(validatedBy = GreaterThanZeroValidator.class)
public @interface GreaterThanZero {

	String message() default "{validation.greater.then.zero.required}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
