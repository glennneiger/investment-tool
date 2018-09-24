package com.cloud99.invest.integration;

import com.cloud99.invest.dto.responses.PropertyValuationResult;

/**
 * Used to convert from one object type to another.
 */
public interface GenericMessageConverter<INPUT_VAL> {

	public <T extends PropertyValuationResult> T convert(INPUT_VAL incoming, Class<T> returnClassType);

}