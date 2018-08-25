package com.cloud99.invest.integration;

/**
 * Used to convert from one object type to another.
 */
public interface MessageConverter<A, B> {

	A convert(B incoming);

}