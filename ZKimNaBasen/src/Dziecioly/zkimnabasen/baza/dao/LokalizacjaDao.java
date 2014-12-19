package Dziecioly.zkimnabasen.baza.dao;

import java.sql.SQLException;

import Dziecioly.zkimnabasen.baza.model.Lokalizacja;

public class LokalizacjaDao extends GenericDao<Lokalizacja, Integer> {

	public Lokalizacja znajdzLokalizacjeJakNieMaToDodaj(Lokalizacja l) {
		try {
			Lokalizacja lokalizacja = getDao().queryBuilder().where()
					.eq("adres", l.getAdres()).and()
					.eq("kategoria", l.getKategoria()).queryForFirst();
			if (lokalizacja != null)
				return lokalizacja;
			else {
				add(l);
				return l;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
