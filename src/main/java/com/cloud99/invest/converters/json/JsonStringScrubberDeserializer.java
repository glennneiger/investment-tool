package com.cloud99.invest.converters.json;

import com.cloud99.invest.util.Util;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonStringScrubberDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String incomingVal = null;
		try {
			JsonNode node = jp.getCodec().readTree(jp);
			if (node != null && !StringUtils.isEmpty(node.asText())) {
				incomingVal = node.asText();
				return Util.removeSpecialCharacters(incomingVal);
			}
		} catch (Exception e) {
			log.error("Error scrubbing json string value, msg: " + e.getMessage(), e);
		}
		return incomingVal;
	}

}
