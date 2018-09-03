package com.cloud99.invest.integration.zillow;

import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.DataProviderException;
import com.cloud99.invest.integration.MessageConverter;
import com.cloud99.invest.integration.ServiceProvider;
import com.cloud99.invest.integration.zillow.deserializers.AmountJsonDeserializer;
import com.cloud99.invest.integration.zillow.deserializers.HighPriceDeserializer;
import com.cloud99.invest.integration.zillow.deserializers.LowPriceDeserializer;
import com.cloud99.invest.integration.zillow.deserializers.ValueChangeDeserializer;
import com.cloud99.invest.integration.zillow.messageConverters.ZillowMessageConverterManager;
import com.cloud99.invest.integration.zillow.messageConverters.ZillowValuationConverter;
import com.cloud99.invest.integration.zillow.results.Amount;
import com.cloud99.invest.integration.zillow.results.High;
import com.cloud99.invest.integration.zillow.results.Low;
import com.cloud99.invest.integration.zillow.results.ValueChange;
import com.cloud99.invest.integration.zillow.results.ZillowEstimate;
import com.cloud99.invest.integration.zillow.results.ZillowSearchResults;
import com.cloud99.invest.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Service
@PropertySource("classpath:application.properties")
@Slf4j
public class ZillowServiceProvider implements ServiceProvider {

	private static final String PROPERTY_SEARCH_URL = "http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id={zws-id}&address={address}&citystatezip={citystatezip}&rentzestimate={rentzestimate}";

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
	public PropertySearchResult propertySearch(PropertySearchRequest request) throws Exception {
		log.trace("PropertySearchRequest: " + request.toString());

		String returnJson = getSearchResults(request);

		ZillowSearchResults response = mapper.readValue(returnJson, ZillowSearchResults.class);
		if ("0".equals(response.getMessage().getCode())) {
			ZillowMessageConverterManager manager = new ZillowMessageConverterManager();
			return manager.convert(response);
		}
		throw new DataProviderException("zillow.failed.service.call", response.getMessage().getText(), "zillow call failed");
	}

	@Override
	public PropertyValuationResult propertyValuation(PropertySearchRequest request) throws Exception {
		log.debug("PropertyValuationRequest: " + request.toString());

		String returnJson = getSearchResults(request);

		MessageConverter<PropertyValuationResult, ZillowEstimate> adaptor = new ZillowValuationConverter(util);

		// convert json to object
		ZillowSearchResults response = mapper.readValue(returnJson, ZillowSearchResults.class);

		if ("0".equals(response.getMessage().getCode())) {
			return adaptor.convert(response.getResponse().getResults().getZillowResult().getZestimate());
		}
		throw new DataProviderException("zillow.failed.service.call", response.getMessage().getText(), "zillow call failed");
	}

	private String getSearchResults(PropertySearchRequest request) {
		// TODO scrub the incoming data for special chars and what not
		Map<String, String> params = new HashMap<>();
		params.put("zws-id", zillowId);
		params.put("address", request.getAddress1());
		params.put("citystatezip", request.getCity() + " " + request.getState() + " " + request.getZip());
		params.put("rentzestimate", Boolean.toString(request.isIncludeRentEstimate()));

		String str = restTemplate.getForObject(PROPERTY_SEARCH_URL, String.class, params);
		
		log.debug("Search provider result: " + str);
		return str;
	}

	@Override
	public Collection<PropertyValuationResult> propertyCompLookup(PropertySearchRequest request) {
		// TODO - NG - implement once it become a priority
		throw new RuntimeException("implement me foo!!!");

	}

}
