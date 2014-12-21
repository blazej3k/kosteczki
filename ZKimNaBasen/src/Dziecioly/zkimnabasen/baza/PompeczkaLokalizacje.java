package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.baza.dao.LokalizacjaDao;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;

public class PompeczkaLokalizacje {

	LokalizacjaDao lokalizacjaDao = new LokalizacjaDao();

	public PompeczkaLokalizacje ()
	{
		Lokalizacja lokalizacja = new Lokalizacja(52.261315, 20.977423, "Ludwika Rydygiera 8", null, true, "Fitness", false);
		lokalizacjaDao.add(lokalizacja);

		Lokalizacja lokalizacja2 = new Lokalizacja(52.244284, 20.984538, "Dzielna 52", "Fitness Club", true, "Fitness", false);
		lokalizacjaDao.add(lokalizacja2);

		Lokalizacja lokalizacja3 = new Lokalizacja(52.244179, 20.947288, "Deotymy 39", "Tenis club", true, "Kort tenisowy", false);
		lokalizacjaDao.add(lokalizacja3);
	}

}