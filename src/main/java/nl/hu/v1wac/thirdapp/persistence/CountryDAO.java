package nl.hu.v1wac.thirdapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.hu.v1wac.thirdapp.model.Country;

public class CountryDAO extends BaseDAO{
	
	List<Country> cl = new ArrayList<Country>();
	
	public CountryDAO(){
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM country";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				cl.add(new Country(rs.getString("code2"), rs.getString("code"), rs.getString("name"), rs.getString("capital"), rs.getString("continent"), rs.getString("region"), rs.getDouble("surfacearea"), rs.getInt("population"), rs.getString("governmentform"), rs.getDouble("latitude"), rs.getDouble("longitude")));
			}
		}catch(SQLException e){
			System.out.println(e);
		}
	}
	
	public Country save(Country country){
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			
			String code = country.getIso3Code();
			String name = country.getName();
			String continent = country.getContinent();
			String region = country.getRegion();
			double surfacearea = country.getSurface();
			int indepyear = 0;
			int population = country.getPopulation();
			double lifeexpectancy = 0.0;
			double gnp = 0.0;
			double gnpgold = 0.0;
			String localname = null;
			String governmentform = country.getGovernment();
			String headofstate = null;
			String code2 = country.getCode();
			double latitude = country.getLatitude();
			double longitude = country.getLongitude();
			String capital = country.getCapital();
			
			String query = "INSERT INTO country " +
						   "VALUES('" + code + "','" + name + "','" + continent + "','" + region + "'," +
						   surfacearea + "," + indepyear + "," + population + "," + lifeexpectancy + "," +
						   gnp + "," + gnpgold + ",'" + localname + "','" + governmentform + "','" +
						   headofstate + "','" + code2 + "'," + latitude + "," + longitude + ",'" + capital + "')";
			
			stmt.executeQuery(query);
			
			return country;
		}catch(SQLException e){
			return null;
		}
	}
	
	public List<Country> findAll(){
		return this.cl;
	}
	
	public Country findByCode(String inCode){
		try(Connection con = super.getConnection()){
			Country c = null;
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM country WHERE code2 LIKE '" + inCode + "'";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				c = new Country(rs.getString("code2"), rs.getString("code"), rs.getString("name"), rs.getString("capital"), rs.getString("continent"), rs.getString("region"), rs.getDouble("surfacearea"), rs.getInt("population"), rs.getString("governmentform"), rs.getDouble("latitude"), rs.getDouble("longitude"));
			}
			
			return c;
		}catch(SQLException e){
			return null;
		}
	}
	
	public List<Country> find10LargestPopulations() {
		Collections.sort(cl, new Comparator<Country>() {
			public int compare(Country c1, Country c2) {
				return c2.getPopulation() - c1.getPopulation();
			};
		});
		
		return cl.subList(0, 10);
	}
	
	public List<Country> find10LargestSurfaces() {
		Collections.sort(cl, new Comparator<Country>() {
			public int compare(Country c1, Country c2) {
				return (int)(c2.getSurface() - c1.getSurface());
			};
		});
		
		return cl.subList(0, 10);
	}
	
	public Country update(Country country){
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			
			String code = country.getIso3Code();
			String name = country.getName();
			String continent = country.getContinent();
			String region = country.getRegion();
			double surfacearea = country.getSurface();
			int indepyear = 0;
			int population = country.getPopulation();
			double lifeexpectancy = 0.0;
			double gnp = 0.0;
			double gnpgold = 0.0;
			String localname = null;
			String governmentform = country.getGovernment();
			String headofstate = null;
			String code2 = country.getCode();
			double latitude = country.getLatitude();
			double longitude = country.getLongitude();
			String capital = country.getCapital();
			
			String query = "UPDATE country SET " +
						   "(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, " + 
						   "gnp, gnpgold, localname, governmentform, headofstate, code2, latitude, longitude, capital) = " + 
						   "('" + code + "','" + name + "','" + continent + "','" + region + "'," +
						   surfacearea + "," + indepyear + "," + population + "," + lifeexpectancy + "," +
						   gnp + "," + gnpgold + ",'" + localname + "','" + governmentform + "','" +
						   headofstate + "','" + code2 + "'," + latitude + "," + longitude + ",'" + capital + "')" +
						   " WHERE code2 = '" + code2 + "'";
			
			stmt.executeQuery(query);
			
			return country;
		}catch(SQLException e){
			return null;
		}
	}
	
	public boolean delete(Country country){
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			
			String query = "DELETE FROM country WHERE code2 = " + country.getCode();
			
			stmt.executeQuery(query);
			
			return true;
		}catch(SQLException e){
			return false;
		}
	}
}
