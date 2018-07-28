package com.cloud99.invest.integration.zillow.results;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class HighPriceDeserializer extends JsonDeserializer<High> {

	@Override
	public High deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);

		High high = new High();
		JsonNode content = node.get("");

		if (content == null) {
			return high;
		}
		high.setContent(content.asDouble());
		high.setCurrency(node.get("currency").asText());

		return high;
	}

}
