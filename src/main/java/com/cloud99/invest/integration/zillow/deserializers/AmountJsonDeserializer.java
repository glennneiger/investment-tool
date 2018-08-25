package com.cloud99.invest.integration.zillow.deserializers;

import com.cloud99.invest.integration.zillow.results.Amount;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class AmountJsonDeserializer extends JsonDeserializer<Amount> {

	@Override
	public Amount deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		Amount amount = new Amount();

		JsonNode content = node.get("");
		if (content == null) {
			return amount;
		}
		amount.setContent(content.asDouble());
		amount.setCurrency(node.get("currency").asText());
		return amount;
	}

}
