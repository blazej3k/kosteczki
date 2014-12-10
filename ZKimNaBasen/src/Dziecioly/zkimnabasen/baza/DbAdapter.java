package Dziecioly.zkimnabasen.baza;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {
	private static final String DEBUG_TAG = "SqLiteBasen";
	
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "baza.db";
	private static final String DB_TODO_TABLE = "todo";
	
	public static final String KEY_ID = "_id";
	public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final int ID_COLUMN = 0;
	
	public static final String KEY_DESCRIPTION = "description";
	public static final String DESCRIPTION_OPTIONS = "TEXT NTO NULL";
	public static final int DESCRIPTION_COLUMN = 1;
	
	public static final String KEY_COMPLETED = "completed";
	public static final String COMPLETED_OPTIONS = "INTEGER DEFAULT 0";
	public static final int COMPLETED_COLUMN = 2;
	
	private static final String DB_CREATE_TODO_TABLE = 
			"CREATE TABLE " + DB_TODO_TABLE + "( " +
		            KEY_ID + " " + ID_OPTIONS + ", " +
		            KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS + ", " +
		            KEY_COMPLETED + " " + COMPLETED_OPTIONS +
		            ");";
	private static final String DROP_TODO_TABLE = 
			"DROP TABLE IF EXISTS " + DB_TODO_TABLE;
	
	private SQLiteDatabase db;
	private Context context;
	private DatabaseHelper dbHelper;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
			//context.get
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE_TODO_TABLE);
			
			Log.d(DEBUG_TAG, "Tworzenie bazy danych..");
			System.out.println("DEBUG_TAG, "+" Tworzenie bazy danych..");
			Log.d(DEBUG_TAG, "Table "+ DB_TODO_TABLE + " ver. " + DB_VERSION + " created.");
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TODO_TABLE);
			
			Log.d(DEBUG_TAG, "Database updating...");
	        Log.d(DEBUG_TAG, "Table " + DB_TODO_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
	        Log.d(DEBUG_TAG, "All data is lost.");
	        
	        onCreate(db);
		}
	}
	
	public DbAdapter (Context context) {
		this.context=context;
	}
	
	public DbAdapter open() {
		dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLException e) {
			db = dbHelper.getReadableDatabase();
		}
		
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
}




