package com.cloud99.invest.dataLoader;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.Name;
import com.cloud99.invest.domain.Person.Gender;
import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.domain.financial.Expences;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.FinancingDetails.LoanType;
import com.cloud99.invest.domain.financial.Income;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.joda.money.CurrencyUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class Loader {

	public static ObjectMapper objMapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {
		// User user = buildUser();
		// Property p = buildProperty();
		buildTypicalClosingCosts();
	}

	public static void buildTypicalClosingCosts() throws Exception {
		ObjectMapper m = new ObjectMapper();
		String[] items = { "Loan Origination Fee", "Discount Fee", "Processing Fee", "Underwriting Fee", "Wire Transfer", "Credit Report", "Tax Service", "Flood Certification", "Title Insurance", "Escrow", "Courier Fee", "Appraisal", "Recording Fee",
				"Homeowner’s Insurance first year premium", "6 Months’ Property Tax Reserves" };

		for (String item : items) {
			System.out.println(m.writeValueAsString(new ItemizedCost(item, new BigDecimal(0), Frequency.SINGLE)) + ",");
		}
	}

	public static void buildTypicalExpences(String[] args) throws Exception {
		ObjectMapper m = new ObjectMapper();
		String[] items = { "Accounting", "License Fees", "Maintenance", "Advertising", "Office Expenses", "Supplies", "Attorny and Legal Fees", "Insurance", "Property Managment", "Property Taxes", "Travel", "Leasing Commissions",
				"Salary and Wages" };

		for (String item : items) {
			System.out.println(m.writeValueAsString(new ItemizedCost(item, new BigDecimal(0), Frequency.MONTHY)) + ",");
		}
	}

	private static PropertyFinances buildPropertyFinances() {
		PropertyFinances cashFlow = new PropertyFinances(buildExpences(), buildIncome(), buildPurchaseDetails(), CurrencyUnit.USD);

		return cashFlow;
	}

	private static PurchaseDetails buildPurchaseDetails() {
		PurchaseDetails p = new PurchaseDetails();
		p.setAfterRepairValue(new BigDecimal(420000));
		p.setFinancingDetails(buildFinancingDetails());
		p.setPurchasePrice(new BigDecimal(350000));
		return p;
	}

	private static FinancingDetails buildFinancingDetails() {
		FinancingDetails f = new FinancingDetails();
		f.setDownPayment(new BigDecimal(20000));
		f.setInterestRate(4.9F);
		f.setLoanAmount(new BigDecimal(350000));
		f.setLoanTermYears(30D);
		f.setLoanType(LoanType.AMORTIZING);
		f.setMortgageInsuranceAmount(new BigDecimal(300));

		return f;
	}

	private static Income buildIncome() {
		Income i = new Income();
		i.setDeposit(new BigDecimal(2500));
		i.setGrossRent(new BigDecimal(2000));
		i.setRentUnit(Frequency.MONTHY);
		return i;
	}

	private static Expences buildExpences() {
		Expences e = new Expences();
		e.setOperatingExpences(Arrays.asList(new ItemizedCost("Closing costs", new BigDecimal(500), Frequency.ANNUALLY)));
		return e;
	}

	private static User buildUser() {
		User user = new User();
		user.setUserRoles(Arrays.asList(UserRole.FREE_USER));
		user.setEmail("nickgilas@gmail.com");
		user.setPassword("password");
		user.setMatchingPassword("password");
		user.setPersonName(buildName("Nick", "Gilas"));
		user.setBirthDate(org.joda.time.LocalDate.now().withYear(1980).withMonthOfYear(3).withDayOfMonth(23));
		user.setGender(Gender.MALE);
		return user;
	}

	private static Property buildProperty() {
		SingleFamilyProperty p = new SingleFamilyProperty();
		p.setAddress(buildAddress());
		p.setName("My single family home");
		p.setBathrooms(2.5f);
		p.setBedrooms(3);
		p.setFinishedSqFt(2700);
		return p;
	}

	private static Address buildAddress() {

		Address a = new Address();
		a.setAddress1("13060 W. 64th Place");
		a.setCity("Arvada");
		a.setState("Colorado");
		a.setZip("80004");
		return a;
	}

	private static Name buildName(String first, String last) {
		Name name = new Name();
		name.setFirstName(first);
		name.setLastName(last);
		return name;
	}

}
