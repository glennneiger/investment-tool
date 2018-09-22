package com.cloud99.invest.converters.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.joda.money.Money;

import java.io.IOException;

public class MoneySerializer extends JsonSerializer<Money> {

	@Override
	public void serialize(Money value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("amount", String.valueOf(value.getAmount().longValue()));
		jgen.writeStringField("currencyCode", value.getCurrencyUnit().getCurrencyCode());
		jgen.writeEndObject();
	}

}
