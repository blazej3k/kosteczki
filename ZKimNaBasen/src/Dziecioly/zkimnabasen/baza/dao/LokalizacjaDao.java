package Dziecioly.zkimnabasen.baza.dao;

import java.sql.SQLException;

import Dziecioly.zkimnabasen.baza.model.Lokalizacja;

public class LokalizacjaDao extends GenericDao<Lokalizacja, Integer> {

	public Lokalizacja pobierzLokalizacje(String adres, String kategoria) {
		try {
			return getDao().queryBuilder().where().eq("adres", adres).and()
					.eq("kategoria", kategoria).queryForFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
