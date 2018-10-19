package org.wwc.push.domain.request.platform;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class PlatformApplicationRequestValidator implements ConstraintValidator<ValidPlatformApplicationRequest, PlatformApplicationRequest> {
	
	@Override
	public void initialize(ValidPlatformApplicationRequest constraintAnnotation) {}

	@Override
	public boolean isValid(PlatformApplicationRequest request, ConstraintValidatorContext context) {
		String platform = request.getAttributesMap().get("mobilePlatform");
		boolean pass = false;
		
		if (StringUtils.isEmpty(platform) ) {
			return false;
		}
		
		switch (platform) {
		case "GCM":
			if (request.getAttributesMap().containsKey("serverApiKey")
				&& !StringUtils.isEmpty(request.getAttributesMap().get("serverApiKey"))
				&& request.getAttributesMap().containsKey("applicationName")
				&& !StringUtils.isEmpty(request.getAttributesMap().get("applicationName"))) {
				pass = true;
			}
			break;

		default:
			break;
		}
		
		return pass;
	}

}
