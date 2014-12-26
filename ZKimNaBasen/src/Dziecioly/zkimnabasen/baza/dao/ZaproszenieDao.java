package Dziecioly.zkimnabasen.baza.dao;

import java.sql.SQLException;

import android.util.Log;

import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;

public class ZaproszenieDao extends GenericDao<Zaproszenie, Integer> {

	public Zaproszenie pobierzZaproszenieUseraNawydarzenie(int id_uzytkownika,
			int id_wydarzenia) {
		try {
			return getDao().queryBuilder().where()
					.eq("uzytkownik_id", id_uzytkownika).and()
					.eq("wydarzenie_id", id_wydarzenia).queryForFirst();
		} catch (SQLException e) {
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
		return null;
	}

}
