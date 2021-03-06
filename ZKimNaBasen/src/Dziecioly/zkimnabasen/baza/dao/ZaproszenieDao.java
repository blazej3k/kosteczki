package Dziecioly.zkimnabasen.baza.dao;

import java.sql.SQLException;

import Dziecioly.zkimnabasen.baza.model.Zaproszenie;

public class ZaproszenieDao extends GenericDao<Zaproszenie, Integer> {

	public Zaproszenie pobierzZaproszenieUseraNawydarzenie(int id_uzytkownika,
			int id_wydarzenia) {
		try {
			return getDao().queryBuilder().where()
					.eq("id_uzytkownik", id_uzytkownika).and()
					.eq("id_wydarzenie", id_wydarzenia).queryForFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
