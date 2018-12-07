package com.cloud99.invest;

import com.cloud99.invest.util.Util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public abstract class MockitoTest implements ReusableTestMocks {

	protected static final String ACCT_ID = "1";

	protected Util util = new Util();

	protected DataCreator dataCreator = new DataCreator();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	public <T> T mock(Class<T> clazz) {
		return Mockito.mock(clazz);
	}

	public Money money(int i) {
		return Money.of(CurrencyUnit.USD, i);
	}
}
