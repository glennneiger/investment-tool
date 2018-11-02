package com.cloud99.invest.integration.data.zillow.messageConverters;

import com.cloud99.invest.domain.financial.TaxAssessment;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.PropertyType;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.integration.data.MessageConverter;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowSearchResult;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZillowResultPropertyConverter implements MessageConverter<ZillowSearchResult, Property> {

	public static final String SINGLE_FAMILY = "SingleFamily";
	private ZillowAddressConverter addressConverter = new ZillowAddressConverter();
	/*
	 * (non-Javadoc) Not that this method doesn't use the Class<T> returnVal since
	 * properties are dynamically created
	 * 
	 * @see
	 * com.cloud99.invest.integration.MessageConverter#convert(java.lang.Object,
	 * java.lang.Class)
	 */
	@Override
	public Property convert(ZillowSearchResult incomingResult) {

		Property p = mapProperty(incomingResult);
		
		p.setBathRooms(incomingResult.getBathrooms());
		p.setBedRooms(incomingResult.getBedrooms());
		p.setFinishedSqFt(incomingResult.getFinishedSqFt());
		p.setLotSizeSqFt(incomingResult.getLotSizeSqFt());
		p.setYearBuilt(incomingResult.getYearBuilt());
		p.setTaxAssessment(new TaxAssessment(incomingResult.getTaxAssessmentYear(), incomingResult.getTaxAssessment()));

		p.setLastSoldDate(incomingResult.getLastSoldDate());

		if (incomingResult.getLastSoldPrice() != null) {
			p.setLastSoldPrice(new BigDecimal(incomingResult.getLastSoldPrice().getContent()));
		}
		p.setAddress(addressConverter.convert(incomingResult.getAddress()));

		return p;
	}

	private Property mapProperty(ZillowSearchResult incomingResult) {

		Property p = null;
		if (StringUtils.isBlank(incomingResult.getUseCode())) {
			// default to single family property if not specified in returned provider data
			p = new SingleFamilyProperty();
		} else {
			PropertyType ourPropertyType = mapPropertyType(incomingResult.getUseCode());

			try {
				p = ourPropertyType.getPropertyClassType().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("Error occurred creating a new property type instance for zillow useCode: " + incomingResult.getUseCode() + ", error is: " + e.getMessage(), e);
				// default to single family if any exceptions are encountered
				p = new SingleFamilyProperty();
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
		} else if (SINGLE_FAMILY.equalsIgnoreCase(useCode)) {
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
