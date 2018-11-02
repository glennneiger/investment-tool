package com.cloud99.invest.services;

import static org.junit.Assert.assertEquals;

import com.cloud99.invest.BaseMockitoTest;
import com.cloud99.invest.dto.responses.CompAnalysisResult;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;

public class CompAnalyzerServiceTest extends BaseMockitoTest {

	@InjectMocks
	private CompAnalyzerService compService;

	@Mock
	private AccountService acctServiceMock;

	@BeforeEach
	public void beforeEach() {
		Mockito.when(acctServiceMock.getAccountSettings(ACCT_ID)).thenReturn(dataCreator.buildAccountSettings());
		compService.setUtil(util);
	}

	@Test
	public void testCompAnalysisResult() {
		PropertyValuationResult valuation1 = dataCreator.buildPropertyValuationResult(money(300000), money(315000), money(295000), 2300);
		PropertyValuationResult valuation2 = dataCreator.buildPropertyValuationResult(money(320000), money(330000), money(315000), 2000);
		PropertyValuationResult valuation3 = dataCreator.buildPropertyValuationResult(money(295000), money(305000), money(290000), 2100);
		
		// average high price = 305000
		PropertyCompSearchResult compResult = dataCreator.buildPropertyCompSearchResult(2000, Arrays.asList(valuation1, valuation2, valuation3));
		CompAnalysisResult analysisResult = compService.compAnalysisResult(ACCT_ID, compResult);

		// comp 1 sqft = $130.43
		// comp 2 sqft = $160.00
		// comp 3 sqft = $140.47
		// 430.9
		// average sqft = $143.64
		// average sales price = $287,280

		// comp 1 high = $136.96
		// comp 2 high = $165
		// comp 3 high = $145.24
		// average high = $149.07
		// average high price = $298,140

		// comp 1 low = $128.26
		// comp 2 low = $157.5
		// comp 3 low = $138.10
		// average low = $141.29
		// average low price = $282,580

		assertEquals(money(287280), analysisResult.getAverageCurrentEstimate());
		assertEquals(money(298140), analysisResult.getAverageHighPrice());
		assertEquals(money(282580), analysisResult.getAverageLowPrice());
	}
}
