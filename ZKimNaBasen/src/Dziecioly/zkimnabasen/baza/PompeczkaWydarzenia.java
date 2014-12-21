package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;

import android.util.Log;

public class PompeczkaWydarzenia {	
	
	public PompeczkaWydarzenia() {
		pompuj();
	}
	
	private void pompuj() {
		WydarzenieDao wydDao = new WydarzenieDao();
		Wydarzenie wydarzenie;
		ZaproszenieDao zapDao = new ZaproszenieDao();
		Zaproszenie zaproszenie = new Zaproszenie(false);
		Uzytkownik user1 = null;
		Uzytkownik user2 = null;
		Uzytkownik user3 = null;

		user1 = new Uzytkownik("B³a¿ej", "0700222333", "bombajka"); // autor wydarzenia lub cz³ek do zaproszenia - uczestnik
		user2 = new Uzytkownik("Karolina", "0700222334", "kuktasek");
		user3 = new Uzytkownik("Struœ Pêdziwietrek", "0700222335", "kojot");
		
		// nie robie tutaj userDao.add(user), bo ustawione jest tymczasowo foreignAutoCreate = true i samo sie potworzy.
		
		
		wydarzenie = new Wydarzenie("Picie wódki", "16.12.2014", "16:00", "18:00", "pijemy na umór", true); // samo wydarzenie
		wydarzenie.setUzytkownik(user1);
		wydDao.add(wydarzenie);
		zaproszenie.setUzytkownik(user2);	// stworzenie zaproszenia do wydarzenia, jedno per user
		zaproszenie.setWydarzenie(wydarzenie);
		zapDao.add(zaproszenie);			// dodanie zaproszenia do bazy
		
		wydarzenie = new Wydarzenie("Picie bimbru", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
		wydarzenie.setUzytkownik(user1);
		wydDao.add(wydarzenie);
		zaproszenie.setUzytkownik(user2);	// stworzenie zaproszenia do wydarzenia, jedno per user
		zaproszenie.setWydarzenie(wydarzenie);
		zapDao.add(zaproszenie);			// dodanie zaproszenia do bazy
		
		wydarzenie = new Wydarzenie("Picie spirytusu", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
		wydarzenie.setUzytkownik(user2);
		wydDao.add(wydarzenie);
		zaproszenie.setUzytkownik(user3);	// stworzenie zaproszenia do wydarzenia, jedno per user
		zaproszenie.setWydarzenie(wydarzenie);
		zapDao.add(zaproszenie);			// dodanie zaproszenia do bazy
		
		wydarzenie = new Wydarzenie("Picie Komandosa", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
		wydarzenie.setUzytkownik(user2);
		wydDao.add(wydarzenie);
		zaproszenie.setUzytkownik(user3);	// stworzenie zaproszenia do wydarzenia, jedno per user
		zaproszenie.setWydarzenie(wydarzenie);
		zapDao.add(zaproszenie);			// dodanie 2 zaproszeñ do bazy
		zaproszenie.setUzytkownik(user1);
		zaproszenie.setWydarzenie(wydarzenie);
		zapDao.add(zaproszenie);
		
		wydarzenie = new Wydarzenie("Picie skrzynki Harnasi", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
		wydarzenie.setUzytkownik(user3);
		wydDao.add(wydarzenie);
		zaproszenie.setUzytkownik(user2);	// stworzenie zaproszenia do wydarzenia, jedno per user
		zaproszenie.setWydarzenie(wydarzenie);
		zapDao.add(zaproszenie);			// dodanie zaproszenia do bazy
		zaproszenie.setUzytkownik(user3);	// stworzenie zaproszenia do wydarzenia, jedno per user
		zaproszenie.setWydarzenie(wydarzenie);
		zapDao.add(zaproszenie);

		Log.d("LOG", "DODALEM WYDARZENIA i ZAPKI");
		
//		wydarzenie = new Wydarzenie("Picie wódki", "Ustro", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
//		wydDao.add(wydarzenie);
//		wydarzenie = new Wydarzenie("Picie bimbru", "Politechnika", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
//		wydDao.add(wydarzenie);
//		wydarzenie = new Wydarzenie("Picie spirytusu", "Ustro", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
//		wydDao.add(wydarzenie);
//		wydarzenie = new Wydarzenie("Picie Komandosa", "Solidarnoœci", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
//		wydDao.add(wydarzenie);
//		wydarzenie = new Wydarzenie("Picie skrzynki Harnasi", "Ustro", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
//		wydDao.add(wydarzenie);
	}
}
