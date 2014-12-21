package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;

public class PompeczkaUzytkowniki {

	public PompeczkaUzytkowniki() {
		UzytkownikDao userDao = new UzytkownikDao();
		Uzytkownik user = null;
		
		user = new Uzytkownik("B³a¿ej", "0700222333", "bombajka");
		userDao.add(user);
		user = new Uzytkownik("Karolina", "0700222334", "kuktasek");
		userDao.add(user);
		user = new Uzytkownik("Struœ Pêdziwietrek", "0700222335", "kojot");
		userDao.add(user);
	}
}

