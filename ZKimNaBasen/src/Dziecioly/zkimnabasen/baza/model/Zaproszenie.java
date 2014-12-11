package Dziecioly.zkimnabasen.baza.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Zaproszenie {

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	boolean wezmie_udzial;
	
	
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
	
	
}
