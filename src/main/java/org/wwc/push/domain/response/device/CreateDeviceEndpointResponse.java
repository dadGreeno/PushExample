package org.wwc.push.domain.response.device;

import com.fasterxml.jackson.annotation.JsonView;

public class CreateDeviceEndpointResponse {

	@JsonView
	private String status = "failure";
	@JsonView
	private String endpointArn = "";
	@JsonView
	private String error = "";
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEndpointArn() {
		return endpointArn;
	}
	public void setEndpointArn(String endpointArn) {
		this.endpointArn = endpointArn;
	}
}
