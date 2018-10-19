package org.wwc.push.domain.request.device;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CreateEndpointRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface ValidCreateEndpointRequest {
	Class<?>[] groups() default {};
	
	String message() default "{org.wwc.push.domain.request.ValidCreateEndpointRequest.message}";
	
	Class<? extends Payload>[] payload() default {};
	
}
