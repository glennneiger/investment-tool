package com.cloud99.invest.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

public interface MongoDocument extends Serializable {

	@Id
	public String getId();

	default String toJsonString() {
		try {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toString();
	}
}
