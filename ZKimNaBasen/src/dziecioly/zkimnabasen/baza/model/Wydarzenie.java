package dziecioly.zkimnabasen.baza.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Wydarzenie {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private String nazwa;

	@DatabaseField(dataType = DataType.DATE_STRING, canBeNull = false)
	private Date data;

	@DatabaseField
	private String godz_od;

	@DatabaseField
	private String godz_do;

	@DatabaseField
	private String opis;

	@DatabaseField
	private boolean otwarte;

	// TODO wylaczyc docelowo foreignAutoCreate
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
	private Uzytkownik uzytkownik;

	// TODO wylaczyc docelowo foreignAutoCreate
	@DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
	private Lokalizacja lokalizacja;

	@ForeignCollectionField(eager = true, maxEagerLevel = 99)
	private ForeignCollection<Zaproszenie> zaproszenia;

	// @ForeignCollectionField(eager = true, maxEagerLevel = 99)
	// private Collection <Zaproszenie> zaproszenia = new
	// ArrayList<Zaproszenie>();
	
	private int tryb;

	public Wydarzenie() {
		// TODO Auto-generated constructor stub
	}

	public Wydarzenie(String nazwa, Date data, String godz_od, String godz_do,
			String opis, boolean otwarte) {
		super();
		this.nazwa = nazwa;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
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
		// return new ArrayList<Zaproszenie>(zaproszenia) ;
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

	public int getTryb() {
		return tryb;
	}

	public void setTryb(int tryb) {
		this.tryb = tryb;
	}
	
	

}
