package com.cloud99.invest.dto.requests;

import com.cloud99.invest.converters.json.JsonStringScrubberDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class PropertySearchRequest implements Serializable {
	private static final long serialVersionUID = -2410996594281129536L;

	@Getter
	@Setter
	@NotEmpty(message = "address1.required")
	@JsonDeserialize(using = JsonStringScrubberDeserializer.class)
	private String address1;

	@Getter
	@Setter
	@JsonDeserialize(using = JsonStringScrubberDeserializer.class)
	private String address2;

	@Getter
	@Setter
	@NotEmpty(message = "state.required")
	@JsonDeserialize(using = JsonStringScrubberDeserializer.class)
	private String state;

	@Getter
	@Setter
	@NotEmpty(message = "city.required")
	@JsonDeserialize(using = JsonStringScrubberDeserializer.class)
	private String city;

	@Getter
	@Setter
	@NotEmpty(message = "zip.required")
	@JsonDeserialize(using = JsonStringScrubberDeserializer.class)
	private String zip;

	@Getter
	@Setter
	private boolean includeRentEstimate = false;

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
