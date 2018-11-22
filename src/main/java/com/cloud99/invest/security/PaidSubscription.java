package com.cloud99.invest.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.cloud99.invest.domain.Subscription;
import com.cloud99.invest.domain.Subscription.SubscriptionType;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation that will be evaluated to see if a user has a
 * {@link SubscriptionType} and if so, they will be labeled as PAID for
 * application access to features.
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@PreAuthorize("hasPermission('" + Subscription.SubscriptionType.SUBSCRIPTION_REF_NAME + "', 'PAID')")
public @interface PaidSubscription {

}
