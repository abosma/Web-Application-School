package nl.hu.v1wac.thirdapp.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.hu.v1wac.thirdapp.persistence.CountryDAO;

public class WorldService {
	
	static CountryDAO cd = new CountryDAO();
	
	public List<Country> getAllCountries() {
		return cd.findAll();
	}
	
	public List<Country> get10LargestPopulations() {
		List<Country> allCountries = cd.findAll();
		
		Collections.sort(allCountries, new Comparator<Country>() {
			public int compare(Country c1, Country c2) {
				return c2.getPopulation() - c1.getPopulation();
			};
		});
		
		return allCountries.subList(0, 10);
	}

	public List<Country> get10LargestSurfaces() {
		List<Country> allCountries = cd.findAll();
		
		Collections.sort(allCountries, new Comparator<Country>() {
			public int compare(Country c1, Country c2) {
				return (int)(c2.getSurface() - c1.getSurface());
			};
		});
		
		return allCountries.subList(0, 10);
	}
	
	public Country getCountryByCode(String code) {
		List<Country> allCountries = cd.findAll();
		
		Country result = null;
		
		for (Country c : allCountries) {
			if (c.getCode().equals(code)) {
				result = c;
				break;
			}
		}
		
		return result;
	}
}
