package com.cloud99.invest.integration.data.zillow.converterManagers;

import com.cloud99.invest.integration.data.zillow.domain.ZillowApiResult;

public interface MessageConverterManager<RETURN_TYPE, T extends ZillowApiResult> {

	public RETURN_TYPE convert(Object zillowResult);

	public Class<? extends ZillowApiResult> getConvertFromType();

	public Class<?> getConvertToType();

}
