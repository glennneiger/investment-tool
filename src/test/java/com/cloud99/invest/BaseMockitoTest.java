package com.cloud99.invest;

import com.cloud99.invest.util.Util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
// @RunWith(MockitoJUnitRunner.class)
@RunWith(JUnitPlatform.class)
public abstract class BaseMockitoTest {

	protected Util util = new Util();

	protected DataCreator dataCreator = new DataCreator();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
}
