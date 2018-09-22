package com.cloud99.invest.integration.zillow.results.comps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowRequest {
	private String count;

	private String zpid;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getZpid() {
		return zpid;
	}

	public void setZpid(String zpid) {
		this.zpid = zpid;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
