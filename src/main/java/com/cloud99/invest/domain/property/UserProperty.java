package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.MongoDocument;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

/**
 * Reference/index document to link a user with all of their properties
 */
public class UserProperty implements MongoDocument {

	@Id
	private String id;

	@Indexed
	private String userEmail;

	private List<String> propertyRefs = new ArrayList<>(0);

	public void addProperty(Property property) {
		propertyRefs.add(property.getId());
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<String> getPropertyRefs() {
		return propertyRefs;
	}

	public void setPropertyRefs(List<String> propertyRefs) {
		this.propertyRefs = propertyRefs;
	}

}
