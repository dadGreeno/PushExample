package org.wwc.push.domain.request.device;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushRequest {
	
	@JsonProperty
	private String arn;
	@JsonProperty
	private Map<String, Map<String, String>> data;
	
	public String getArn() {
		return arn;
	}
	public void setArn(String arn) {
		this.arn = arn;
	}
	public Map<String, Map<String, String>> getData() {
		return data;
	}
	public void setData(Map<String, Map<String, String>> data) {
		this.data = data;
	}

}
