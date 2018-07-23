package com.cloud99.invest;

import com.cloud99.invest.domain.TimeUnit;
import com.cloud99.invest.domain.financial.Expences;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.Income;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.financial.PurchaseDetails;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public abstract class BaseFinancialTest extends MockitoTest {

	@Test
	public abstract void executeTest();

	public PropertyFinances buildPropertyFinances(double purchasePrice) {

		// 20% Down, 4.5% Interest rate
		FinancingDetails details = buildFinancingDetails(purchasePrice, 0.20D, 4.5F);

		// rehab: $50k

		// annual operating expenses: $3000 - monthly: $20K - annually
		Expences expences = buildExpences(0F, 1666.666);

		// annual income: $45K - annually
		Income income = buildIncome(0, 3750, 0);

		// After repair value (ARV) - 315000
		PurchaseDetails purchaseDetails = buildPurchaseDetails(purchasePrice, 315000D);
		purchaseDetails.setFinancingDetails(details);

		PropertyFinances cash = new PropertyFinances(expences, income, purchaseDetails, CURRENCY);

		return cash;
	}

	public PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv) {

		PurchaseDetails d = new PurchaseDetails();
		d.setAfterRepairValue(new BigDecimal(arv));
		d.setPurchasePrice(new BigDecimal(purchasePrice));

		return d;
	}

	public Income buildIncome(double deposit, double rent, double otherIncome) {

		Income i = new Income();
		i.setDeposit(new BigDecimal(deposit));
		i.setGrossRent(new BigDecimal(rent));
		i.setOtherIncome(new BigDecimal(otherIncome));
		i.setRentUnit(TimeUnit.MONTHY);

		return i;
	}
}
