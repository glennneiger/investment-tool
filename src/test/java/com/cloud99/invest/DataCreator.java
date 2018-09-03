package com.cloud99.invest;

import com.cloud99.invest.config.AppConfig;
import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.Name;
import com.cloud99.invest.domain.Person.Gender;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.SubscriptionType;
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
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.joda.money.CurrencyUnit;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.TimeZone;

@Component
public class DataCreator {

	public static void main(String[] args) {
		// User user = buildUser();
		// Property p = buildProperty();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		AppConfig.UTC_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
		mapper.setDateFormat(AppConfig.UTC_DATE_FORMAT);
		mapper.registerModule(new JodaModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			System.out.println(mapper.writeValueAsString(buildAccountCreationRequest()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static AccountCreationRequest buildAccountCreationRequest() {
		AccountCreationRequest r = new AccountCreationRequest();
		r.setAccountName("Test account name");
		r.setBirthDate(DateTime.now().toLocalDate());
		r.setGender(Gender.FEMALE);
		r.setEmail("test.user@cloud99.com");
		r.setFirstName("FirstName");
		r.setMiddleName("MiddleName");
		r.setLastName("LastName");
		r.setPassword("password");
		r.setMatchingPassword("password");
		r.setSubscription(SubscriptionType.FREE);
		r.setBirthDate(new LocalDate("1980-03-23"));
		return r;
	}

	public Collection<ItemizedCost> buildCosts(int amount) {
		return Arrays.asList(new ItemizedCost("Cost", BigDecimal.valueOf(amount), Frequency.MONTHY));
	}

	public PropertySearchRequest buildPropertySearchRequest() {
		PropertySearchRequest r = new PropertySearchRequest();
		r.setAddress1("13060 West 64th Place");
		r.setCity("Arvada");
		r.setState("CO");
		r.setZip("80004");
		return r;
	}

	public void buildTypicalClosingCosts() throws Exception {
		ObjectMapper m = new ObjectMapper();
		String[] items = { "Loan Origination Fee", "Discount Fee", "Processing Fee", "Underwriting Fee", "Wire Transfer", "Credit Report", "Tax Service", "Flood Certification", "Title Insurance", "Escrow", "Courier Fee", "Appraisal", "Recording Fee",
				"Homeowner’s Insurance first year premium", "6 Months’ Property Tax Reserves" };

		for (String item : items) {
			System.out.println(m.writeValueAsString(new ItemizedCost(item, new BigDecimal(0), Frequency.SINGLE)) + ",");
		}
	}

	public void buildTypicalExpences() throws Exception {
		ObjectMapper m = new ObjectMapper();
		String[] items = { "Accounting", "License Fees", "Maintenance", "Advertising", "Office Expenses", "Supplies", "Attorny and Legal Fees", "Insurance", "Property Managment", "Property Taxes", "Travel", "Leasing Commissions",
				"Salary and Wages" };

		for (String item : items) {
			System.out.println(m.writeValueAsString(new ItemizedCost(item, new BigDecimal(0), Frequency.MONTHY)) + ",");
		}
	}

	public PropertyFinances buildPropertyFinances() {
		PropertyFinances cashFlow = new PropertyFinances(buildExpences(), buildIncome(), buildPurchaseDetails(), CurrencyUnit.USD);

		return cashFlow;
	}

	public PurchaseDetails buildPurchaseDetails() {
		PurchaseDetails p = new PurchaseDetails();
		p.setAfterRepairValue(new BigDecimal(420000));
		p.setFinancingDetails(buildFinancingDetails());
		p.setPurchasePrice(new BigDecimal(350000));
		return p;
	}

	public FinancingDetails buildFinancingDetails() {
		FinancingDetails f = new FinancingDetails();
		f.setDownPayment(new BigDecimal(20000));
		f.setInterestRate(4.9F);
		f.setLoanAmount(new BigDecimal(350000));
		f.setLoanTermYears(30D);
		f.setLoanType(LoanType.AMORTIZING);
		f.setMortgageInsuranceAmount(new BigDecimal(300));

		return f;
	}

	public Income buildIncome() {
		Income i = new Income();
		i.setDeposit(new BigDecimal(2500));
		i.setGrossRent(new BigDecimal(2000));
		i.setRentUnit(Frequency.MONTHY);
		return i;
	}

	public Expences buildExpences() {
		Expences e = new Expences();
		e.setOperatingExpences(Arrays.asList(new ItemizedCost("Closing costs", new BigDecimal(500), Frequency.ANNUALLY)));
		return e;
	}

	public User buildUser() {
		User user = new User();
		user.setUserRoles(Arrays.asList(UserRole.CUSTOMER));
		user.setEmail("nickgilas@gmail.com");
		user.setPassword("password");
		user.setPersonName(buildName("Nick", "Gilas"));
		user.setBirthDate(org.joda.time.LocalDate.now().withYear(1980).withMonthOfYear(3).withDayOfMonth(23));
		user.setGender(Gender.MALE);
		return user;
	}

	public Property buildProperty() {
		SingleFamilyProperty p = new SingleFamilyProperty();
		p.setAddress(buildAddress());
		p.setName("My single family home");
		p.setBathRooms(2.5f);
		p.setBedRooms(3);
		p.setFinishedSqFt(2700);
		return p;
	}

	public Address buildAddress() {

		Address a = new Address();
		a.setAddress1("13060 W. 64th Place");
		a.setCity("Arvada");
		a.setState("Colorado");
		a.setZip("80004");
		return a;
	}

	public Name buildName(String first, String last) {
		Name name = new Name();
		name.setFirstName(first);
		name.setLastName(last);
		return name;
	}

}
