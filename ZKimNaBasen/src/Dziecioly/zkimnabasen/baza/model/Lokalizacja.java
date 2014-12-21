package Dziecioly.zkimnabasen.baza.model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Lokalizacja {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	private double lat;

	@DatabaseField
	private double lon;

	@DatabaseField
	private String adres;

	@DatabaseField
	private String opis;

	@DatabaseField
	private boolean publiczna;

	@DatabaseField
	private String kategoria;

	@ForeignCollectionField
	private ForeignCollection<Wydarzenie> wydarzenia;
	
	private boolean lokalizacjaUzytkownika = false;

	public static final String[] kategorie = { "Bie¿nia", "Fitness",
			"Kort tenisowy", "Koszykówka", "Lodowisko", "Pi³ka no¿na",
			"Pi³ka rêczna", "P³ywalnia", "Siatkówka", "Skatepark",
			"Strzelnica", "Œcianka wspinaczkowa", "Inne" };

	public Lokalizacja() {
	}

	public Lokalizacja(double lat, double lon, String adres, String opis,
			boolean publiczna, String kategoria) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.adres = adres;
		this.opis = opis;
		this.publiczna = publiczna;
		this.kategoria = kategoria;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPubliczna() {
		return publiczna;
	}

	public void setPubliczna(boolean publiczna) {
		this.publiczna = publiczna;
	}

	public List<Wydarzenie> getZaprozenia() {
		ArrayList<Wydarzenie> wydarzeniaList = new ArrayList<Wydarzenie>();
		for (Wydarzenie w : wydarzenia) {
			wydarzeniaList.add(w);
		}
		return wydarzeniaList;
	}

	public void setWydarzenia(ForeignCollection<Wydarzenie> wydarzenia) {
		this.wydarzenia = wydarzenia;
	}

	public String getKategoria() {
		return kategoria;
	}

	public void setKategoria(String kategoria) {
		this.kategoria = kategoria;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public boolean isLokalizacjaUzytkownika() {
		return lokalizacjaUzytkownika;
	}

	public void setLokalizacjaUzytkownika(boolean lokalizacjaUzytkownika) {
		this.lokalizacjaUzytkownika = lokalizacjaUzytkownika;
	}
	
	
	
	

}
