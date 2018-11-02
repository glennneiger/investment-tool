package com.cloud99.invest.integration.data.zillow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.cloud99.invest.BaseMockitoTest;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.integration.data.zillow.domain.ZillowAddress;
import com.cloud99.invest.integration.data.zillow.domain.search.Amount;
import com.cloud99.invest.integration.data.zillow.domain.search.High;
import com.cloud99.invest.integration.data.zillow.domain.search.Low;
import com.cloud99.invest.integration.data.zillow.domain.search.ValuationRange;
import com.cloud99.invest.integration.data.zillow.domain.search.ValueChange;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowEstimate;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowSearchResult;
import com.cloud99.invest.integration.data.zillow.messageConverters.ZillowResultPropertyConverter;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

public class ZillowPropertyConverterTest extends BaseMockitoTest {

	@InjectMocks
	private ZillowResultPropertyConverter converter = new ZillowResultPropertyConverter();

	@Before
	public void setup() {
		super.setup();
	}

	@Test
	public void test_convert() {

		ZillowSearchResult expected = buildResult();
		Property property = converter.convert(expected);
		assertNotNull(property);

		assertEquals(new Double(expected.getBathrooms()), (Double) property.getBathRooms());
		assertEquals(expected.getBedrooms(), property.getBedRooms());
		assertEquals(expected.getFinishedSqFt(), property.getFinishedSqFt());
		assertEquals(expected.getBedrooms(), property.getBedRooms());
		assertEquals(expected.getLastSoldDate(), property.getLastSoldDate());
		assertEquals(new BigDecimal(expected.getLastSoldPrice().getContent()), property.getLastSoldPrice());
		assertEquals(expected.getTaxAssessment(), property.getTaxAssessment().getTaxAssessment());
		assertEquals(expected.getTaxAssessmentYear(), property.getTaxAssessment().getTaxYear());

	}

	private ZillowSearchResult buildResult() {

		ZillowSearchResult r = new ZillowSearchResult();
		r.setAddress(buildAddress());
		r.setZestimate(buildZestimate());
		r.setBathrooms(3D);
		r.setBedrooms(4);
		r.setFinishedSqFt(2050);
		r.setLastSoldDate("01/11/2018");
		Amount lastSoldPrice = new Amount();
		lastSoldPrice.setContent(315000D);
		r.setLastSoldPrice(lastSoldPrice);
		r.setRentzestimate(buildZestimate());
		r.setTaxAssessment(BigDecimal.valueOf(200000));
		r.setTaxAssessmentYear(2018);
		r.setYearBuilt(2010);
		r.setUseCode("SingleFamily");
		return r;
	}

	private ZillowEstimate buildZestimate() {

		ZillowEstimate z = new ZillowEstimate();
		z.setAmount(buildAmount(350000));
		z.setValueChange(buildValueChange(3500D));
		z.setPercentile(.05D);
		z.setValuationRange(buildValuationRange(350000, 300000));
		z.setValueChange(buildValueChange(8000));

		return z;
	}

	private ValueChange buildValueChange(double amt) {

		ValueChange c = new ValueChange();
		c.setContent(BigDecimal.valueOf(amt));
		c.setCurrency("USD");
		c.setDuration(2);
		return c;
	}

	private ValuationRange buildValuationRange(double high, double low) {

		ValuationRange r = new ValuationRange();
		r.setHigh(buildHigh(high));
		r.setLow(buildLow(low));

		return r;
	}

	private Low buildLow(double i) {

		Low l = new Low();
		l.setContent(i);
		l.setCurrency("USD");
		return l;
	}

	private High buildHigh(double amt) {

		High h = new High();
		h.setContent(amt);
		h.setCurrency("USD");
		return h;
	}

	private Amount buildAmount(double amount) {

		Amount a = new Amount();
		a.setContent(amount);
		a.setCurrency("USD");
		return a;
	}

	private ZillowAddress buildAddress() {

		ZillowAddress a = new ZillowAddress();
		a.setCity("Denver");
		a.setState("CO");
		a.setStreet("200 E Colfax Ave");
		a.setZipcode("80203");
		return a;
	}
}
