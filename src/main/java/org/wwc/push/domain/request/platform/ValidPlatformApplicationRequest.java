package org.wwc.push.domain.request.platform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PlatformApplicationRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface ValidPlatformApplicationRequest {
	Class<?>[] groups() default {};
	
	String message() default "{org.wwc.push.domain.request.ValidPlatformApplicationRequest.message}";
	
	Class<? extends Payload>[] payload() default {};
	
}
