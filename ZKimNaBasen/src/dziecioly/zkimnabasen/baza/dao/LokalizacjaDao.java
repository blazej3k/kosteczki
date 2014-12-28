package dziecioly.zkimnabasen.baza.dao;

import java.sql.SQLException;
import java.util.List;

import dziecioly.zkimnabasen.baza.model.Lokalizacja;

public class LokalizacjaDao extends GenericDao<Lokalizacja, Integer> {

	public Lokalizacja czyIstniejeJesliNieToDodaj(Lokalizacja lokalizacja) {
		try {
			Lokalizacja l = getDao().queryBuilder().where()
					.eq("adres", lokalizacja.getAdres()).and()
					.eq("kategoria", lokalizacja.getKategoria())
					.queryForFirst();
			if (l != null)
				return l;
			else {
				add(lokalizacja);
				return lokalizacja;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Lokalizacja> pobierzPubliczne(String kategoria) {
		try {
			return getDao().queryBuilder().where().eq("publiczna", true).and()
					.eq("kategoria", kategoria).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
