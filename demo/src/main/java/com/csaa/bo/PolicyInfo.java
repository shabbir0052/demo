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
//@JsonPropertyOrder({ "policyNumber", "dataSource", "type" })
public class PolicyInfo {

	
	private String policyNumber;
	
	private String dataSource;
	
	private String type;
	
	//@JsonProperty("policyNumber")
	public String getPolicyNumber() {
		return policyNumber;
	}

	//@JsonProperty("policyNumber")
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	//@JsonProperty("dataSource")
	public String getDataSource() {
		return dataSource;
	}

	//@JsonProperty("dataSource")
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	//@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}


}