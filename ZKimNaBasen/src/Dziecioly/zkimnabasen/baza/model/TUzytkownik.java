package Dziecioly.zkimnabasen.baza.model;

public class TUzytkownik {
	
	public static final String KEY_ID = "_id";
	public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final int ID_COLUMN = 0;

	public static final String KEY_NAZWA_UZYTKOWNIKA = "nazwa_uzytkownika";
	public static final String NAZWA_UZYTKOWNIKA_OPTIONS = "TEXT NOT NULL UNIQUE";
	public static final int NAZWA_UZYTKOWNIKA_COLUMN = 1;
	
	public static final String KEY_HASLO = "haslo";
	public static final String HASLO_OPTIONS = "TEXT DEFAULT NULL";
	public static final int HASLO_COLUMN = 2;
	
	public static final String KEY_TELEFON = "telefon";
	public static final String TELEFON_OPTIONS = "TEXT DEFAULT 0";
	public static final int TELEFON_COLUMN = 3;
}
