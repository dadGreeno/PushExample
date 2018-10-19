package org.wwc.push.domain.response.platform;

import com.fasterxml.jackson.annotation.JsonView;

public class PlatformApplicationResponse {
	
	@JsonView
	private String status = "failure";
	@JsonView
	private String error = "";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
