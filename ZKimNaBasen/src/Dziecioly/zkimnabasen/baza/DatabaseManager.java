package Dziecioly.zkimnabasen.baza;

import java.util.ArrayList;
import java.util.List;

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
	private static final int DATABASE_VERSION = 1;

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
			Log.i("DATABASE MANAGER", "BAZA UTWORZONA");
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
		try {
			List<String> allSql = new ArrayList<String>();
			switch (oldVersion) {
			case 1:
				// allSql.add("alter table AdData add column `new_col` VARCHAR");
				// allSql.add("alter table AdData add column `new_col2` VARCHAR");
			}
			for (String sql : allSql) {
				db.execSQL(sql);
			}
		} catch (SQLException e) {
			Log.e(DatabaseManager.class.getName(),
					"exception during onUpgrade", e);
			throw new RuntimeException(e);

		}

	}

}
