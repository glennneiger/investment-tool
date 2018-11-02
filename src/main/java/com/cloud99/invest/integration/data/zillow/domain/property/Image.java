package com.cloud99.invest.integration.data.zillow.domain.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URL;
import java.util.List;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {
	private List<URL> url;
}
