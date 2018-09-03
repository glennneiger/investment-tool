package com.cloud99.invest.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Document(collection = "verificationToken")
public class VerificationToken implements MongoDocument {
	private static final long serialVersionUID = 4154260752618407479L;

	@Id
	@Indexed
	@Getter
	@Setter
	private String token;

	@Getter
	@Setter
	private String userId;

	@Getter
	@Setter
	private Integer timeToLiveSeconds;

	// Note: we have to use a standard java Date instead of Joda because extensions
	// of this class can't convert Joda DateTimes to native store types without more
	// custom work on our part
	@Getter
	@Setter
	private Date createTime;

	public VerificationToken(String userId, Integer timeToLiveSeconds) {
		this.userId = userId;
		this.token = UUID.randomUUID().toString();
		this.timeToLiveSeconds = timeToLiveSeconds;
		// this.expireTime = calculationExpireTime(expiryTimeInMinutes);
		this.createTime = DateTime.now().toDate();
	}

	@Transient
	public DateTime getExpireDateTime() {
		DateTime dateTime = new DateTime(createTime.getTime());
		return dateTime.plusSeconds(getTimeToLiveSeconds());
	}

	@Override
	public String getId() {
		return getToken();
	}

}
