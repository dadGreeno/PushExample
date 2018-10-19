package org.wwc.push;

import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.ListPlatformApplicationsResult;
import com.amazonaws.services.sns.model.PlatformApplication;

public class AwsUtils {
	
	public AmazonSNS buildAwsInstance() throws AmazonClientException {
		ClientConfiguration cfg = new ClientConfiguration();
		cfg.setProtocol(Protocol.HTTPS);
		
		return AmazonSNSClientBuilder.standard().withClientConfiguration(cfg).withRegion(Regions.US_EAST_1)
				.build();
	}
	
	public List<PlatformApplication> listPlatformApps(AmazonSNS sns) {
		ListPlatformApplicationsResult listResults = sns.listPlatformApplications();
		
		return listResults.getPlatformApplications();
	}
	

}
