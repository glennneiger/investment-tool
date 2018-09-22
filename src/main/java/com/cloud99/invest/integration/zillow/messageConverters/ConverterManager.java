package com.cloud99.invest.integration.zillow.messageConverters;

public interface ConverterManager<INPUT_VAR, RETURN_VAL> {

	RETURN_VAL convert(INPUT_VAR input);

}