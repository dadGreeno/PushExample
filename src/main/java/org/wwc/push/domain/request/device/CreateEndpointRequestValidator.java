package org.wwc.push.domain.request.device;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class CreateEndpointRequestValidator implements ConstraintValidator<ValidCreateEndpointRequest, CreateDeviceEndpointRequest> {
	
	@Override
	public void initialize(ValidCreateEndpointRequest constraintAnnotation) {}

	@Override
	public boolean isValid(CreateDeviceEndpointRequest request, ConstraintValidatorContext context) {
		String platform = request.getMobilePlatform();
		boolean pass = false;
		
		if (StringUtils.isEmpty(platform) ) {
			return false;
		}
		
		switch (platform) {
		case "GCM":
			if (!StringUtils.isEmpty(request.getApplicationName())
				&& !StringUtils.isEmpty(request.getDeviceId())
				&& !StringUtils.isEmpty(request.getPlatformToken())) {
				pass = true;
			}
			break;

		default:
			break;
		}
		
		return pass;
	}

}
