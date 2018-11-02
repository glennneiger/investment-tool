package com.cloud99.invest.integration.data;

/**
 * Used to convert from one object type to another.
 */
public interface MessageConverter<INPUT_VAL, RETURN_VAL> {

	public RETURN_VAL convert(INPUT_VAL incoming);

}