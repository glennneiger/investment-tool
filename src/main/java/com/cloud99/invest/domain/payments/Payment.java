package com.cloud99.invest.domain.payments;

import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.User;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Getter;
import lombok.Setter;

public class Payment implements MongoDocument {
	private static final long serialVersionUID = -4821653918788054532L;

	@Getter
	@Setter
	private String id;

	@DBRef
	@Getter
	@Setter
	private User requestor;

}
