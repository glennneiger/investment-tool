package com.cloud99.invest.integration.data.zillow.deserializers;

import com.cloud99.invest.integration.data.zillow.domain.search.Low;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class LowPriceDeserializer extends JsonDeserializer<Low> {

	@Override
	public Low deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);

		Low low = new Low();
		JsonNode content = node.get("");
		if (content == null) {
			return low;
		}
		low.setContent(content.asDouble());
		low.setCurrency(node.get("currency").asText());
		return low;
	}

}
