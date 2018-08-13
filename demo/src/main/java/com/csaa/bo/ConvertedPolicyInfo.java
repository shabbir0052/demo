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
//@JsonPropertyOrder({ "convertedPolicyInfoRequest" })
public class ConvertedPolicyInfo {

	//@JsonProperty("convertedPolicyInfoRequest")
	private ConvertedPolicyInfoRequest convertedPolicyInfoRequest;

	

	//@JsonProperty("convertedPolicyInfoRequest")
	public ConvertedPolicyInfoRequest getConvertedPolicyInfoRequest() {
		return convertedPolicyInfoRequest;
	}

	//@JsonProperty("convertedPolicyInfoRequest")
	public void setConvertedPolicyInfoRequest(ConvertedPolicyInfoRequest convertedPolicyInfoRequest) {
		this.convertedPolicyInfoRequest = convertedPolicyInfoRequest;
	}


}