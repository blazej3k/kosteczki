package Dziecioly.zkimnabasen.baza;

import java.sql.SQLException;
import java.util.List;

import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import android.content.Context;

public class DatabaseManager {

	 static private DatabaseManager instance;

	    static public void init(Context ctx) {
	        if (null==instance) {
	            instance = new DatabaseManager(ctx);
	        }
	    }

	    static public DatabaseManager getInstance() {
	        return instance;
	    }

	    private DatabaseHelper helper;
	    private DatabaseManager(Context ctx) {
	        helper = new DatabaseHelper(ctx);
	    }

	    public DatabaseHelper getHelper() {
	        return helper;
	    }

	    public List<Uzytkownik> getAllWishLists() {
	        List<Uzytkownik> uzytkownikList = null;
	        try {
	        	uzytkownikList = getHelper().getUzytkownikDao().queryForAll();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return uzytkownikList;
	}
}
