package com.cloud99.invest.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document(collection = "verificationToken")
public class VerificationToken extends BaseToken implements MongoDocument {
	private static final long serialVersionUID = 4154260752618407479L;

	public VerificationToken(String userId, int expireTimeInMinutes) {
		super(userId, expireTimeInMinutes);
	}

	@Override
	public String toString() {
		return toJsonString();
	}

	@Override
	public String getId() {
		return getToken();
	}

}
