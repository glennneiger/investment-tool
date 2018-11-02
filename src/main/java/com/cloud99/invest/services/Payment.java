package com.cloud99.invest.services;

import org.joda.money.CurrencyUnit;

import java.io.Serializable;

import lombok.Data;

@Data
public class Payment implements Serializable {
	private static final long serialVersionUID = -3647698502899316001L;

	private double amount;
	private CurrencyUnit currencyUnit = CurrencyUnit.USD;
	private String description;

}
