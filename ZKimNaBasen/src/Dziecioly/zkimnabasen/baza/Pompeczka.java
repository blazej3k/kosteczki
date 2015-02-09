package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.activity.General;
import Dziecioly.zkimnabasen.baza.dao.LokalizacjaDao;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;

public class Pompeczka {

	LokalizacjaDao lokalizacjaDao = new LokalizacjaDao();
	WydarzenieDao wydarzenieDao = new WydarzenieDao();
	UzytkownikDao uzytkownikDao = new UzytkownikDao();
	ZaproszenieDao zaproszenieDao = new ZaproszenieDao();

	public Pompeczka() {
		Uzytkownik uzytkownik1 = new Uzytkownik("karolina", "500100100",
				"kpass");
		uzytkownikDao.add(uzytkownik1);

		Uzytkownik uzytkownik2 = new Uzytkownik("b³a¿ej", "500200200", "bpass");
		uzytkownikDao.add(uzytkownik2);

		Uzytkownik uzytkownik3 = new Uzytkownik("jola", "500300300", "jpass");
		uzytkownikDao.add(uzytkownik3);
		
		Uzytkownik uzytkownik4 = new Uzytkownik("maciek", "500400400", "mpass");
		uzytkownikDao.add(uzytkownik4);

		Lokalizacja lokalizacja1 = new Lokalizacja(52.261315, 20.977423,
				"Ludwika Rydygiera 8", null, true, "Fitness");
		lokalizacjaDao.add(lokalizacja1);

		Lokalizacja lokalizacja2 = new Lokalizacja(52.244284, 20.984538,
				"Dzielna 52", "Fitness Club", true, "Fitness");
		lokalizacjaDao.add(lokalizacja2);

		Lokalizacja lokalizacja3 = new Lokalizacja(52.267764, 21.048574,
				"Smoleñska 60", "Energy Fitness", true, "Fitness");
		lokalizacjaDao.add(lokalizacja3);

		Lokalizacja lokalizacja4 = new Lokalizacja(52.244179, 20.947288,
				"Deotymy 39", "Tenis club", true, "Kort tenisowy");
		lokalizacjaDao.add(lokalizacja4);

		Lokalizacja lokalizacja5 = new Lokalizacja(52.242182, 20.997928,
				"Orla 13", "Basen", true, "P³ywalnia");
		lokalizacjaDao.add(lokalizacja5);

		Lokalizacja lokalizacja6 = new Lokalizacja(52.229402, 20.992273,
				"Twarda 56A", null, true, "P³ywalnia");
		lokalizacjaDao.add(lokalizacja6);

		Lokalizacja lokalizacja7 = new Lokalizacja(52.249991, 21.035679,
				"Jagielloñska 7", "DOSiR Praga Pó³noc - P³ywalnia Prawy brzeg",
				false, "P³ywalnia");
		lokalizacjaDao.add(lokalizacja6);

		// wydarzenie 1 - uzytkownik 1, lokalizacja 7, zaproszenia (2 3 4)
		Wydarzenie wydarzenie1 = new Wydarzenie("Wypad na basen",
				General.dateFromString("01.03.2015"), "14:00", "16:00",
				"Niedziela to œwietny dzieñ na odrobinê relaksu! :) Proponujê wspólne wyjœcie na basen. Tego dnia na Rydygiera jest promocja -wejœcie 50% taniej!", false); 																			
		wydarzenie1.setUzytkownik(uzytkownik1);
		wydarzenie1.setLokalizacja(lokalizacja7);
		wydarzenieDao.add(wydarzenie1);
		
		Zaproszenie zaproszenie1 = new Zaproszenie(true);
		zaproszenie1.setUzytkownik(uzytkownik2);
		zaproszenie1.setWydarzenie(wydarzenie1);
		zaproszenieDao.add(zaproszenie1);
		
		Zaproszenie zaproszenie2 = new Zaproszenie(false);
		zaproszenie2.setUzytkownik(uzytkownik3);
		zaproszenie2.setWydarzenie(wydarzenie1);
		zaproszenieDao.add(zaproszenie2);
		
		Zaproszenie zaproszenie3 = new Zaproszenie(true);
		zaproszenie3.setUzytkownik(uzytkownik4);
		zaproszenie3.setWydarzenie(wydarzenie1);
		zaproszenieDao.add(zaproszenie3);
		
		
		// wydarzenie 2 - uzytkownik 1, lokalizacja 1,  zaproszenie (3)
		Wydarzenie wydarzenie2 = new Wydarzenie("Poniedzia³kowy fitness",
				General.dateFromString("02.03.2015"), "18:30", "20:00",
				"Trzeba dobrze rozpocz¹æ tydzieñ. W poniedzia³ki o 18:30 jest areobik Fitness Clubie; by³am w zesz³ym tygodniu - by³o super", false); 																	
		wydarzenie2.setUzytkownik(uzytkownik1);
		wydarzenie2.setLokalizacja(lokalizacja1);
		wydarzenieDao.add(wydarzenie2);
		
		Zaproszenie zaproszenie4 = new Zaproszenie(false);
		zaproszenie4.setUzytkownik(uzytkownik3);
		zaproszenie4.setWydarzenie(wydarzenie2);
		zaproszenieDao.add(zaproszenie4);
		
		
		// wydarzenie 3 - uzytkownik 2, lokalizacja 4,  otwarte (1)
		Wydarzenie wydarzenie3 = new Wydarzenie("Tenis",
				General.dateFromString("01.03.2015"), "11:00", null,
				"Ktoœ chêtny? :)", true); 																	
		wydarzenie3.setUzytkownik(uzytkownik2);
		wydarzenie3.setLokalizacja(lokalizacja4);
		wydarzenieDao.add(wydarzenie3);
		
		Zaproszenie zaproszenie7 = new Zaproszenie(true);
		zaproszenie7.setUzytkownik(uzytkownik1);
		zaproszenie7.setWydarzenie(wydarzenie3);
		zaproszenieDao.add(zaproszenie7);
		
		
		// wydarzenie 4 - uzytkownik 2, lokalizacja 6,  otwarte
		Wydarzenie wydarzenie4 = new Wydarzenie("Godzinka p³ywania",
				General.dateFromString("04.03.2015"), "17:30", "18:30",
				"Zapraszam na wspólne pokonanie kilku d³ugoœci basenu.", true); 																	
		wydarzenie4.setUzytkownik(uzytkownik2);
		wydarzenie4.setLokalizacja(lokalizacja6);
		wydarzenieDao.add(wydarzenie4);
		
		
		// wydarzenie 5 - uzytkownik 3, lokalizacja 3,  zaproszenia (1, 2)
		Wydarzenie wydarzenie5 = new Wydarzenie("Pilates",
				General.dateFromString("07.03.2015"), "12:15", "14:00",
				null, false); 																	
		wydarzenie5.setUzytkownik(uzytkownik3);
		wydarzenie5.setLokalizacja(lokalizacja1);
		wydarzenieDao.add(wydarzenie5);
		
		Zaproszenie zaproszenie5 = new Zaproszenie(false);
		zaproszenie5.setUzytkownik(uzytkownik1);
		zaproszenie5.setWydarzenie(wydarzenie5);
		zaproszenieDao.add(zaproszenie5);
		
		Zaproszenie zaproszenie6 = new Zaproszenie(true);
		zaproszenie6.setUzytkownik(uzytkownik2);
		zaproszenie6.setWydarzenie(wydarzenie5);
		zaproszenieDao.add(zaproszenie6);
		
	}
}
