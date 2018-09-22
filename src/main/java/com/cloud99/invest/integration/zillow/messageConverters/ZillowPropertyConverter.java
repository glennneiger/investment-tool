package com.cloud99.invest.integration.zillow.messageConverters;

import com.cloud99.invest.domain.financial.TaxAssessment;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.PropertyType;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.integration.MessageConverter;
import com.cloud99.invest.integration.zillow.results.ZillowResult;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZillowPropertyConverter<T extends Property> implements MessageConverter<ZillowResult, T> {

	/*
	 * (non-Javadoc) Not that this method doesn't use the Class<T> returnVal since
	 * properties are dynamically created
	 * 
	 * @see
	 * com.cloud99.invest.integration.MessageConverter#convert(java.lang.Object,
	 * java.lang.Class)
	 */
	@Override
	public T convert(ZillowResult incomingResult, Class<T> returnVal) {

		T p = mapProperty(incomingResult);
		
		p.setBathRooms(incomingResult.getBathrooms());
		p.setBedRooms(incomingResult.getBedrooms());
		p.setFinishedSqFt(incomingResult.getFinishedSqFt());
		p.setLotSizeSqFt(incomingResult.getLotSizeSqFt());
		p.setYearBuilt(incomingResult.getYearBuilt());
		p.setTaxAssessment(new TaxAssessment(incomingResult.getTaxAssessmentYear(), incomingResult.getTaxAssessment()));
		p.setLastSoldDate(incomingResult.getLastSoldDate());
		p.setLastSoldPrice(new BigDecimal(incomingResult.getLastSoldPrice().getContent()));

		return p;
	}

	@SuppressWarnings("unchecked")
	private T mapProperty(ZillowResult incomingResult) {

		T p = null;
		if (StringUtils.isBlank(incomingResult.getUseCode())) {
			// default to single family property if not specified in returned provider data
			p = (T) new SingleFamilyProperty();
		} else {
			PropertyType ourPropertyType = mapPropertyType(incomingResult.getUseCode());

			try {
				p = (T) ourPropertyType.getPropertyClassType().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("Error occurred creating a new property type instance for zillow useCode: " + incomingResult.getUseCode() + ", error is: " + e.getMessage(), e);
				// default to single family if any exceptions are encountered
				p = (T) new SingleFamilyProperty();
			}
		}

		return p;
	}

	/**
	 * Maps Zillow UseCode types to our PropertyType.
	 * 
	 * If we add new ProperType values then this should be updated to map to the
	 * zillow type.
	 * 
	 * @param useCode
	 *            received from Zillow API
	 * @return the associated <code>PropertyType</code>
	 */
	private PropertyType mapPropertyType(String useCode) {

		if ("Unknown".equalsIgnoreCase(useCode)) {
			return PropertyType.SINGLE_FAMILY;
		} else if ("SingleFamily".equalsIgnoreCase(useCode)) {
			return PropertyType.SINGLE_FAMILY;
		} else if (isValueInList(useCode, "Duplex", "Triplex", "Quadruplex", "MultiFamily2To4", "MultiFamily5Plus")) {
			return PropertyType.MULTI_FAMILY;
		} else if (isValueInList(useCode, "Condominium", "Cooperative")) {
			return PropertyType.CONDO;
		} else {
			return PropertyType.SINGLE_FAMILY;
		}
	}

	private boolean isValueInList(String useCode, String... list) {
		return Arrays.stream(list).anyMatch(item -> item.equalsIgnoreCase(useCode));
	}

}
