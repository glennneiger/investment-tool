package com.cloud99.invest.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class holds any application specific status information. For example, it
 * will hold initialization flags for reference/static data to indicate if the
 * data has been loading in the target environment.
 *
 */
@Document
public class ApplicationState implements MongoDocument {
	private static final long serialVersionUID = -2386747200601169861L;

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
