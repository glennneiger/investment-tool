package com.cloud99.invest.domain.account;

import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.Status;
import com.cloud99.invest.domain.financial.ItemizedCost;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "account")
public class Account implements MongoDocument {
	private static final long serialVersionUID = -7224716818049308814L;

	public static final String DEFAULT_TIMEZONE = "US/Mountain";

	@Id
	@Setter
	@Getter
	private String id;

	@Setter
	@Getter
	@NotNull(message = "account.name.required")
	@NotEmpty(message = "account.name.required")
	private String name;

	@Setter
	@Getter
	private DateTime createDate;

	@Setter
	@Getter
	private DateTime updateDate;

	@Setter
	@Getter
	private TimeZone timeZone = TimeZone.getTimeZone(DEFAULT_TIMEZONE);

	@Setter
	@Getter
	private Locale locale = Locale.getDefault();

	@Setter
	@Getter
	private Status status;

	@Setter
	@Getter
	private String ownerId;

	@Setter
	@Getter
	private GeneralSettings accountOptions;

	@Getter
	@Setter
	private List<ItemizedCost> holdingCostList;

	@Getter
	@Setter
	private List<ItemizedCost> expencesList;

	@Getter
	@Setter
	private List<ItemizedCost> closingCostsList;

	@Transient
	public static String getDefaultTimezone() {
		return DEFAULT_TIMEZONE;
	}

	@Override
	public String toString() {
		return toJsonString();
	}

}
