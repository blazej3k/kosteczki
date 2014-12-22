package Dziecioly.zkimnabasen.baza.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import Dziecioly.zkimnabasen.activity.General;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;
import android.R.integer;
import android.util.Log;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.Where;

public class WydarzenieDao extends GenericDao<Wydarzenie, Integer> {

	public List<Wydarzenie> pobierzWydarzenia() {
		Date currDate = new Date();
		String string = General.stringFromDate(currDate);
		currDate = General.dateFromString(string);
		try {
			return getDao().queryBuilder().orderBy("data", true)
					.orderBy("godz_od", true).where().ge("data", currDate)
					.query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public ForeignCollection<Zaproszenie> getEmptyCollection() {
		try {
			return getDao().getEmptyForeignCollection("zaproszenia");
		} catch (SQLException e) {
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
		return null;

	}

}
