package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.activity.General;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
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
	public static final int DATABASE_VERSION = 1;
	public static final String DEBUG_TAG = "SqLiteBasen";
	public static final String DEBUG_TAG2 = "DBManager";

	private static Context ctx;

	private static final String DROP_TABLE_UZYTKOWNIK = "DROP TABLE IF EXISTS Uzytkownik";
	private static final String DROP_TABLE_ZAPROSZENIE = "DROP TABLE IF EXISTS Zaproszenie";
	private static final String DROP_TABLE_WYDARZENIE = "DROP TABLE IF EXISTS Wydarzenie";
	private static final String DROP_TABLE_LOKALIZACJA = "DROP TABLE IF EXISTS Lokalizacja";

	private SQLiteDatabase db;
	static private DatabaseManager instance;

	static public void init(Context ctx) {

		if (null == instance) {
			DatabaseManager.ctx = ctx;
			instance = new DatabaseManager(ctx);
		}
	}

	public SQLiteDatabase getDb() {
		return db;
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
			Log.d(DEBUG_TAG2, "Closing connection");
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			db = database;
			Log.d(DEBUG_TAG2, "on create");
			TableUtils.createTable(connectionSource, Uzytkownik.class);
			TableUtils.createTable(connectionSource, Wydarzenie.class);
			TableUtils.createTable(connectionSource, Zaproszenie.class);
			TableUtils.createTable(connectionSource, Lokalizacja.class);

			General.clearSharedPrefs(ctx);

			new PompeczkaLokalizacje();

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

		upgrade(db);

	}

	public void upgrade(SQLiteDatabase db) {
		if (db == null)
			db = instance.getWritableDatabase();
		Log.d(DEBUG_TAG2, "on update");

		db.execSQL(DROP_TABLE_UZYTKOWNIK);
		db.execSQL(DROP_TABLE_WYDARZENIE);
		db.execSQL(DROP_TABLE_ZAPROSZENIE);
		db.execSQL(DROP_TABLE_LOKALIZACJA);

		Log.d(DEBUG_TAG, "Database updating...");
		Log.d(DEBUG_TAG, "All data is lost.");

		onCreate(db, connectionSource);
	}

}
