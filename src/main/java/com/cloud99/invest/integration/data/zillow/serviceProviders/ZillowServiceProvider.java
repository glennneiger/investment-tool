package com.cloud99.invest.integration.data.zillow.serviceProviders;

import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.integration.data.DataProviderException;
import com.cloud99.invest.integration.data.DataServiceProvider;
import com.cloud99.invest.integration.data.zillow.ZillowApiDetails;
import com.cloud99.invest.integration.data.zillow.converterManagers.MessageConverterManager;
import com.cloud99.invest.integration.data.zillow.deserializers.AmountJsonDeserializer;
import com.cloud99.invest.integration.data.zillow.deserializers.HighPriceDeserializer;
import com.cloud99.invest.integration.data.zillow.deserializers.LowPriceDeserializer;
import com.cloud99.invest.integration.data.zillow.deserializers.ValueChangeDeserializer;
import com.cloud99.invest.integration.data.zillow.domain.ZillowApiResult;
import com.cloud99.invest.integration.data.zillow.domain.search.Amount;
import com.cloud99.invest.integration.data.zillow.domain.search.High;
import com.cloud99.invest.integration.data.zillow.domain.search.Low;
import com.cloud99.invest.integration.data.zillow.domain.search.ValueChange;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Service
@PropertySource("classpath:application.properties")
@Slf4j
public class ZillowServiceProvider implements DataServiceProvider {


	@Value("${zillow.id}")
	private String zillowId;

	@Autowired
	private RestTemplate restTemplate;

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

	@Override
	public PropertyCompSearchResult propertyCompLookup(String providerPropertyId, Integer numOfCompsToLookup) {
		
		log.trace("PropertyCompRequest, ID: {}, NumOfComps: {} ", providerPropertyId, numOfCompsToLookup);
		Map<String, Object> params = new HashMap<>();
		params.put("zws-id", zillowId);
		params.put("zpid", providerPropertyId);
		params.put("count", numOfCompsToLookup);
		params.put("rentzestimate", true);

		String xml = invokeService(params, ZillowApiDetails.COMPS);
		return convertResponse(xml, ZillowApiDetails.COMPS);
	}

	@Override
	public PropertySearchResult propertySearch(PropertySearchRequest request) {

		log.trace("PropertySearchRequest: " + request.toString());
		Map<String, Object> params = new HashMap<>();
		params.put("zws-id", zillowId);
		params.put("address", request.getAddress1());
		params.put("citystatezip", request.getCity() + " " + request.getState() + " " + request.getZip());
		params.put("rentzestimate", request.isIncludeRentEstimate());
		log.trace("Params: " + params);

		String xml = invokeService(params, ZillowApiDetails.PROPERTY_SEARCH);
		return convertResponse(xml, ZillowApiDetails.PROPERTY_SEARCH);

	}

	@SuppressWarnings("unchecked")
	private <RETURN_TYPE, T extends ZillowApiResult> RETURN_TYPE convertResponse(String responseXml, ZillowApiDetails apiDetails) {

		try {
			log.trace("Staring to convertResponse for zillow api: {}", apiDetails);

			MessageConverterManager<?, ?> converterManager = apiDetails.getConverterManager().newInstance();
			T zillowResult = (T) xmlMapper.readValue(responseXml, converterManager.getConvertFromType());

			RETURN_TYPE result = null;
			if ("0".equals(zillowResult.getMessage().getCode())) {

				result = (RETURN_TYPE) converterManager.convert(zillowResult);
				log.debug("Api conversion result: {}", result);
				return result;

			}
			throw new DataProviderException("zillow.failed.service.call", zillowResult.getMessage().getText(), "zillow call failed");

		} catch (IOException | InstantiationException | IllegalAccessException e) {
			log.error("Error occurred while invoking zillow API: {}, {}", apiDetails.toString(), e.getMessage(), e);
		}
		return null;
	}

	private String invokeService(Map<String, Object> params, ZillowApiDetails apiDetails) {
		String str = restTemplate.getForObject(apiDetails.getUrl().toString(), String.class, params);
		log.debug("Zillow API result for url: {}, returnVal: {} ", apiDetails.getUrl().toString(), str);
		return str;
	}

	protected void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
