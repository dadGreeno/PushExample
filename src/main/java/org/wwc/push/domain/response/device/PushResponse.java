package org.wwc.push.domain.response.device;

import com.fasterxml.jackson.annotation.JsonView;

public class PushResponse {
	
	@JsonView
	private String status = "failure";
	@JsonView
	private String error = "";
	@JsonView
	private boolean endpointDisabled;
	@JsonView
	private String messageId;
	
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
	public boolean isEndpointDisabled() {
		return endpointDisabled;
	}
	public void setEndpointDisabled(boolean endpointDisabled) {
		this.endpointDisabled = endpointDisabled;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
