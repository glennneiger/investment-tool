package com.cloud99.invest.integration.data.zillow.messageConverters;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.integration.data.MessageConverter;
import com.cloud99.invest.integration.data.zillow.domain.ZillowAddress;

import org.apache.commons.lang3.StringUtils;

public class ZillowAddressConverter implements MessageConverter<ZillowAddress, Address> {

	@Override
	public Address convert(ZillowAddress zAddress) {

		Address address = new Address();
		address.setAddress1(zAddress.getStreet());
		address.setCity(zAddress.getCity());
		address.setZip(zAddress.getZipcode());
		address.setState(zAddress.getState());
		if (!StringUtils.isBlank(zAddress.getLatitude())) {
			address.setLatitude(Double.valueOf(zAddress.getLatitude()));
		}
		if (!StringUtils.isBlank(zAddress.getLongitude())) {
			address.setLongitude(Double.valueOf(zAddress.getLongitude()));
		}
		return address;
	}

}
