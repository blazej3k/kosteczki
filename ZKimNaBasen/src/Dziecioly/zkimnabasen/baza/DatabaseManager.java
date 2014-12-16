package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseManager extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "zKimNaBasen.sqlite";
	private static final int DATABASE_VERSION = 6;
	public static final String DEBUG_TAG = "SqLiteBasen";
	
	private static final String DROP_TABLE_UZYTKOWNIK = 
			"DROP TABLE IF EXISTS Uzytkownik";
	private static final String DROP_TABLE_ZAPROSZENIE = 
			"DROP TABLE IF EXISTS Zaproszenie";
	private static final String DROP_TABLE_WYDARZENIE = 
			"DROP TABLE IF EXISTS Wydarzenie";

	static private DatabaseManager instance;

	static public void init(Context ctx) {
		if (null == instance) {
			instance = new DatabaseManager(ctx);
		}
	}

	static public DatabaseManager getInstance() {
		return instance;
	}

	public DatabaseManager(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void close() {
		try {
			getConnectionSource().close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Uzytkownik.class);
			TableUtils.createTable(connectionSource, Wydarzenie.class);
			TableUtils.createTable(connectionSource, Zaproszenie.class);
			Log.d(DEBUG_TAG, "Database creating...");
		} catch (SQLException e) {
			Log.e(DatabaseManager.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		
		db.execSQL(DROP_TABLE_UZYTKOWNIK);
		db.execSQL(DROP_TABLE_WYDARZENIE);
		db.execSQL(DROP_TABLE_ZAPROSZENIE);
		
		Log.d(DEBUG_TAG, "Database updating...");
        Log.d(DEBUG_TAG, "All data is lost.");
        
        onCreate(db, connectionSource);
	

	}

}