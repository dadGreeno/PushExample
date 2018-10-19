package org.wwc.push.aws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wwc.push.AwsUtils;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.AuthorizationErrorException;
import com.amazonaws.services.sns.model.CreatePlatformApplicationRequest;
import com.amazonaws.services.sns.model.CreatePlatformApplicationResult;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.InternalErrorException;
import com.amazonaws.services.sns.model.InvalidParameterException;
import com.amazonaws.services.sns.model.NotFoundException;
import com.amazonaws.services.sns.model.PlatformApplication;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AmazonSNSClientWrapper {
	
	private final AmazonSNS snsClient;
	private final List<PlatformApplication> platformApps;
	
	public AmazonSNSClientWrapper(AmazonSNS client, List<PlatformApplication> list) {
		this.snsClient = client;
		this.platformApps = list;
	}
	
	public PublishResult publish(String arn, Map<String, Map<String, String>> data,
			AwsUtils awsUtils) {
		String platform = "";
		if (data.keySet().iterator().hasNext()) {
			platform = data.keySet().iterator().next();
		}
		
		if (platform.equalsIgnoreCase("")) {
			throw new InvalidParameterException("Must be GCM");
		}
		
		PublishRequest request = new PublishRequest();
		request.setMessageStructure("json");
		Map<String, Object> androidMsg = new HashMap<String, Object>();
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put("text", data.get(platform).get("text"));
		androidMsg.put("data", messageMap);
		String message = jsonify(androidMsg);
		
		
		
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put("GCM", message);
		
		String sendMsg = jsonify(msgMap);
		
		request.setTargetArn(arn);
		request.setMessage(sendMsg);
		
		return snsClient.publish(request);
		
	}
	
	public String createDeviceEndpoint(String customData, String token, String platformAppArn) throws AuthorizationErrorException, InternalErrorException, InvalidParameterException, NotFoundException {
		CreatePlatformEndpointResult result = new CreatePlatformEndpointResult();
		CreatePlatformEndpointRequest request = new CreatePlatformEndpointRequest();
		request.setCustomUserData(customData);
		request.setToken(token);
		request.setPlatformApplicationArn(platformAppArn);
		result  = snsClient.createPlatformEndpoint(request);
		return result.getEndpointArn();
		
	}
	
	public String createPlatformApplicationEndpoint(String platform, String principal, String credential, String applicationName) throws AuthorizationErrorException, InternalErrorException, InvalidParameterException, NotFoundException {
		String platformApplicationArn = getExistingPlatform(platform, applicationName);
		
		if (platformApplicationArn.equalsIgnoreCase("")) {
			CreatePlatformApplicationResult result = createPlatformApplication(applicationName, platform, principal, credential);
			
			platformApplicationArn = result.getPlatformApplicationArn();
			
//			SetPlatformApplicationAttributesRequest request = new SetPlatformApplicationAttributesRequest();
//			Map<String, String> attributes = new HashMap<String, String>();
//			
//			attributes.put("SuccessFeedbackRoleArn", "arn:aws.iam:xxxx:role/SNSSuccessFeedback");//setup in AWS role
//			attributes.put("FailureFeedbackRoleArn", "arn:aws.iam:xxxx:role/SNSFailureFeedback");//setup in AWS role
//			attributes.put("SuccessFeedbackSampleRate", "100");//100%
//			request.withAttributes(attributes);
//			request.setPlatformApplicationArn(platformApplicationArn);
//			
//			snsClient.setPlatformApplicationAttributes(request);
		}
		return platformApplicationArn;
	}
	
	private CreatePlatformApplicationResult createPlatformApplication(String applicationName, String platform,
			String principal, String credential)
			throws AuthorizationErrorException, InternalErrorException, InvalidParameterException {

		CreatePlatformApplicationRequest createPlatformApplicationRequest = new CreatePlatformApplicationRequest();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("PlatformPrincipal", principal);
		attributes.put("PlatformCredential", credential);
		createPlatformApplicationRequest.setAttributes(attributes);
		createPlatformApplicationRequest.setName(applicationName);
		createPlatformApplicationRequest.setPlatform(platform);

		return snsClient.createPlatformApplication(createPlatformApplicationRequest);
	}
	
	public String getExistingPlatform(String platform, String applicationName) {
		for (PlatformApplication pa: platformApps) {
			if (pa.getPlatformApplicationArn().contains(platform)
					&& pa.getPlatformApplicationArn().contains(applicationName)) {
				return pa.getPlatformApplicationArn();
			}
		}
		return "";
	}
	
	public static String jsonify(Object message) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(message);
		} catch (Exception e) {
			//log
			throw (RuntimeException) e;
		}
	}

}
