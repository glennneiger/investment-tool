package com.cloud99.invest.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.BaseMockitoTest;
import com.cloud99.invest.domain.financial.ItemizedCost;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

public class UtilTest extends BaseMockitoTest {

	private Util util = new Util();

	@Test
	public void testRemoveSpecialCharacters() {

		String invalidCharsString = "Hello !@#(*^%&$*%,.??((\\ TEST";
		String result = util.removeSpecialCharacters(invalidCharsString);
		assertEquals("Hello  TEST", result);
	}
}
