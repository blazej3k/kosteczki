package Dziecioly.zkimnabasen.baza.dao;

import java.sql.SQLException;
import java.util.List;

import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import android.util.Log;

import com.j256.ormlite.stmt.Where;

public class UzytkownikDao extends GenericDao<Uzytkownik, Integer> {

	@SuppressWarnings("unchecked")
	public boolean zaloguj(String login, String haslo) {
		try {
			Where<Uzytkownik, Integer> where = getDao().queryBuilder().where();
			where.and(
					where.or(where.eq("nazwa", login),
							where.eq("nr_tel", login)),
					where.eq("haslo", haslo));

			Log.d(DatabaseManager.DEBUG_TAG, where.getStatement());
			long count = where.countOf();
			Log.d(DatabaseManager.DEBUG_TAG, "countOf: " + Long.toString(count));

			if (count == 0)
				return false;
			else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Uzytkownik pobierzZalogowanegoUzytkownika(String login) {
		try {
			return getDao().queryBuilder().where().eq("nazwa", login).or()
					.eq("nr_tel", login).queryForFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Uzytkownik> pobierzZnajomych(int id) {
		try {
			return getDao().queryBuilder().orderBy("nazwa", true).where()
					.ne("id", id).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
