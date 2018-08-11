package com.cloud99.invest.domain.account;

import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.Status;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.repo.extensions.CascadeSave;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "Account")
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
	private Integer numberOfPropertiesAllowed;

	@CascadeSave
	private Collection<User> assignedUsers;

	@Setter
	@Getter
	private String ownerId;

	@Transient
	public static String getDefaultTimezone() {
		return DEFAULT_TIMEZONE;
	}

	public void addAssignedUser(User user) {
		Collection<User> users = getAssignedUsers();
		users.add(user);
	}

	public Collection<User> getAssignedUsers() {
		if (assignedUsers == null) {
			assignedUsers = new ArrayList<>(Arrays.asList());
		}
		return assignedUsers;
	}

	public void setAssignedUsers(Collection<User> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
