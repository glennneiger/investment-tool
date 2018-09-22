package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.BaseDomainObject;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Document
public class Expences extends BaseDomainObject {
	private static final long serialVersionUID = -4305901713759899401L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Expences.class);

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private float vacancyRate = 0.0F;

	@Getter
	@Setter
	private Collection<ReoccuringExpense> operatingExpences = new ArrayList<>(0);

	// TODO - NG - I think this can be moved to the Util class and reused by all,
	// just need to inject an instance of Util in this class
	@Transient
	public Money getTotalAnnualOperatingExpences(CurrencyUnit currency) {

		Money total = Money.of(currency, 0);

		if (operatingExpences != null) {

			for (ReoccuringExpense cost : operatingExpences) {
				BigDecimal annual = cost.getCost().multiply(new BigDecimal(cost.getNumberOfPeriodsAnnually().getAnnualPeriods(), new MathContext(2, RoundingMode.HALF_EVEN)));
				total = total.plus(annual, RoundingMode.CEILING);
			}
		}

		LOGGER.debug(total.getAmount() + " - annual operating expences");
		return total;
	}

}
