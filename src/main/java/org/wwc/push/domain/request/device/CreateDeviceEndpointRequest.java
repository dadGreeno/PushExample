package org.wwc.push.domain.request.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateDeviceEndpointRequest {
	
	@JsonProperty
	private String mobilePlatform;
	@JsonProperty
	private String platformToken;
	@JsonProperty
	private String applicationName;
	@JsonProperty
	private String deviceId;
	
	public String getMobilePlatform() {
		return mobilePlatform;
	}
	public void setMobilePlatform(String mobilePlatform) {
		this.mobilePlatform = mobilePlatform;
	}
	public String getPlatformToken() {
		return platformToken;
	}
	public void setPlatformToken(String platformToken) {
		this.platformToken = platformToken;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
