package com.cloud99.invest.domain;

import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.repo.extensions.CascadeSave;
import com.cloud99.invest.validation.PasswordMatches;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@PasswordMatches
@Document
public class User extends Person implements MongoDocument, Authentication {
	private static final long serialVersionUID = 1445414593887068772L;

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
	private boolean isAuthenticated;

	private DateTime lastLoginDate;

	@CascadeSave
	private Collection<UserRole> userRoles = new ArrayList<>(0);

	// mongo objectId references to all properties user has access to
	@CascadeSave
	private List<String> propertyRefs = new ArrayList<>(0);

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return buildAuthorities(this.getUserRoles());
	}

	private Collection<? extends GrantedAuthority> buildAuthorities(Collection<UserRole> applicationRoles) {

		Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
		for (UserRole role : applicationRoles) {
			auths.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		return auths;
	}

	public List<String> getPropertyRefs() {
		return propertyRefs;
	}

	public void setPropertyRefs(List<String> propertyRefs) {
		this.propertyRefs = propertyRefs;
	}

	public DateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(DateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

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

	@Override
	public String getName() {
		return getEmail();
	}

	@Override
	public Object getCredentials() {
		return getAuthorities();
	}

	@Override
	public Object getDetails() {
		return this;
	}

	@Override
	public Object getPrincipal() {
		return this;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.setAuthenticated(isAuthenticated);
	}

}
