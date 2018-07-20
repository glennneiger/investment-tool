package com.cloud99.invest.converters.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.springframework.core.convert.converter.Converter;

import java.time.ZonedDateTime;

public class ZonedDateTimeConverter implements Converter<ZonedDateTime, DBObject> {

	@Override
	public DBObject convert(ZonedDateTime source) {
		DBObject obj = new BasicDBObject();
		obj.put("value", source.toString());

		return obj;
	}
}
