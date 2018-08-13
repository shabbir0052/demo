package com.csaa.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.csaa.bo.ConvertedPolicyInfoRequest;



import aaancnu_common_version2.com.aaancnuit.ApplicationContext;
import aaancnu_retrieveconvertedpolicyinfo_version2.com.aaancnuit.PolicySource;
import aaancnu_wsdl_retrieveconvertedpolicyinfo_version2.com.aaancnuit.RetrieveConvertedPolicyInfoRequest;

@Component
public class RequestProcessor {
    private static final Logger LOG = LoggerFactory
    		.getLogger(RequestProcessor.class);
	
	public RetrieveConvertedPolicyInfoRequest transformToConveredPolicySOAPServiceRequest(com.csaa.bo.ConvertedPolicyInfo policyInfoJson) {
		RetrieveConvertedPolicyInfoRequest output=null;
    	try {
    		if (policyInfoJson==null) {
    			throw new Exception
    			("API Request  was not bound to the method via"
        				+ "integeration framework");
    		}
    		output = processCreateConveredPolicySOAPServiceRequest(policyInfoJson);
    		
    	} catch(Exception e) {
    		LOG.error("ConverPolicyInfo message transalation failed  ", e);
    	}
    	return output;
	}
	
	protected RetrieveConvertedPolicyInfoRequest processCreateConveredPolicySOAPServiceRequest(com.csaa.bo.ConvertedPolicyInfo policyInfoJson)
			 throws Exception {
		
		ApplicationContext appCtx= new ApplicationContext();
		appCtx.setApplication("CamelPOC");
		appCtx.setSubSystem("ConvertedPolicyAPI");
		appCtx.setCorrelationId("1234");
		RetrieveConvertedPolicyInfoRequest output = new RetrieveConvertedPolicyInfoRequest();
		output.setApplicationContext(appCtx);
		PolicySource policySource= new PolicySource();
		policySource.setPolicyNumber(policyInfoJson.getConvertedPolicyInfoRequest().getPolicyInfo().getPolicyNumber());
		List<PolicySource> policySources= output.getPolicy();
		policySources.add(policySource);
		
		return output;
						
	}
	
	public RetrieveConvertedPolicyInfoRequest transformToConveredPolicySOAPServiceRequestString(String policyInfoJson) {
		RetrieveConvertedPolicyInfoRequest output=null;
    	try {
    		if (policyInfoJson==null) {
    			throw new Exception
    			("API Request  was not bound to the method via"
        				+ "integeration framework");
    		}
    		LOG.info("-->"+policyInfoJson);
    		
    	} catch(Exception e) {
    		LOG.error("ConverPolicyInfo message transalation failed  ", e);
    	}
    	 
    	return output;
	}

}