package Dziecioly.zkimnabasen.baza.model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Wydarzenie {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private String nazwa;

	@DatabaseField
	private String data;

	@DatabaseField
	private String godz_od;

	@DatabaseField
	private String godz_do;

	@DatabaseField
	private String opis;

	@DatabaseField
	private boolean otwarte;

	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Uzytkownik uzytkownik;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Lokalizacja lokalizacja;

	@ForeignCollectionField
	private ForeignCollection<Zaproszenie> zaproszenia;

	public Wydarzenie() {
		// TODO Auto-generated constructor stub
	}

	public Wydarzenie(String nazwa, Uzytkownik uzytkownik, String lokalizacja, String data,
			String godz_od, String godz_do, String opis, boolean otwarte) {
		super();
		this.nazwa = nazwa;
<<<<<<< HEAD
		this.uzytkownik = uzytkownik;
		this.lokalizacja = lokalizacja;
=======
>>>>>>> refs/remotes/origin/master
		this.data = data;
		this.godz_od = godz_od;
		this.godz_do = godz_do;
		this.opis = opis;
		this.otwarte = otwarte;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getGodz_od() {
		return godz_od;
	}

	public void setGodz_od(String godz_od) {
		this.godz_od = godz_od;
	}

	public String getGodz_do() {
		return godz_do;
	}

	public void setGodz_do(String godz_do) {
		this.godz_do = godz_do;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public boolean isOtwarte() {
		return otwarte;
	}

	public void setOtwarte(boolean otwarte) {
		this.otwarte = otwarte;
	}

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

	public List<Zaproszenie> getZaproszenia() {
		ArrayList<Zaproszenie> zaproszenieList = new ArrayList<Zaproszenie>();
		for (Zaproszenie z : zaproszenia) {
			zaproszenieList.add(z);
		}
		return zaproszenieList;
	}

	public void setZaproszenia(ForeignCollection<Zaproszenie> zaproszenia) {
		this.zaproszenia = zaproszenia;
	}

	public Lokalizacja getLokalizacja() {
		return lokalizacja;
	}

	public void setLokalizacja(Lokalizacja lokalizacja) {
		this.lokalizacja = lokalizacja;
	}
	
	

}
