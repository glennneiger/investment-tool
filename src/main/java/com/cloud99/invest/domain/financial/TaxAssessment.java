package com.cloud99.invest.domain.financial;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class TaxAssessment implements Serializable {
	private static final long serialVersionUID = -665517773357050167L;

	@Getter
	@Setter
	private Integer taxYear;

	@Getter
	@Setter
	private Double taxAssessment;
}
