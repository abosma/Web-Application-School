package nl.hu.v1wac.thirdapp.webservices;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import nl.hu.v1wac.thirdapp.model.*;
import nl.hu.v1wac.thirdapp.persistence.CountryDAO;

@Path("/countries")
public class Main {
	
	CountryDAO dao = new CountryDAO();
	
	static WorldService ws = ServiceProvider.getWorldService();
	
	@GET
	@Produces("application/json")
	public String getAllCountries(){
		List<Country> allCountries = ws.getAllCountries();
		
		if(allCountries == null){
			throw new WebApplicationException("Something went wrong with getting all countries!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		allCountries.forEach(c -> jab.add(countryBuild(c)));
		return jab.build().toString();
	}
	
	@GET
	@Path("{ccode}")
	@Produces("application/json")
	public String getByCountryCode(@PathParam("ccode") String ccode ){
		Country c = ws.getCountryByCode(ccode.toUpperCase());
		
		if(c == null){
			throw new WebApplicationException("No country found with that code!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder().add(countryBuild(c));
		return jab.build().toString();	
	}
	
	@GET
	@Path("/largestsurfaces")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLargestSurfaces(){
		List<Country> allCountries = ws.get10LargestSurfaces();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		allCountries.forEach(c -> jab.add(countryBuild(c)));
		return jab.build().toString();
	}
	
	@GET
	@Path("/largestpopulations")
	@Produces("application/json")
	public String getLargestPopulations(){
		List<Country> allCountries = ws.get10LargestPopulations();
		
		if(allCountries == null){
			throw new WebApplicationException("Something went wrong with getting all countries!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		allCountries.forEach(c -> jab.add(countryBuild(c)));
		return jab.build().toString();
	}
	
	@DELETE
	@Path("{ccode}/deletecountry")
	public boolean deleteCountry(@PathParam("ccode") String ccode){
		List<Country> allCountries = ws.get10LargestPopulations();
		
		if(allCountries == null){
			throw new WebApplicationException("Something went wrong with getting all countries!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		allCountries.forEach(c -> jab.add(countryBuild(c)));
		return false;
	}
	
	@POST
	@Path("{ccode}/insertcountry")
	public boolean insertCountry(@PathParam("ccode") String ccode){
		List<Country> allCountries = ws.get10LargestPopulations();
		
		if(allCountries == null){
			throw new WebApplicationException("Something went wrong with getting all countries!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		allCountries.forEach(c -> jab.add(countryBuild(c)));
		return false;
	}
	
	@PUT
	@Path("{ccode}/updatecountry")
	public boolean updateCountry(@PathParam("ccode") String ccode){
		List<Country> allCountries = ws.get10LargestPopulations();
		
		if(allCountries == null){
			throw new WebApplicationException("Something went wrong with getting all countries!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		allCountries.forEach(c -> jab.add(countryBuild(c)));
		return false;
	}
	
	public JsonObjectBuilder countryBuild(Country c){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("code", c.getCode());
		job.add("name", c.getName());
		job.add("capital", c.getCapital());
		job.add("surface", c.getSurface());
		job.add("government", c.getGovernment());
		job.add("lat", c.getLatitude());
		job.add("iso3", c.getIso3Code());
		job.add("continent", c.getContinent());
		job.add("region", c.getRegion());
		job.add("population", c.getPopulation());
		job.add("lng", c.getLongitude());
		return job;
	}
	
}
