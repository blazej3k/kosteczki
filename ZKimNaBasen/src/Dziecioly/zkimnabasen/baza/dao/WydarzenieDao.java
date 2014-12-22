package Dziecioly.zkimnabasen.baza.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import Dziecioly.zkimnabasen.baza.model.Wydarzenie;

public class WydarzenieDao extends GenericDao<Wydarzenie, Integer>{

	public List<Wydarzenie> pobierzWydarzenia()
	{
		Date currDate = new Date();
		try {
			return getDao().queryBuilder().orderBy("data", true).orderBy("godz_od", true).where().ge("data", currDate).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
}
