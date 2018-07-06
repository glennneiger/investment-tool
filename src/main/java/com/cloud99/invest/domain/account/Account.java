package com.cloud99.invest.domain.account;

import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.repo.extensions.CascadeSave;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Collection;
import java.util.Locale;
import java.util.TimeZone;

// TODO - NG - need to add  property for number of document or size that can be stored on our servers
public class Account implements MongoDocument<String> {

	public static final String DEFAULT_TIMEZONE = "US/Mountain";

	public static enum Status {
		CLOSED, ACTIVE, SUPPENDED, PENDING
	}

	@Id
	private String id;

	@NotNull(message = "account.name.required")
	@NotEmpty(message = "account.name.required")
	private String name;

	private DateTime createDate;
	private DateTime updateDate;
	private TimeZone timeZone = TimeZone.getTimeZone(DEFAULT_TIMEZONE);
	private Locale locale = Locale.getDefault();

	private Status status;

	private Integer numberOfPropertiesAllowed;

	@DBRef
	@CascadeSave
	private Collection<User> assignedUsers;

	private User owner;

	public Integer getNumberOfPropertiesAllowed() {
		return numberOfPropertiesAllowed;
	}

	public void setNumberOfPropertiesAllowed(Integer numberOfPropertiesAllowed) {
		this.numberOfPropertiesAllowed = numberOfPropertiesAllowed;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public static String getDefaultTimezone() {
		return DEFAULT_TIMEZONE;
	}

	public Collection<User> getAssignedUsers() {
		return assignedUsers;
	}

	public void setAssignedUsers(Collection<User> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public DateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
