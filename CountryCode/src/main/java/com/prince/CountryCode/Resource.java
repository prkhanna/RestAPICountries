package com.prince.CountryCode;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("/country")
public class Resource {
	
	CountryDetails countryDetails = new CountryDetails();
	
	
	

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public void getAllDetails() {
		
	 countryDetails.getAllCountry();
		
		
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Path("abc")
	public Countries getDetailss() {
		
		Countries cnty = countryDetails.getOnlineCountries();
		return cnty;
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("creates")
	public void createEmp() {
		countryDetails.createCountries();
		
	}
}
