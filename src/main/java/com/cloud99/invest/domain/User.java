package com.cloud99.invest.domain;

import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.repo.extensions.CascadeSave;
import com.cloud99.invest.validation.PasswordMatches;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

@PasswordMatches
@Document
public class User extends Person implements MongoDocument<String> {

	@Id
	private String id;

	@Indexed
	@NotNull(message = "required.email")
	@NotEmpty(message = "required.email")
	@Email
	private String email;

	@NotNull(message = "password.required")
	@NotEmpty(message = "password.required")
	private String password;

	@NotNull(message = "password.required")
	@NotEmpty(message = "password.required")
	@Transient
	private String matchingPassword;

	private Locale locale;
	private boolean enabled = false;

	@CascadeSave
	private Collection<UserRole> userRoles = new ArrayList<>(0);

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Collection<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Collection<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMatchingPassword(String password) {
		this.matchingPassword = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	@Override
	public String toString() {
		return toJsonString();
	}

	public void addUserRole(UserRole role) {
		this.userRoles.add(role);

	}

}
