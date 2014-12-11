package Dziecioly.zkimnabasen.baza.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Zaproszenie {

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	boolean wezmie_udzial;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate=true)
	private Uzytkownik uzytkownik;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate=true)
	private Wydarzenie wydarzenie;
	
	
	public Zaproszenie() {
		// TODO Auto-generated constructor stub
	}
	

	public Zaproszenie(boolean wezmie_udzial) {
		super();
		this.wezmie_udzial = wezmie_udzial;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isWezmie_udzial() {
		return wezmie_udzial;
	}

	public void setWezmie_udzial(boolean wezmie_udzial) {
		this.wezmie_udzial = wezmie_udzial;
	}


	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}


	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}


	public Wydarzenie getWydarzenie() {
		return wydarzenie;
	}


	public void setWydarzenie(Wydarzenie wydarzenie) {
		this.wydarzenie = wydarzenie;
	}
	
	
	
}
