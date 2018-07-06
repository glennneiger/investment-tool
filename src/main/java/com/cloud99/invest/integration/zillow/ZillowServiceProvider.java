package com.cloud99.invest.integration.zillow;

import com.cloud99.invest.dto.PropertySearchRequest;
import com.cloud99.invest.dto.PropertyValuationResult;
import com.cloud99.invest.integration.DataProviderException;
import com.cloud99.invest.integration.MessageAdaptor;
import com.cloud99.invest.integration.ServiceProvider;
import com.cloud99.invest.integration.zillow.results.Amount;
import com.cloud99.invest.integration.zillow.results.AmountJsonDeserializer;
import com.cloud99.invest.integration.zillow.results.High;
import com.cloud99.invest.integration.zillow.results.HighPriceDeserializer;
import com.cloud99.invest.integration.zillow.results.Low;
import com.cloud99.invest.integration.zillow.results.LowPriceDeserializer;
import com.cloud99.invest.integration.zillow.results.SearchResults;
import com.cloud99.invest.integration.zillow.results.ValueChange;
import com.cloud99.invest.integration.zillow.results.ValueChangeDeserializer;
import com.cloud99.invest.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:application.properties")
public class ZillowServiceProvider implements ServiceProvider {
	final static Logger log = LoggerFactory.getLogger(ZillowServiceProvider.class);

	private static final String PROPERTY_SEARCH_URL = "http://www.zillow.com/webservice/GetSearchResults.htm?zws-id={zws-id}&address={address}&citystatezip={citystatezip}";

	@Value("${zillow.id}")
	private String zillowId;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Util util;

	private ObjectMapper mapper;

	@PostConstruct
	public void init() {

		mapper = new XmlMapper();

		SimpleModule module = new SimpleModule();
		module.addDeserializer(Amount.class, new AmountJsonDeserializer());
		module.addDeserializer(High.class, new HighPriceDeserializer());
		module.addDeserializer(Low.class, new LowPriceDeserializer());
		module.addDeserializer(ValueChange.class, new ValueChangeDeserializer());

		mapper.registerModule(module);
	}

	@Override
	public PropertyValuationResult propertyValuation(PropertySearchRequest request) throws Exception {
		log.debug("PropertyValuationRequest: " + request.toString());

		String zwsid = zillowId;

		// TODO scrub the incoming data for special chars and whatnot
		Map<String, String> params = new HashMap<String, String>();
		params.put("zws-id", zwsid);
		params.put("address", request.getAddress1());
		params.put("citystatezip", request.getCity() + " " + request.getState() + " " + request.getZip());

		String str = restTemplate.getForObject(PROPERTY_SEARCH_URL, String.class, params);
		
		log.debug("Search provider result: " + str);
		
		MessageAdaptor<PropertyValuationResult, SearchResults> adaptor = new ZillowSearchProviderMessageAdaptor(util);

		SearchResults response = mapper.readValue(str, SearchResults.class);
		
		if ("0".equals(response.getMessage().getCode())) {
				return adaptor.convert(response);
		} else {
			throw new DataProviderException("zillow.failed.service.call", response.getMessage().getText());
		}
	}

	@Override
	public Collection<PropertyValuationResult> propertyCompLookup(PropertySearchRequest request) {
		// TODO FInish me
		throw new RuntimeException("implement me foo!!!");

	}

}
