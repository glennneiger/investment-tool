package com.cloud99.invest.integration.data.zillow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.cloud99.invest.MockitoTest;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.data.zillow.domain.ZillowAddress;
import com.cloud99.invest.integration.data.zillow.domain.search.Amount;
import com.cloud99.invest.integration.data.zillow.domain.search.High;
import com.cloud99.invest.integration.data.zillow.domain.search.Low;
import com.cloud99.invest.integration.data.zillow.domain.search.ValuationRange;
import com.cloud99.invest.integration.data.zillow.domain.search.ValueChange;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowEstimate;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowResponse;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowResults;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowSearchResult;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowSearchResults;
import com.cloud99.invest.integration.data.zillow.messageConverters.ZillowZestimateConverter;
import com.cloud99.invest.util.Util;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

public class ZillowValuationConverterTest extends MockitoTest {

	
	private ZillowEstimate zestimate;

	private Util util = new Util();

	@InjectMocks
	private ZillowZestimateConverter converter = new ZillowZestimateConverter();

	@Before
	public void setup() {
		super.setup();
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
	
	@SuppressWarnings({ "cast" })
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
				(BigDecimal) zestimate.getValueChange().getContent().setScale(2),
				(BigDecimal) result.getValueChange().getAmount());
		
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

	private ZillowSearchResult buildResult() {

		ZillowSearchResult r = new ZillowSearchResult();
		r.setAddress(buildAddress());
		r.setZestimate(buildZestimate());
		r.setBathrooms(3D);
		r.setBedrooms(4);
		r.setFinishedSqFt(2050);
		r.setLastSoldDate("01/11/2018");
		Amount lastSoldPrice = new Amount();
		lastSoldPrice.setContent(350000D);
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
		z.setPercentile(.05);
		z.setValuationRange(buildValuationRange(350000, 300000));
		z.setValueChange(buildValueChange(8000.00));
		
		this.zestimate = z;
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
