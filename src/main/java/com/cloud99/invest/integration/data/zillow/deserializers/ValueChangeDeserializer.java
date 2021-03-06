package com.cloud99.invest.integration.data.zillow.deserializers;

import com.cloud99.invest.integration.data.zillow.domain.search.ValueChange;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

public class ValueChangeDeserializer extends JsonDeserializer<ValueChange> {

	@Override
	public ValueChange deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);

		ValueChange obj = new ValueChange();
		JsonNode content = node.get("");
		if (content == null) {
			return obj;
		}
		obj.setContent(new BigDecimal(content.asText()));
		obj.setCurrency(node.get("currency").asText());
		obj.setDuration(node.get("duration").asInt());
		return obj;
	}

}
