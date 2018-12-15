package com.cloud99.invest;

import com.cloud99.invest.config.AppConfig;
import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.Name;
import com.cloud99.invest.domain.Person.Gender;
import com.cloud99.invest.domain.Status;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.AccountSettings;
import com.cloud99.invest.domain.account.MembershipType;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.domain.billing.Subscription;
import com.cloud99.invest.domain.billing.Subscription.Interval;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.FinancingDetails.LoanType;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.rental.RentalExpences;
import com.cloud99.invest.domain.financial.rental.RentalIncome;
import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;
import com.cloud99.invest.domain.financial.rental.ReoccuringExpense;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertyCompValuationResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Component
public class DataCreator {

	public static void main(String[] args) throws Exception {
		// User user = buildUser();
		// Property p = buildProperty();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		AppConfig.UTC_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
		mapper.setDateFormat(AppConfig.UTC_DATE_FORMAT);
		mapper.registerModule(new JodaModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DataCreator dataCreator = new DataCreator();

		System.out.println(mapper.writeValueAsString(dataCreator.buildSubscriptions()));

	}

	public List<Subscription> buildSubscriptions() {

		Subscription sub1 = new Subscription();
		sub1.setProviderSubscriptionPlanId("plan_Dz6f2apauFMCL6");
		sub1.setDescription("Monthly Subsription");
		sub1.setBillingInterval(Interval.MONTHLY);
		sub1.setPrice(8.99);

		Subscription sub2 = new Subscription();
		sub2.setProviderSubscriptionPlanId("plan_Dz6fZwldkojoRJ");
		sub2.setDescription("Annual Subscription - 2 Months Free!");
		sub2.setPrice(89.90);
		sub2.setBillingInterval(Interval.ANUALLY);

		return Arrays.asList(sub1, sub2);
	}

	public PropertyCompSearchResult buildPropertyCompSearchResult(int finishedSqFt, List<PropertyValuationResult> valuations) {
		PropertyCompSearchResult r = new PropertyCompSearchResult();
		r.setCurrencyUnit(CurrencyUnit.USD);
		r.setSubjectProperty(buildProperty());
		r.getSubjectProperty().setFinishedSqFt(finishedSqFt);
		r.setPropertyValuations(buildPropertyCompValuationResults(valuations));
		return r;
	}

	public Collection<PropertyCompValuationResult> buildPropertyCompValuationResults(List<PropertyValuationResult> valuations) {
		List<PropertyCompValuationResult> results = new ArrayList<>();
		for (PropertyValuationResult valuation : valuations) {
			results.add(buildPropertyCompValuation(valuation));
		}
		return results;
	}

	public PropertyCompValuationResult buildPropertyCompValuation(PropertyValuationResult result) {
		PropertyCompValuationResult r = new PropertyCompValuationResult();
		r.setValuation(result);
		return r;
	}

	public PropertyValuationResult buildPropertyValuationResult(Money estimate, Money highEstimate, Money lowEstimate, int sqFt) {
		PropertyValuationResult r = new PropertyValuationResult();
		r.setCurrentEstimate(estimate);
		r.setHighValue(highEstimate);
		r.setLowValue(lowEstimate);
		r.setProperty(buildProperty());
		r.getProperty().setFinishedSqFt(sqFt);
		return r;
	}

	public AccountCreationRequest buildAccountCreationRequest() {
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
		r.setBirthDate(new LocalDate("1980-03-23"));
		return r;
	}

	public Collection<ItemizedCost> buildCosts(double amount) {
		return Arrays.asList(new ItemizedCost("Cost", amount));
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
			System.out.println(m.writeValueAsString(new ItemizedCost(item, 0)) + ",");
		}
	}

	public void buildTypicalExpences() throws Exception {
		ObjectMapper m = new ObjectMapper();
		String[] items = { "Accounting", "License Fees", "Maintenance", "Advertising", "Office Expenses", "Supplies", "Attorny and Legal Fees", "Insurance", "Property Managment", "Property Taxes", "Travel", "Leasing Commissions",
				"Salary and Wages" };

		for (String item : items) {
			System.out.println(m.writeValueAsString(new ItemizedCost(item, 0)) + ",");
		}
	}

	public RentalPropertyFinances buildPropertyFinances() {
		RentalPropertyFinances cashFlow = new RentalPropertyFinances(buildExpences(), buildIncome(), buildPurchaseDetails(BigDecimal.valueOf(350000)));

		return cashFlow;
	}

	public PurchaseDetails buildPurchaseDetails() {
		return buildPurchaseDetails(BigDecimal.valueOf(0));
	}

	public PurchaseDetails buildPurchaseDetails(BigDecimal purchasePrice) {
		PurchaseDetails p = new PurchaseDetails();
		p.setFinancingDetails(buildFinancingDetails());
		p.setPurchasePrice(purchasePrice);

		return p;
	}

	public FinancingDetails buildFinancingDetails() {
		FinancingDetails f = new FinancingDetails();
		f.setDownPayment(new BigDecimal(20000));
		f.setInterestRate(4.9F);
		f.setLoanAmount(new BigDecimal(350000));
		f.setLoanTermYears(30D);
		f.setLoanType(LoanType.AMORTIZING);

		return f;
	}

	public RentalIncome buildIncome() {
		RentalIncome i = new RentalIncome();
		i.setDeposit(new BigDecimal(2500));
		i.setGrossRent(new BigDecimal(2000));
		i.setRentUnit(Frequency.MONTHY);
		return i;
	}

	public RentalExpences buildExpences() {
		RentalExpences e = new RentalExpences();
		e.setOperatingExpences(Arrays.asList(new ReoccuringExpense("Closing costs", new BigDecimal(500), Frequency.ANNUALLY)));
		return e;
	}

	public User buildUser() {
		User user = new User();
		List<UserRole> roles = new ArrayList<>();
		roles.add(UserRole.CUSTOMER);
		user.setUserRoles(roles);

		// TODO - NG - setup test email so it's not mine
		user.setEmail("nickgilas@gmail.com");
		user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
		user.setPersonName(buildName("TestFirstName", "TestLastName"));
		user.setBirthDate(org.joda.time.LocalDate.now().withYear(1980).withMonthOfYear(3).withDayOfMonth(23));
		user.setGender(Gender.MALE);
		user.setEnabled(true);
		return user;
	}

	public Property buildProperty() {
		SingleFamilyProperty p = new SingleFamilyProperty();
		p.setAddress(buildAddress());
		p.setName("My single family home");
		p.setBathRooms(2.5D);
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

	public Account buildAccount(String ownerId) {
		Account a = new Account();
		a.setName("Test Account");
		a.setCreateDate(DateTime.now());
		a.setOwnerId(ownerId);
		a.setStatus(Status.ACTIVE);

		return a;
	}

	public AuthToken buildAuthToken(String userId) {
		AuthToken a = new AuthToken();
		a.setTimeToLiveSeconds(5000);
		a.setToken(UUID.randomUUID().toString());
		a.setUserId(userId);
		return a;
	}

	public AccountSettings buildAccountSettings() {
		AccountSettings s = new AccountSettings();
		s.setCurrency(CurrencyUnit.USD);
		return s;
	}

}
