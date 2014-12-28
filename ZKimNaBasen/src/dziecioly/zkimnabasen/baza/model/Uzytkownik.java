package dziecioly.zkimnabasen.baza.model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Uzytkownik {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false, unique = true)
	private String nazwa;

	@DatabaseField(canBeNull = false, unique = true)
	private String nr_tel;

	@DatabaseField(canBeNull = false)
	private String haslo;

	@ForeignCollectionField
	private ForeignCollection<Wydarzenie> wydarzenia;

	@ForeignCollectionField
	private ForeignCollection<Zaproszenie> zaproszenia;

	public Uzytkownik() {
		// TODO Auto-generated constructor stub
	}

	public Uzytkownik(String nazwa, String nr_tel, String haslo) {
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

	public String getNr_tel() {
		return nr_tel;
	}

	public void setNr_tel(String nr_tel) {
		this.nr_tel = nr_tel;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public List<Wydarzenie> getWydarzenia() {
		ArrayList<Wydarzenie> wydarzenieList = new ArrayList<Wydarzenie>();
		for (Wydarzenie w : wydarzenia) {
			wydarzenieList.add(w);
		}
		return wydarzenieList;
	}

	public void setWydarzenia(ForeignCollection<Wydarzenie> wydarzenia) {
		this.wydarzenia = wydarzenia;
	}

	public List<Zaproszenie> getZaprozenia() {
		ArrayList<Zaproszenie> zaproszenieList = new ArrayList<Zaproszenie>();
		for (Zaproszenie z : zaproszenia) {
			zaproszenieList.add(z);
		}
		return zaproszenieList;
	}

	public void setZaproszenia(ForeignCollection<Zaproszenie> zaproszenia) {
		this.zaproszenia = zaproszenia;
	}

}
