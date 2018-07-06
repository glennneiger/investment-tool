package com.cloud99.invest.integration.zillow.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false)
public class Result {

	private Zestimate zestimate;

	private LocalRealEstate localRealEstate;

	private Address address;

	private Links links;

	private String zpid;

	public Zestimate getZestimate() {
		return zestimate;
	}

	public void setZestimate(Zestimate zestimate) {
		this.zestimate = zestimate;
	}

	public LocalRealEstate getLocalRealEstate() {
		return localRealEstate;
	}

	public void setLocalRealEstate(LocalRealEstate localRealEstate) {
		this.localRealEstate = localRealEstate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}

	public String getZpid() {
		return zpid;
	}

	public void setZpid(String zpid) {
		this.zpid = zpid;
	}

	@Override
	public String toString() {
		return "ClassPojo [zestimate = " + zestimate + ", localRealEstate = " + localRealEstate + ", address = "
				+ address + ", links = " + links + ", zpid = " + zpid + "]";
	}
}
