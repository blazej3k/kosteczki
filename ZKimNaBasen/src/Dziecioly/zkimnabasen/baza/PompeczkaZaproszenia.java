package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;

public class PompeczkaZaproszenia {
	
	public PompeczkaZaproszenia() {
		ZaproszenieDao zapDao = new ZaproszenieDao();
		Zaproszenie zaproszenie = new Zaproszenie(false);
		Uzytkownik user = null;
		
//		user = new Uzytkownik("B³a¿ej", "0700222333", "bombajka");
//		zaproszenie.setUzytkownik(uzytkownik);
//		
//		
//		
//		user = new Uzytkownik("Karolina", "0700222334", "kuktasek");
//		zaproszenie.setUzytkownik(uzytkownik);
		
		System.out.println("Pitu pitu");
	}
	
	
}
