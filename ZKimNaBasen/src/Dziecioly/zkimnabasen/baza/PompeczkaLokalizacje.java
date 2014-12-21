package Dziecioly.zkimnabasen.baza;

import Dziecioly.zkimnabasen.baza.dao.LokalizacjaDao;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;

public class PompeczkaLokalizacje {

	LokalizacjaDao lokalizacjaDao = new LokalizacjaDao();
	
	public PompeczkaLokalizacje ()
	{
		Lokalizacja lokalizacja = new Lokalizacja(52.261315, 20.977423, "Ludwika Rydygiera 8", null, true, "Fitness");
		lokalizacjaDao.add(lokalizacja);
		
		Lokalizacja lokalizacja2 = new Lokalizacja(52.244284, 20.984538, "Dzielna 52", "Fitness Club", true, "Fitness");
		lokalizacjaDao.add(lokalizacja2);
		
		Lokalizacja lokalizacja3 = new Lokalizacja(52.244179, 20.947288, "Deotymy 39", "Tenis club", true, "Kort tenisowy");
		lokalizacjaDao.add(lokalizacja3);
		
		Lokalizacja lokalizacja4 = new Lokalizacja(52.242182, 20.997928, "Orla 13", "basen", true, "P³ywalnia");
		lokalizacjaDao.add(lokalizacja4);
		
		Lokalizacja lokalizacja5 = new Lokalizacja(52.243969, 20.950721, "Sitnika 2", null, true, "P³ywalnia");
		lokalizacjaDao.add(lokalizacja5);

	}
	
}
