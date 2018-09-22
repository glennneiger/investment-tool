package com.cloud99.invest.integration.zillow.messageConverters;

import com.cloud99.invest.domain.financial.TaxAssessment;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.dto.responses.PropertyCompResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.MessageConverter;
import com.cloud99.invest.integration.zillow.results.comps.Comp;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class ZillowCompConverter implements MessageConverter<Comp, PropertyCompResult> {
	private ZillowAddressConverter addressConverter = new ZillowAddressConverter();
	private ZillowZestimateConverter<PropertyValuationResult> valuationConverter = new ZillowZestimateConverter<>(null);

	@SuppressWarnings("unchecked")
	@Override
	public PropertyCompResult convert(Comp incoming, Class<PropertyCompResult> returnVal) {

		PropertyCompResult result = new PropertyCompResult();
		Property property = new SingleFamilyProperty();
		property.setAddress(addressConverter.convert(incoming.getAddress(), null));
		property.setBathRooms(incoming.getBathrooms());
		property.setBedRooms(incoming.getBedrooms());

		// TODO - NG - convert to date from format: mm/dd/yyyy
		// DateTime dt = incoming.getLastSoldDate()
		// property.setLastSoldDate(dt);

		if (incoming.getLastSoldPrice() != null) {
			property.setLastSoldPrice(new BigDecimal(incoming.getLastSoldPrice().getContent()));
		}
		property.setLotSizeSqFt(incoming.getLotSizeSqFt());
		property.setTaxAssessment(new TaxAssessment(incoming.getTaxAssessmentYear(), incoming.getTaxAssessment()));
		if (!StringUtils.isBlank(incoming.getYearBuilt())) {
			property.setYearBuilt(Integer.parseInt(incoming.getYearBuilt()));
		}
		result.setSubjectProperty(property);
		valuationConverter(incoming.getZestimate());
		return result;
	}
}
