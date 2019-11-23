package com.prince.CountryCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import com.prince.CountryCode.Countries.Country;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

//this is the repository class  from where we will get the response data
public class CountryDetails {
	
	
	 private static final String REST_URI  = "http://country.io/names.json";
	 
	 private static final String REST_URI_XML  = "https://supply-xml.booking.com/hotels/xml/countries";
 
		private Client client = Client.create();
		String	url = "jdbc:postgresql://localhost:8080/postgres";
		Connection con = null;
        	
        	 
        /**constructor
         * @param client
         */
        public CountryDetails() {
			
			 try {
				Class.forName("org.postgresql.Driver");
				con = 	 DriverManager.getConnection(url,"postgres", "Password@24");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				Logger.getLogger("Class not found exception");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Logger.getLogger("SQL exception");
			}
			
		}


		/**
         * GET Object of employee details from Rest api on country details
         * @return
         */
        public Countries getOnlineCountries() {
        	Countries details = client.resource(REST_URI_XML).accept(MediaType.APPLICATION_XML).get(Countries.class);
		/*
		 * Iterator<Country> abc = details.getCountry().iterator(); while(abc.hasNext())
		 * { Country element = abc.next(); System.out.println("CountryCode: " +
		 * element.getCountrycode() + " Name: " +element.getName()); }
		 */
        	
              return details;
   }
        
	/**
	 * create country code and name entry in the database
	 */
	public void createCountries() {
		String query = "insert into Countries values (?,?)";

		try {

			Iterator<Country> itr = getOnlineCountries().getCountry().iterator();
			while (itr.hasNext()) {
				Country country = itr.next();

				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, country.getCountrycode());
				ps.setString(2, country.getName());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger("problem in query while inserting data ");
		}
	}
        
        
        
        public void getAllCountry() {
        	WebResource webResource = client.resource(REST_URI);
        	ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
              if(response.getStatus() !=200) {
            	  throw new RuntimeException("http reponse incorrect" +response.getStatus());
              }
   }
}
