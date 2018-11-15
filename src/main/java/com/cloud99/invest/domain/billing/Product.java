package com.cloud99.invest.domain.billing;

import com.cloud99.invest.domain.MongoDocument;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Product implements MongoDocument {

	@Id
	private String id;

}
