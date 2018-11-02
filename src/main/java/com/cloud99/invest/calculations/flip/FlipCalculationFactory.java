package com.cloud99.invest.calculations.flip;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FlipCalculationFactory {

	private Map<FlipCalculationType, FlipCalculation<?>> allCalculations;

	@PostConstruct
	public void init() {
		allCalculations = new HashMap<>();
		FlipCalculationType.ALL_CALCULATIONS.forEach(calcType -> {
			try {
				allCalculations.put(calcType, (FlipCalculation<?>) calcType.getCalcClass().newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("Error creating all calculations map for type: {}, msg: {}", calcType, e.getMessage(), e);
			}
		});
	}

	public Map<FlipCalculationType, FlipCalculation<?>> getAllCalculations() {
		return allCalculations;
	}
}
