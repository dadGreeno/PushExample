package org.wwc.push;

import java.util.Map;

import javax.annotation.security.PermitAll;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wwc.push.aws.AmazonSNSClientWrapper;
import org.wwc.push.domain.request.device.CreateDeviceEndpointRequest;
import org.wwc.push.domain.request.device.PushRequest;
import org.wwc.push.domain.request.platform.PlatformApplicationRequest;
import org.wwc.push.domain.response.device.CreateDeviceEndpointResponse;
import org.wwc.push.domain.response.device.PushResponse;
import org.wwc.push.domain.response.platform.PlatformApplicationResponse;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.EndpointDisabledException;
import com.amazonaws.services.sns.model.PublishResult;

@RestController
public class DeviceSetupController {
	
	private AwsUtils awsUtils = new AwsUtils();
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    @PermitAll
	@RequestMapping(value = "awsCreatePlatformRequest", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MappingJacksonValue> createPlatform(@RequestBody PlatformApplicationRequest request) {
    	AmazonSNS snsClient = awsUtils.buildAwsInstance();
    	AmazonSNSClientWrapper wrapper = new AmazonSNSClientWrapper(snsClient, awsUtils.listPlatformApps(snsClient));
    	
    	String arn = "";
    	
    	Map<String, String> platformMap = request.getAttributesMap();
    	String platform = platformMap.get("mobilePlatform");
    	PlatformApplicationResponse response = new PlatformApplicationResponse();
    	
    	try {
    		arn = wrapper.createPlatformApplicationEndpoint(platform, "", platformMap.get("serverApiKey"), platformMap.get("applicationName"));
    		response.setStatus("OK");
    	} catch (AmazonSNSException e) {
    		response.setError(e.getMessage());
    	}
    	
    	MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(response);
    	
		return new ResponseEntity<MappingJacksonValue>(mappingJacksonValue, HttpStatus.OK);
	}

    @PermitAll
	@RequestMapping(value = "awsUpdatePlatformRequest", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MappingJacksonValue> updatePlatform(@RequestBody PlatformApplicationRequest request) {
    	
    	return null;
	}

    @PermitAll
	@RequestMapping(value = "awsCreateDeviceEndpointRequest", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MappingJacksonValue> createDeviceEndpoint(@RequestBody CreateDeviceEndpointRequest request) {
    	CreateDeviceEndpointResponse response = new CreateDeviceEndpointResponse();
    	
    	AmazonSNS snsClient = awsUtils.buildAwsInstance();
    	AmazonSNSClientWrapper wrapper = new AmazonSNSClientWrapper(snsClient, awsUtils.listPlatformApps(snsClient));
    	
    	String arn = "";
    	
    	try {
    		arn = wrapper.createDeviceEndpoint(request.getDeviceId(), request.getPlatformToken(), wrapper.getExistingPlatform(request.getMobilePlatform(), request.getApplicationName()));
    		response.setEndpointArn(arn);
    		response.setStatus("OK");
    		response.setError("");
    	} catch (AmazonSNSException e) {
    		response.setError(e.getMessage());
    	}
    	
    	MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(response);
    	
		return new ResponseEntity<MappingJacksonValue>(mappingJacksonValue, HttpStatus.OK);
	}

    @PermitAll
	@RequestMapping(value = "awsUpdateDeviceEndpointTokenRequest", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String updateDeviceEndpointToken() {
		return "Greetings from Spring Boot!";
	}

    @PermitAll
	@RequestMapping(value = "awsPushNotificationToDeviceRequest", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MappingJacksonValue> pushNotification(@RequestBody PushRequest request) {
    	PushResponse response = new PushResponse();
    	
    	AmazonSNS snsClient = awsUtils.buildAwsInstance();
    	AmazonSNSClientWrapper wrapper = new AmazonSNSClientWrapper(snsClient, awsUtils.listPlatformApps(snsClient));
    	
    	PublishResult result;
    	
    	//normally go get the device endpoint from a repository somewhere
    	
    	try {
    		result = wrapper.publish(request.getArn(), request.getData(), awsUtils);
    		response.setMessageId(result.getMessageId());
    		response.setStatus("OK");
    	} catch (EndpointDisabledException disabled) {
    		//TODO go remove the device or archive
    		response.setError(disabled.getMessage());
    		response.setEndpointDisabled(true);
    		//TODO log some info
    	} catch (AmazonSNSException e) {
    		response.setError(e.getMessage());
    	}
    	
    	
    	MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(response);
    	
		return new ResponseEntity<MappingJacksonValue>(mappingJacksonValue, HttpStatus.OK);
	}
    
    
    
}
