package com.cloud99.invest.integration.data.zillow.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

	private String forSaleByOwner;

	private String overview;

	private String forSale;

	private String homedetails;

	private String graphsanddata;

	public String getGraphsanddata() {
		return graphsanddata;
	}

	public void setGraphsanddata(String graphsanddata) {
		this.graphsanddata = graphsanddata;
	}

	public String getHomedetails() {
		return homedetails;
	}

	public void setHomedetails(String homedetails) {
		this.homedetails = homedetails;
	}

	public String getForSaleByOwner() {
		return forSaleByOwner;
	}

	public void setForSaleByOwner(String forSaleByOwner) {
		this.forSaleByOwner = forSaleByOwner;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getForSale() {
		return forSale;
	}

	public void setForSale(String forSale) {
		this.forSale = forSale;
	}

	@Override
	public String toString() {
		return "ClassPojo [forSaleByOwner = " + forSaleByOwner + ", overview = " + overview + ", forSale = " + forSale
				+ "]";
	}
}
