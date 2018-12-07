package com.cloud99.invest.domain.account;

import org.springframework.data.redis.connection.Subscription;

/**
 * Membership type for a given user of the application. If a user is a
 * <code>PAID</code> member then they should also have a valid
 * {@link Subscription}
 */
public enum MembershipType {

	FREE, PAID;

	// used by security evaluation for permissions, refactor friendly
	public static final String MEMBERSHIP_TYPE_REF_NAME = "MembershipType";

	public static MembershipType findByValue(String membershipName) {
		for (MembershipType type : values()) {
			if (type.name().equals(membershipName)) {
				return type;
			}
		}
		return null;
	}

}
