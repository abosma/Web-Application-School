package nl.hu.v1wac.thirdapp.webservices;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import nl.hu.v1wac.thirdapp.model.*;
import nl.hu.v1wac.thirdapp.persistence.CountryDAO;

@Path("/countries")
public class Main {
	
	CountryDAO dao = new CountryDAO();
	
	@GET
	@RolesAllowed("User")
	@Produces("application/json")
	public String getAllCountries(){
		List<Country> allCountries = dao.findAll();
		
		if(allCountries == null){
			throw new WebApplicationException("Something went wrong with getting all countries!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		allCountries.forEach(c -> jab.add(countryBuild(c)));
		return jab.build().toString();
	}
	
	@GET
	@RolesAllowed("User")
	@Path("{ccode}")
	@Produces("application/json")
	public String getByCountryCode(@PathParam("ccode") String ccode ){
		Country c = dao.findByCode(ccode);
		
		if(c == null){
			throw new WebApplicationException("No country found with that code!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder().add(countryBuild(c));
		return jab.build().toString();	
	}
	
	@GET
	@RolesAllowed("User")
	@Path("/largestsurfaces")
	@Produces("application/json")
	public String getLargestSurfaces(){
		List<Country> allCountries = dao.find10LargestSurfaces();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		allCountries.forEach(c -> jab.add(countryBuild(c)));
		return jab.build().toString();
	}
	
	@GET
	@RolesAllowed("User")
	@Path("/largestpopulations")
	@Produces("application/json")
	public String getLargestPopulations(){
		List<Country> allCountries = dao.find10LargestPopulations();
		
		if(allCountries == null){
			throw new WebApplicationException("Something went wrong with getting all countries!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		allCountries.forEach(c -> jab.add(countryBuild(c)));
		return jab.build().toString();
	}
	
	@DELETE
	@RolesAllowed("user")
	@Path("{ccode}/deletecountry")
	public Response deleteCountry(@PathParam("ccode") String ccode){
		
		Country c = dao.findByCode(ccode);
		
		if(c == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			dao.delete(c);
			return Response.ok().build();
		}
		
	}
	
	@POST
	@RolesAllowed("user")
	@Path("/insertcountry")
	public Response insertCountry(@FormParam("code") String ccode,
								 @FormParam("capital") String capital,
								 @FormParam("continent") String continent,
								 @FormParam("government") String government,
								 @FormParam("iso3") String iso3,
								 @FormParam("latitude") double lat,
								 @FormParam("longitude") double lon,
								 @FormParam("name") String name,
								 @FormParam("population") int pop,
								 @FormParam("region") String reg,
								 @FormParam("surface") double surface){
		
		Country c = new Country(ccode, iso3, name, capital, continent, reg, surface, pop, government, lat, lon);
		
		Country response = dao.save(c);
		
		if(response == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			return Response.ok().build();
		}
	}
	
	@PUT
	@RolesAllowed("user")
	@Path("{ccode}/updatecountry")
	public Response updateCountry(@PathParam("ccode") String ccode,
								 @FormParam("capital") String capital,
								 @FormParam("continent") String continent,
								 @FormParam("government") String government,
								 @FormParam("iso3") String iso3,
								 @FormParam("latitude") double lat,
								 @FormParam("longitude") double lon,
								 @FormParam("name") String name,
								 @FormParam("population") int pop,
								 @FormParam("region") String reg,
								 @FormParam("surface") double surface){
		
		Country c = new Country(ccode, iso3, name, capital, continent, reg, surface, pop, government, lat, lon);
		
		Country response = dao.update(c);
		
		if(response == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			return Response.ok().build();
		}
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
