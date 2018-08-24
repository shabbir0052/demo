package com.csaa.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.csaa.bo.ConvertedPolicyInfoRequest;
import com.csaa.bo.ConvertedPolicySummary;
import com.csaa.bo.RetrieveConvertedPolicyInfoResponse;

import aaancnu_common_version2.com.aaancnuit.ApplicationContext;
import aaancnu_retrieveconvertedpolicyinfo_version2.com.aaancnuit.ConvertedPolicy;
import aaancnu_retrieveconvertedpolicyinfo_version2.com.aaancnuit.ConvertedPolicyInfo;
import aaancnu_retrieveconvertedpolicyinfo_version2.com.aaancnuit.PolicySource;
import aaancnu_wsdl_retrieveconvertedpolicyinfo_version2.com.aaancnuit.RetrieveConvertedPolicyInfoRequest;

@Component
public class RequestProcessor {
    private static final Logger LOG = LoggerFactory
    		.getLogger(com.csaa.processor.RequestProcessor.class);
	
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
		LOG.info(">>>>>>I am in processCreateConveredPolicySOAPServiceRequest <<<<<<<<<<<");
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
	
	public RetrieveConvertedPolicyInfoResponse processGetConvertedPolicyResp (aaancnu_wsdl_retrieveconvertedpolicyinfo_version2.com.aaancnuit.RetrieveConvertedPolicyInfoResponse resp) {
		
		LOG.info(">>>>>>I am in processGetConvertedPolicyResp <<<<<<<<<<<");

		RetrieveConvertedPolicyInfoResponse output= new RetrieveConvertedPolicyInfoResponse();
		List<ConvertedPolicyInfo> convertedPoliciesInfo= resp.getConvertedPolicyInfo();
		for ( ConvertedPolicyInfo ele:convertedPoliciesInfo) {
			ConvertedPolicy  convPol= ele.getConvertedPolicy();
			PolicySource sourcePol= ele.getRequestedPolicy();
			com.csaa.bo.ConvertedPolicy desConvPol= new com.csaa.bo.ConvertedPolicy();
			desConvPol.setPolicyNumber(convPol.getPolicyNumber());
			desConvPol.setDataSource(convPol.getDataSource());
			desConvPol.setType(convPol.getType());
			desConvPol.setStatus(convPol.getStatus());
			
			com.csaa.bo.RequestedPolicy destSourcePol = new com.csaa.bo.RequestedPolicy(); 
			destSourcePol.setPolicyNumber(sourcePol.getPolicyNumber());
			destSourcePol.setDataSource(sourcePol.getDataSource());
			com.csaa.bo.ConvertedPolicySummary convPolSumm= new com.csaa.bo.ConvertedPolicySummary();
			convPolSumm.setConvertedPolicy(desConvPol);
			convPolSumm.setRequestedPolicy(destSourcePol);
		    List<ConvertedPolicySummary> list=output.getConvertedPolicyInfo();
		    list.add(convPolSumm);        					 
			
		}
		
		
		return output;
		
	}
	
	
	
}
