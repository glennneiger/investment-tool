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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

@PasswordMatches
@Document
public class User extends Person implements MongoDocument, Authentication {
	private static final long serialVersionUID = 1445414593887068772L;

	@Id
	@Getter
	@Setter
	private String id;

	@Indexed
	@NotNull(message = "required.email")
	@NotEmpty(message = "required.email")
	@Email
	@Getter
	@Setter
	private String email;

	@NotNull(message = "password.required")
	@NotEmpty(message = "password.required")
	@Getter
	@Setter
	private String password;

	@NotNull(message = "password.required")
	@NotEmpty(message = "password.required")
	@Transient
	@Getter
	@Setter
	private String matchingPassword;

	@Getter
	@Setter
	private Locale locale = Locale.getDefault();

	@Getter
	@Setter
	private boolean enabled;

	@Getter
	@Setter
	private boolean isAuthenticated;

	@Getter
	@Setter
	private DateTime lastLoginDate;

	@CascadeSave
	@Getter
	@Setter
	private List<UserRole> userRoles;

	// mongo objectId references to all properties user has access to
	@CascadeSave
	@Getter
	@Setter
	private List<String> propertyRefs = new ArrayList<>(Arrays.asList());

	private List<SimpleGrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (authorities == null) {
			authorities = buildAuthorities(this.userRoles);
		}
		return authorities;
	}

	private List<SimpleGrantedAuthority> buildAuthorities(Collection<UserRole> applicationRoles) {
		authorities = new ArrayList<>(applicationRoles.size());
		for (UserRole role : applicationRoles) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		return authorities;
	}

	@Override
	public String toString() {
		return toJsonString();
	}

	public void addUserRole(UserRole role) {
		if (this.userRoles == null) {
			this.userRoles = new ArrayList<>(Arrays.asList());
		}
		if (!userRoles.contains(role)) {
			userRoles.add(role);
		}
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

}
