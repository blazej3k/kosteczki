package Dziecioly.zkimnabasen.baza.dao;

import java.sql.SQLException;
import java.util.List;

import Dziecioly.zkimnabasen.baza.model.Uzytkownik;

public class UzytkownikDao extends GenericDao<Uzytkownik, Integer>{
	

	
	 public List<Uzytkownik> getAllWishLists() {
	        List<Uzytkownik> uzytkownikList = null;
	        try {
	        	uzytkownikList = getDao().queryForAll();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return uzytkownikList;
	}
	 
		 
	
}
