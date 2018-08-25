package com.cloud99.invest.domain.account;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to capture all of the addition options
 * that are associated at the account level. 
 */
public class AccountOptions {
	private static final long serialVersionUID = -2336061599993466226L;


	// tracks how many documents an individual account has associated with it
	@Getter
	@Setter
	private Integer storedDocumentCount = 0;

	@Setter
	@Getter
	private Integer totalDocsAllowed = 0; // -1 means infinity
	
}
