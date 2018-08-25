package com.cloud99.invest.integration.zillow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.cloud99.invest.BaseMockitoTest;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.zillow.results.ZillowAddress;
import com.cloud99.invest.integration.zillow.messageConverters.ZillowValuationConverter;
import com.cloud99.invest.integration.zillow.results.Amount;
import com.cloud99.invest.integration.zillow.results.High;
import com.cloud99.invest.integration.zillow.results.Low;
import com.cloud99.invest.integration.zillow.results.ZillowResponse;
import com.cloud99.invest.integration.zillow.results.ZillowResult;
import com.cloud99.invest.integration.zillow.results.ZillowResults;
import com.cloud99.invest.integration.zillow.results.ZillowSearchResults;
import com.cloud99.invest.integration.zillow.results.ValuationRange;
import com.cloud99.invest.integration.zillow.results.ValueChange;
import com.cloud99.invest.integration.zillow.results.ZillowEstimate;
import com.cloud99.invest.util.Util;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;

public class ZillowValuationConverterTest extends BaseMockitoTest {

	@InjectMocks
	private ZillowValuationConverter converter = new ZillowValuationConverter();
	
	private ZillowEstimate zestimate;

	private Util util = new Util();

	@Before
	public void setup() {
		super.setup();
		converter.setUtil(util);
	}

	@Test
	public void testSearchProperty_no_result() {
		
		ZillowSearchResults searchResults = new ZillowSearchResults();
		searchResults.setResponse(buildResponse());
		searchResults.getResponse().getResults().setZillowResult(null);

		Assertions.assertThrows(RuntimeException.class, () -> {
			converter.convert(searchResults.getResponse().getResults().getZillowResult().getZestimate());
		});
	}
	
	@SuppressWarnings({ "cast", "boxing" })
	@Test
	public void testConvertSearchResults() {

		ZillowSearchResults searchResults = new ZillowSearchResults();
		searchResults.setResponse(buildResponse());

		PropertyValuationResult result = converter.convert(searchResults.getResponse().getResults().getZillowResult().getZestimate());
		assertNotNull(result);
		
		// Zestimate
		assertEquals(
				(Double) zestimate.getAmount().getContent(), 
				(Double) result.getCurrentEstimate().getAmount().doubleValue());
		
		assertEquals(
				(Double) zestimate.getValuationRange().getHigh().getContent(), 
				(Double) result.getHighValue().getAmount().doubleValue());
		
		assertEquals(
				(Double) zestimate.getValuationRange().getLow().getContent(), 
				(Double) result.getLowValue().getAmount().doubleValue());
		
		assertEquals(
				(Double) zestimate.getValueChange().getContent(), 
				(Double) result.getValueChange().getAmount().doubleValue());
		
		assertEquals(
				zestimate.getPercentile(), 
				result.getPercentileChange());

	}

	private ZillowResponse buildResponse() {

		ZillowResponse r = new ZillowResponse();
		r.setResults(buildResults());

		return r;
	}

	private ZillowResults buildResults() {

		ZillowResults r = new ZillowResults();
		r.setZillowResult(buildResult());
		return r;
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
		lastSoldPrice.setContent(350000D);
		r.setLastSoldPrice(lastSoldPrice);
		r.setRentzestimate(buildZestimate());
		r.setTaxAssessment(200000.00);
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
