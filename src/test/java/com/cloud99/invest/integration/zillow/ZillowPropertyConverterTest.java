package com.cloud99.invest.integration.zillow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.cloud99.invest.BaseMockitoTest;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.integration.zillow.messageConverters.ZillowPropertyConverter;
import com.cloud99.invest.integration.zillow.results.Amount;
import com.cloud99.invest.integration.zillow.results.High;
import com.cloud99.invest.integration.zillow.results.Low;
import com.cloud99.invest.integration.zillow.results.ValuationRange;
import com.cloud99.invest.integration.zillow.results.ValueChange;
import com.cloud99.invest.integration.zillow.results.ZillowAddress;
import com.cloud99.invest.integration.zillow.results.ZillowEstimate;
import com.cloud99.invest.integration.zillow.results.ZillowResult;
import com.cloud99.invest.integration.zillow.results.ZillowResults;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

public class ZillowPropertyConverterTest extends BaseMockitoTest {

	@InjectMocks
	private ZillowPropertyConverter converter = new ZillowPropertyConverter();

	private ZillowEstimate zestimate;

	@Before
	public void setup() {
		super.setup();
	}

	@SuppressWarnings("boxing")
	@Test
	public void test_convert() {

		ZillowResult expected = buildResult();
		Property property = converter.convert(expected);
		assertNotNull(property);

		assertEquals(new Float(expected.getBathrooms()), (Float) property.getBathRooms());
		assertEquals(expected.getBedrooms(), property.getBedRooms());
		assertEquals(expected.getFinishedSqFt(), property.getFinishedSqFt());
		assertEquals(expected.getBedrooms(), property.getBedRooms());
		assertEquals(expected.getLastSoldDate(), DateTime.parse("01/11/2018", DateTimeFormat.forPattern("dd/MM/yyyy")));
		assertEquals(new BigDecimal(expected.getLastSoldPrice().getContent()), property.getLastSoldPrice());
		assertEquals(expected.getTaxAssessment(), property.getTaxAssessment().getTaxAssessment());
		assertEquals(expected.getTaxAssessmentYear(), property.getTaxAssessment().getTaxYear());

	}

	@SuppressWarnings("boxing")
	private ZillowResult buildResult() {

		ZillowResult r = new ZillowResult();
		r.setAddress(buildAddress());
		r.setZestimate(buildZestimate());
		r.setBathrooms(3);
		r.setBedrooms(4);
		r.setFinishedSqFt(2050);
		r.setLastSoldDate("01/11/2018");
		Amount lastSoldPrice = new Amount();
		lastSoldPrice.setContent(315000D);
		r.setLastSoldPrice(lastSoldPrice);
		r.setRentzestimate(buildZestimate());
		r.setTaxAssessment(200000.0);
		r.setTaxAssessmentYear(2018);
		r.setYearBuilt(2010);
		r.setUseCode("SingleFamily");
		return r;
	}

	@SuppressWarnings("boxing")
	private ZillowEstimate buildZestimate() {

		ZillowEstimate z = new ZillowEstimate();
		z.setAmount(buildAmount(350000));
		z.setValueChange(buildValueChange(3500D));
		z.setPercentile(.05F);
		z.setValuationRange(buildValuationRange(350000, 300000));
		z.setValueChange(buildValueChange(8000));

		this.zestimate = z;
		return z;
	}

	@SuppressWarnings("boxing")
	private ValueChange buildValueChange(double amt) {

		ValueChange c = new ValueChange();
		c.setContent(amt);
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

	@SuppressWarnings("boxing")
	private Low buildLow(double i) {

		Low l = new Low();
		l.setContent(i);
		l.setCurrency("USD");
		return l;
	}

	@SuppressWarnings("boxing")
	private High buildHigh(double amt) {

		High h = new High();
		h.setContent(amt);
		h.setCurrency("USD");
		return h;
	}

	@SuppressWarnings("boxing")
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
