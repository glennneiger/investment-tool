package com.cloud99.invest.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.BaseMockitoTest;

import org.junit.jupiter.api.Test;

public class UtilTest extends BaseMockitoTest {

	private Util util = new Util();

	@Test
	public void testRemoveSpecialCharacters() {

		String invalidCharsString = "Hello !@#(*^%&$*%,.??((\\ TEST";
		String result = util.removeSpecialCharacters(invalidCharsString);
		assertEquals("Hello  TEST", result);
	}
}
