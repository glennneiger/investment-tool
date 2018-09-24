package com.cloud99.invest.converters.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.joda.money.Money;

import java.io.IOException;
import java.text.DecimalFormat;

public class MoneySerializer extends JsonSerializer<Money> {

	// TODO - NG - make the formatting a debug only feature as we don't want to send
	// clients the formatted values
	private DecimalFormat formatter = new DecimalFormat("$###,###,###");

	@Override
	public void serialize(Money value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("amount", formatter.format(value.getAmount().longValue()));
		jgen.writeStringField("currencyCode", value.getCurrencyUnit().getCurrencyCode());
		jgen.writeEndObject();
	}

}
