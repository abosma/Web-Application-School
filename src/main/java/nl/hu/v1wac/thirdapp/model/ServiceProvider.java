package nl.hu.v1wac.thirdapp.model;

import nl.hu.v1wac.thirdapp.persistence.CountryDAO;

public class ServiceProvider {
	private static CountryDAO cd = new CountryDAO();

	public static CountryDAO getCountryDAO() {
		return cd;
	}
}