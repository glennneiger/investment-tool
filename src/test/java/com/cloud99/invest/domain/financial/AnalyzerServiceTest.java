package com.cloud99.invest.domain.financial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.MockitoTest;
import com.cloud99.invest.domain.TimeUnit;
import com.cloud99.invest.services.AnalyzerService;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AnalyzerServiceTest extends MockitoTest {

	private AnalyzerService service = new AnalyzerService();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCalculateNetOperatingIncome() {
		double purchasePrice = 300000D;

		// purchase price: $300k
		FinancingDetails details = buildFinancingDetails(purchasePrice, 0.20D, 4.5F);

		// annual operating expenses: $8,100
		Expences expences = buildExpences(0F, 675);

		// annual income: $24,600
		Income income = buildIncome(1500, 2050, 0);

		// NOI: $16,500
		PurchaseDetails purchaseDetails = buildPurchaseDetails(purchasePrice, 350000D);
		purchaseDetails.setFinancingDetails(details);

		PropertyFinances cash = new PropertyFinances(expences, income, purchaseDetails, CURRENCY);
		Money noi = service.calculateNetOperatingIncome(cash);
		assertEquals(buildMoney(16500), noi);

	}

	@Test
	void calculateCapRate() {
		double purchasePrice = 300000D;

		// purchase price: $300k
		FinancingDetails details = buildFinancingDetails(purchasePrice, 0.20D, 4.5F);

		// annual operating expenses: $8,100
		Expences expences = buildExpences(0F, 675);

		// annual income: $24,600
		Income income = buildIncome(1500, 2050, 0);

		PurchaseDetails purchaseDetails = buildPurchaseDetails(purchasePrice, 350000D);
		purchaseDetails.setFinancingDetails(details);

		PropertyFinances cash = new PropertyFinances(expences, income, purchaseDetails, CURRENCY);
		assertEquals(new Float(.055), service.calculateCapRate(cash));

	}

	private PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv ) {

		PurchaseDetails d = new PurchaseDetails();
		d.setAfterRepairValue(new BigDecimal(arv));
		d.setPurchasePrice(new BigDecimal(purchasePrice));
		
		return d;
	}

	private Income buildIncome(double deposit, double rent, double otherIncome) {

		Income i = new Income();
		i.setDeposit(new BigDecimal(deposit));
		i.setGrossRent(new BigDecimal(rent));
		i.setOtherIncome(new BigDecimal(otherIncome));
		i.setRentUnit(TimeUnit.MONTHY);

		return i;
	}

}
