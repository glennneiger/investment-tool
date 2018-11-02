package com.cloud99.invest.domain.financial.flip;

import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.validation.GreaterThanZero;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

public class FlipPropertyFinances implements MongoDocument, PropertyFinances {
	private static final long serialVersionUID = -5046774151248258921L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String propertyId;

	@Valid
	@Getter
	@Setter
	private PurchaseDetails purchaseDetails;

	@NotNull(message = "after.repair.value.required")
	@GreaterThanZero
	@Getter
	@Setter
	private BigDecimal afterRepairValue = BigDecimal.valueOf(0);

	@NotNull(message = "desired.profit.required")
	@GreaterThanZero
	@Getter
	@Setter
	private BigDecimal desiredProfit = BigDecimal.valueOf(0);

	@NotNull(message = "holding.days.required")
	@Getter
	@Setter
	private Integer holdingDays = 1;

	@Getter
	@Setter
	private Collection<ItemizedCost> holdingCosts = new ArrayList<>(0);

	@Getter
	@Setter
	private Collection<ItemizedCost> saleClosingCosts = new ArrayList<>(0);

}
