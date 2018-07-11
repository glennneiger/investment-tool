package com.cloud99.invest.integration.zillow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.cloud99.invest.MockitoExtension;
import com.cloud99.invest.MockitoTest;
import com.cloud99.invest.dto.PropertyValuationResult;
import com.cloud99.invest.integration.zillow.results.Address;
import com.cloud99.invest.integration.zillow.results.Amount;
import com.cloud99.invest.integration.zillow.results.High;
import com.cloud99.invest.integration.zillow.results.Low;
import com.cloud99.invest.integration.zillow.results.OneWeekChange;
import com.cloud99.invest.integration.zillow.results.Response;
import com.cloud99.invest.integration.zillow.results.Result;
import com.cloud99.invest.integration.zillow.results.Results;
import com.cloud99.invest.integration.zillow.results.SearchResults;
import com.cloud99.invest.integration.zillow.results.ValuationRange;
import com.cloud99.invest.integration.zillow.results.ValueChange;
import com.cloud99.invest.integration.zillow.results.Zestimate;
import com.cloud99.invest.util.Util;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
public class ZillowMessageAdaptorTest extends MockitoTest {

	@InjectMocks
	private ZillowSearchProviderMessageAdaptor adaptor;
	
	private Zestimate zestimate;
	private Result zillowResult;

	@BeforeEach
	public void setup() {
		adaptor.setUtil(new Util());
	}

	@Test
	public void testSearchProperty_no_result() {
		
		SearchResults searchResults = new SearchResults();
		searchResults.setResponse(buildResponse());
		searchResults.getResponse().getResults().setResult(null);

		Assertions.assertThrows(RuntimeException.class, () -> {
			adaptor.convert(searchResults);
		});
	}
	
	@Test
	public void testSearchProperty() {

		SearchResults searchResults = new SearchResults();
		searchResults.setResponse(buildResponse());

		PropertyValuationResult result = adaptor.convert(searchResults);
		assertNotNull(result);
		assertEquals((Double) zestimate.getAmount().getContent(), (Double) result.getCurrentEstimate().getAmount().doubleValue());
		assertEquals((Double) zestimate.getValuationRange().getHigh().getContent(), (Double) result.getHighValue().getAmount().doubleValue());
		assertEquals((Double) zestimate.getValuationRange().getLow().getContent(), (Double) result.getLowValue().getAmount().doubleValue());
		assertEquals((Double) zestimate.getValueChange().getContent(), (Double) result.getValueChange().getAmount().doubleValue());
		assertEquals((Float) Float.parseFloat(zestimate.getPercentile()), (Float) result.getPercentileChange());
	}

	private Response buildResponse() {

		Response r = new Response();
		r.setResults(buildResults());

		return r;
	}

	private Results buildResults() {

		Results r = new Results();
		r.setResult(buildResult());
		return r;
	}

	private Result buildResult() {

		Result r = new Result();
		r.setAddress(buildAddress());
		r.setZestimate(buildZestimate());

		zillowResult = r;
		return r;
	}

	private Zestimate buildZestimate() {

		Zestimate z = new Zestimate();
		z.setAmount(buildAmount(350000));
		z.setOneWeekChange(buildOneWeekChange());
		z.setPercentile("5");
		z.setValuationRange(buildValuationRange(350000, 300000));
		z.setValueChange(buildValueChange(8000));
		
		this.zestimate = z;
		return z;
	}

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

	private OneWeekChange buildOneWeekChange() {

		OneWeekChange c = new OneWeekChange();
		c.setDeprecated("1000");
		return c;
	}

	private Amount buildAmount(double amount) {

		Amount a = new Amount();
		a.setContent(amount);
		a.setCurrency("USD");
		return a;
	}

	private Address buildAddress() {

		Address a = new Address();
		a.setCity("Arvada");
		a.setState("CO");
		a.setStreet("13060 West 64th Place");
		a.setZipcode("80004");
		return a;
	}

}
