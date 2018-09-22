package com.cloud99.invest.integration.zillow;

import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertyCompResult;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.DataProviderException;
import com.cloud99.invest.integration.MessageConverter;
import com.cloud99.invest.integration.ServiceProvider;
import com.cloud99.invest.integration.zillow.deserializers.AmountJsonDeserializer;
import com.cloud99.invest.integration.zillow.deserializers.HighPriceDeserializer;
import com.cloud99.invest.integration.zillow.deserializers.LowPriceDeserializer;
import com.cloud99.invest.integration.zillow.deserializers.ValueChangeDeserializer;
import com.cloud99.invest.integration.zillow.messageConverters.ZillowCompSearchResultsConverterManager;
import com.cloud99.invest.integration.zillow.messageConverters.ZillowSearchResultsMessageConverterManager;
import com.cloud99.invest.integration.zillow.messageConverters.ZillowZestimateConverter;
import com.cloud99.invest.integration.zillow.results.Amount;
import com.cloud99.invest.integration.zillow.results.High;
import com.cloud99.invest.integration.zillow.results.Low;
import com.cloud99.invest.integration.zillow.results.ValueChange;
import com.cloud99.invest.integration.zillow.results.ZillowEstimate;
import com.cloud99.invest.integration.zillow.results.ZillowSearchResults;
import com.cloud99.invest.integration.zillow.results.comps.ZillowComparables;
import com.cloud99.invest.util.Util;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Service
@PropertySource("classpath:application.properties")
@Slf4j
public class ZillowServiceProvider implements ServiceProvider {

	private static final String PROPERTY_SEARCH_URL = "http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id={zws-id}&address={address}&citystatezip={citystatezip}&rentzestimate={rentzestimate}";
	private static final String GET_COMPS_URL = "http://www.zillow.com/webservice/GetDeepComps.htm?zws-id={zws-id}&zpid={zpid}&count={count}&rentzestimate={rentzestimate}";

	@Value("${zillow.id}")
	private String zillowId;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Util util;

	@Autowired
	private ObjectMapper mapper;

	private XmlMapper xmlMapper;

	@PostConstruct
	public void init() {

		xmlMapper = new XmlMapper();

		JacksonXmlModule module = new JacksonXmlModule();
		module.addDeserializer(Amount.class, new AmountJsonDeserializer());
		module.addDeserializer(High.class, new HighPriceDeserializer());
		module.addDeserializer(Low.class, new LowPriceDeserializer());
		module.addDeserializer(ValueChange.class, new ValueChangeDeserializer());
		module.setDefaultUseWrapper(false);
		xmlMapper.registerModule(module);

		JacksonXmlModule xmlModule = new JacksonXmlModule();
		xmlModule.setDefaultUseWrapper(false);
		xmlMapper.registerModule(new JaxbAnnotationModule());
		xmlMapper.registerModule(xmlModule);

		xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		xmlMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
	}

	private Object executeApiCall(Map<String, Object> params, String url) {

		return new Object();
	}
	@Override
	public Collection<PropertyValuationResult> propertyCompLookup(String providerPropertyId, Integer numOfCompsToLookup) {
		
		ZillowComparables response = null;

		String returnXml = getCompResults(providerPropertyId, numOfCompsToLookup, true);
		try {
			response = xmlMapper.readValue(returnXml, ZillowComparables.class);
			if ("0".equals(response.getMessage().getCode())) {
				MessageConverter<ZillowComparables, Collection<PropertyCompResult>> manager = new ZillowCompSearchResultsConverterManager(util);
				Collection<PropertyCompResult> comps = manager.convert(response);
				return comps;
			}
			throw new DataProviderException("zillow.failed.service.call", response.getMessage().getText(), "zillow call failed");

		} catch (IOException e) {
			log.error("Error occurred while looking up comps from zillow: {}, providerPropertyId: {}", e.getMessage(), providerPropertyId, e);
		}
		return null;
	}

	@Override
	public PropertySearchResult propertySearch(PropertySearchRequest request) {
		log.trace("PropertySearchRequest: " + request.toString());

		String returnXml = getSearchResults(request);

		ZillowSearchResults response;
		try {
			response = xmlMapper.readValue(returnXml, ZillowSearchResults.class);
			if ("0".equals(response.getMessage().getCode())) {
				MessageConverter<ZillowSearchResults, PropertySearchResult> manager = new ZillowSearchResultsMessageConverterManager();
				return manager.convert(response);
			}
			throw new DataProviderException("zillow.failed.service.call", response.getMessage().getText(), "zillow call failed");
		} catch (IOException e) {
			log.error("Error occurred while looking up properties from zillow, request: {}, msg: {}", request.toString(), e.getMessage(), e);
		}
		return null;
	}

	@Override
	public PropertyValuationResult propertyValuation(PropertySearchRequest request) {
		log.debug("PropertyValuationRequest: " + request.toString());

		String returnJson = getSearchResults(request);

		MessageConverter<ZillowEstimate, PropertyValuationResult> adaptor = new ZillowZestimateConverter(util);

		// convert json to object
		ZillowSearchResults response;
		try {
			response = xmlMapper.readValue(returnJson, ZillowSearchResults.class);
			if ("0".equals(response.getMessage().getCode())) {
				return adaptor.convert(response.getResponse().getResults().getZillowResult().getZestimate());
			}
			throw new DataProviderException("zillow.failed.service.call", response.getMessage().getText(), "zillow call failed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String getSearchResults(PropertySearchRequest request) {

		Map<String, Object> params = new HashMap<>();
		params.put("zws-id", zillowId);
		params.put("address", request.getAddress1());
		params.put("citystatezip", request.getCity() + " " + request.getState() + " " + request.getZip());
		params.put("rentzestimate", request.isIncludeRentEstimate());

		String str = restTemplate.getForObject(PROPERTY_SEARCH_URL, String.class, params);
		
		log.debug("Zillow search provider result: " + str);
		return str;
	}

	private String getCompResults(String zillowPropertyId, int numOfCompsToLookup, boolean includeRentEstimate) {

		Map<String, Object> params = new HashMap<>();
		params.put("zws-id", zillowId);
		params.put("zpid", zillowPropertyId);
		params.put("count", numOfCompsToLookup);
		params.put("rentzestimate", includeRentEstimate);

		String str = restTemplate.getForObject(GET_COMPS_URL, String.class, params);

		log.debug("Zillow get comps result: " + str);
		return str;
	}

}
