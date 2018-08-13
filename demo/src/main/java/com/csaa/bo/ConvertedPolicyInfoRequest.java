package com.csaa.bo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({ "policyInfo" })
public class ConvertedPolicyInfoRequest {

	//@JsonProperty("policyInfo")
	private PolicyInfo policyInfo;


	//@JsonProperty("policyInfo")
	public PolicyInfo getPolicyInfo() {
		return policyInfo;
	}

	//@JsonProperty("policyInfo")
	public void setPolicyInfo(PolicyInfo policyInfo) {
		this.policyInfo = policyInfo;
	}



}
