package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.baza.model.TUzytkownik;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {
	public static final String DEBUG_TAG = "SqLiteBasen";
	
	private static final int DB_VERSION = 2;
	private static final String DB_NAME = "baza.db";

	private static final String DB_UZYTKOWNIK_TABLE = "uzytkownik";
	private static final String DB_WYDARZENIE_TABLE = "wydarzenie";
	private static final String DB_ZAPROSZENIE_TABLE = "zaproszenie";
	
	private static final String DB_CREATE_UZYTKOWNIK_TABLE = 
			"CREATE TABLE " + DB_UZYTKOWNIK_TABLE + "( " +
		            TUzytkownik.KEY_ID + " " + TUzytkownik.ID_OPTIONS + ", " +
		            TUzytkownik.KEY_NAZWA_UZYTKOWNIKA + " " + TUzytkownik.NAZWA_UZYTKOWNIKA_OPTIONS + ", " +
		            TUzytkownik.KEY_HASLO + " " + TUzytkownik.HASLO_OPTIONS + ", " +
		            TUzytkownik.KEY_TELEFON + " " + TUzytkownik.TELEFON_OPTIONS +
		            ");";
	private static final String DROP_UZYTKOWNIK_TABLE = 
			"DROP TABLE IF EXISTS " + DB_UZYTKOWNIK_TABLE;
	
	private SQLiteDatabase db;
	private Context context;
	private DatabaseHelper dbHelper;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE_UZYTKOWNIK_TABLE);
			
			Log.d(DEBUG_TAG, "Tworzenie bazy danych..");
			System.out.println("DEBUG_TAG, "+" Tworzenie bazy danych..");
			Log.d(DEBUG_TAG, "Table "+ DB_UZYTKOWNIK_TABLE + " ver. " + DB_VERSION + " created.");
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_UZYTKOWNIK_TABLE);
			
			Log.d(DEBUG_TAG, "Database updating...");
	        Log.d(DEBUG_TAG, "Table " + DB_UZYTKOWNIK_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
	        Log.d(DEBUG_TAG, "All data is lost.");
	        
	        onCreate(db);
		}
	}
	//////////////////////////////////////////// KONSTRUKTOR
	public DbAdapter (Context context) {
		this.context=context;
		Log.d(DEBUG_TAG, "KUTAS ARIWA BAZA - dostep do bazy.");
	}
	
	public long insertUzytkownik(String nazwa, String haslo, String telefon) {
		Log.d(DEBUG_TAG, "Robimy inserta.");
		
		ContentValues newTodoValues = new ContentValues();
		newTodoValues.put(TUzytkownik.KEY_NAZWA_UZYTKOWNIKA, nazwa);
		newTodoValues.put(TUzytkownik.KEY_HASLO, haslo);
		newTodoValues.put(TUzytkownik.KEY_TELEFON, telefon);
		return db.insert(DB_UZYTKOWNIK_TABLE, null, newTodoValues);
	}
	
//	public boolean updateTodo(TodoTask task) {
//		long id = task.getId();
//		String description = task.getDescription();
//		boolean completed = task.isCompleted();
//		return updateTodo(id, description, completed);
//	}
	 
//	public boolean updateTodo(long id, String description, boolean completed) {
//	    String where = TUzytkownik.KEY_ID + "=" + id;
//	    int completedTask = completed ? 1 : 0;
//	    ContentValues updateTodoValues = new ContentValues();
//	    updateTodoValues.put(TUzytkownik.KEY_DESCRIPTION, description);
//	    updateTodoValues.put(TUzytkownik.KEY_COMPLETED, completedTask);
//	    return db.update(DB_UZYTKOWNIK_TABLE, updateTodoValues, where, null) > 0;
//	}
//	
//	public boolean deleteTodo(long id){
//	    String where = TUzytkownik.KEY_ID + "=" + id;
//	    return db.delete(DB_UZYTKOWNIK_TABLE, where, null) > 0;
//	}
//	
//	public Cursor getAllTodos() {
//	    String[] columns = {TUzytkownik.KEY_ID, TUzytkownik.KEY_DESCRIPTION, TUzytkownik.KEY_COMPLETED};
//	    return db.query(DB_UZYTKOWNIK_TABLE, columns, null, null, null, null, null);
//	}
//	 
//	public TodoTask getTodo(long id) {
//	    String[] columns = {TUzytkownik.KEY_ID, TUzytkownik.KEY_DESCRIPTION, TUzytkownik.KEY_COMPLETED};
//	    String where = TUzytkownik.KEY_ID + "=" + id;
//	    Cursor cursor = db.query(DB_UZYTKOWNIK_TABLE, columns, where, null, null, null, null);
//	    TodoTask task = null;
//	    if(cursor != null && cursor.moveToFirst()) {
//	        String description = cursor.getString(TUzytkownik.DESCRIPTION_COLUMN);
//	        boolean completed = cursor.getInt(TUzytkownik.COMPLETED_COLUMN) > 0 ? true : false;
//	        task = new TodoTask(id, description, completed);
//	    }
//	    return task;
//	}
	
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




