package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;

public class PompeczkaWydarzenia {	
	private int ile;
	
	public PompeczkaWydarzenia(int ile) {
		this.ile=ile;
		
		pompuj();
	}
	
	private void pompuj() {
		WydarzenieDao wydDao = new WydarzenieDao();
		Wydarzenie wydarzenie;
		Uzytkownik user = new Uzytkownik("B�a�ej", "0700222333", "bombajka");
		
		
		wydarzenie = new Wydarzenie("Picie w�dki", "Ustro", "16.12.2014", "16:00", "18:00", "pijemy na um�r", true);
		wydDao.add(wydarzenie);
		wydarzenie = new Wydarzenie("Picie bimbru", "Politechnika", "16.12.2014", "16:00", "18:00", "pijemy na um�r", true);
		wydDao.add(wydarzenie);
		wydarzenie = new Wydarzenie("Picie spirytusu", "Ustro", "16.12.2014", "16:00", "18:00", "pijemy na um�r", true);
		wydDao.add(wydarzenie);
		wydarzenie = new Wydarzenie("Picie Komandosa", "Solidarno�ci", "16.12.2014", "16:00", "18:00", "pijemy na um�r", true);
		wydDao.add(wydarzenie);
		wydarzenie = new Wydarzenie("Picie skrzynki Harnasi", "Ustro", "16.12.2014", "16:00", "18:00", "pijemy na um�r", true);
		wydDao.add(wydarzenie);
	}
}
