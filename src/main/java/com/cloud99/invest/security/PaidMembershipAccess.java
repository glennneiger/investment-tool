package com.cloud99.invest.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.cloud99.invest.domain.account.MembershipType;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation that will be evaluated to see if a user has a PAID membership or
 * not. This is used to secure features that are paid.
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@PreAuthorize("hasPermission('" + MembershipType.MEMBERSHIP_TYPE_REF_NAME + "', 'PAID')")
public @interface PaidMembershipAccess {

}
