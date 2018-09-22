package com.cloud99.invest.converters.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.joda.money.CurrencyUnit;

import java.io.IOException;

public class CurrencyUnitSerializer extends JsonSerializer<CurrencyUnit> {

	@Override
	public void serialize(CurrencyUnit value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {

		jgen.writeStartObject();
		jgen.writeStringField("currencyCode", value.getCurrencyCode());
		jgen.writeEndObject();
	}

}
