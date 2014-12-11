package Dziecioly.zkimnabasen.baza.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Uzytkownik {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	private String nazwa;
	
	@DatabaseField
	private int nr_tel;
	
	@DatabaseField
	private String haslo;
	
	public Uzytkownik() {
		// TODO Auto-generated constructor stub
	}

	public Uzytkownik(String nazwa, int nr_tel, String haslo) {
		super();
		this.nazwa = nazwa;
		this.nr_tel = nr_tel;
		this.haslo = haslo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public int getNr_tel() {
		return nr_tel;
	}

	public void setNr_tel(int nr_tel) {
		this.nr_tel = nr_tel;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}
	
	
}
