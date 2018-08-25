package com.cloud99.invest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.BaseMockitoTest;
import com.cloud99.invest.dto.requests.FlipAnalysisRequest;
import com.cloud99.invest.dto.responses.FlipAnalysisResults;

import org.joda.money.CurrencyUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class AnalyzerServiceTest extends BaseMockitoTest {

	@Autowired
	private AnalyzerService analyzerService = new AnalyzerService();

	@BeforeEach
	public void setup() {
		super.setup();
		analyzerService.setUtil(util);
	}

	@Test
	public void testCalculateFlip() {
		FlipAnalysisResults results = analyzerService.analyzeFlip(buildFinancials());
		assertEquals(BigDecimal.valueOf(389400.00).setScale(2), results.getMaxAllowableOfferPrice().getAmount());
		assertEquals(Double.valueOf(5), results.getReturnOnInvestment());
	}

	private FlipAnalysisRequest buildFinancials() {
		FlipAnalysisRequest c = new FlipAnalysisRequest();
		c.setAfterRepairValue(BigDecimal.valueOf(450000.00));
		c.setCurrencyUnit(CurrencyUnit.USD);
		c.setDesiredProfit(BigDecimal.valueOf(20000.00));
		c.setRepairCosts(BigDecimal.valueOf(20000.00));
		c.setClosingCosts(dataCreator.buildCosts(16600));
		c.setHoldingCosts(dataCreator.buildCosts(2000));
		c.setHoldingDays(60);
		return c;
	}
}
