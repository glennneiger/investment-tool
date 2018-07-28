package com.cloud99.invest;

import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
// @RunWith(MockitoJUnitRunner.class)
@RunWith(JUnitPlatform.class)
public abstract class MockitoTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
}
