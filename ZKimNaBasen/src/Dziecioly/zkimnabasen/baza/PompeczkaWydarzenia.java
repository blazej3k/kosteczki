package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
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
		
		wydarzenie = new Wydarzenie("Picie wódki", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
		wydDao.add(wydarzenie);
		wydarzenie = new Wydarzenie("Picie bimbru", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
		wydDao.add(wydarzenie);
		wydarzenie = new Wydarzenie("Picie spirytusu", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
		wydDao.add(wydarzenie);
		wydarzenie = new Wydarzenie("Picie Komandosa", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
		wydDao.add(wydarzenie);
		wydarzenie = new Wydarzenie("Picie skrzynki Harnasi", "16.12.2014", "16:00", "18:00", "pijemy na umór", true);
		wydDao.add(wydarzenie);
	}
}
