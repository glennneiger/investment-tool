package com.cloud99.invest.converters.json;

import com.cloud99.invest.exceptions.ServiceException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.apache.commons.lang3.StringUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrencyUnitDeserializer extends JsonDeserializer<CurrencyUnit> {

	@Override
	public CurrencyUnit deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		String currencyCode = null;

		try {
			JsonNode node = jp.getCodec().readTree(jp);
			currencyCode = node.asText();
			if (!StringUtils.isEmpty(currencyCode)) {
				return CurrencyUnit.getInstance(currencyCode);
			}
			// default value
			return getDefaulCurrency();

		} catch (IllegalCurrencyException e) {
			log.error("Error converting currency unit: " + e.getMessage(), e);
			throw new ServiceException("currency.code.unknown", null, currencyCode);
		} catch (Throwable t) {
			log.error("Unhandled exception caught converting currency unit: " + t.getMessage(), t);
			return getDefaulCurrency();
		}

	}

	private CurrencyUnit getDefaulCurrency() {
		return CurrencyUnit.USD;
	}

}
