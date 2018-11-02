package com.cloud99.invest.domain;

import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.repo.extensions.CascadeSave;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Document
public class User extends Person implements Authentication, UserDetails {
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

	@JsonIgnore
	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private boolean enabled;

	@Transient
	@Getter
	@Setter
	@JsonIgnore
	private boolean authenticated;

	@Getter
	@Setter
	@JsonIgnore
	private DateTime lastLoginDate;

	@CascadeSave
	@Getter
	@Setter
	@JsonIgnore
	private List<UserRole> userRoles;

	// mongo objectId references to all properties user has access to\
	@DBRef
	@CascadeSave
	@Getter
	@Setter
	private List<String> propertyRefs = new ArrayList<>(Arrays.asList());

	@JsonIgnore
	@Transient
	private List<SimpleGrantedAuthority> authorities;

	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (authorities == null) {
			authorities = buildAuthorities(this.userRoles);
		}
		return authorities;
	}

	@Transient
	private List<SimpleGrantedAuthority> buildAuthorities(Collection<UserRole> applicationRoles) {
		authorities = new ArrayList<>(applicationRoles.size());
		for (UserRole role : applicationRoles) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		return authorities;
	}

	@Transient
	public void addUserRole(UserRole role) {
		if (this.userRoles == null) {
			this.userRoles = new ArrayList<>(Arrays.asList());
		}
		if (!userRoles.contains(role)) {
			userRoles.add(role);
		}
	}

	@Override
	@JsonIgnore
	public String getName() {
		return getEmail();
	}

	@Override
	@JsonIgnore
	public Object getCredentials() {
		return getAuthorities();
	}

	@Override
	@JsonIgnore
	public Object getDetails() {
		return this;
	}

	@Override
	@JsonIgnore
	public Object getPrincipal() {
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
