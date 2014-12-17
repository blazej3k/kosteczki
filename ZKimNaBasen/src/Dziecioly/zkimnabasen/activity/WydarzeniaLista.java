package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.PompeczkaWydarzenia;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.List_Custom_ListaWydarzen;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class WydarzeniaLista extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wydarzenia_lista);

		//new PompeczkaWydarzenia(0);

		WydarzenieDao wydDao = new WydarzenieDao();
		List<Wydarzenie> wydarzenia = wydDao.list();
		String [] sWydarzenia = null;

		if (wydarzenia.size() == 0)
			Log.d(DatabaseManager.DEBUG_TAG, "PUSTA LISTA");
		else {
			sWydarzenia = new String[wydarzenia.size()];

			for (int i=0; i < wydarzenia.size(); i++)
				sWydarzenia[i] = wydarzenia.get(i).getNazwa();

			for (Wydarzenie x: wydarzenia)
				Log.d(DatabaseManager.DEBUG_TAG, "ID: "+ x.getId());
		}


		//String[] przykladowe_dane = {"Wlaz³ kotek", "na p³otek", "i mruga.", "piêkna to", "piosneczka nied³uga.", "Wlaz³ kurek", "na murek", "i pieje", "niech siê nikt", "z tych piosnek", "nie œmieje."};
		ListView rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
		List_Custom_ListaWydarzen adapter_listy = new List_Custom_ListaWydarzen(sWydarzenia, this);
		rozbudowana_lista.setAdapter(adapter_listy);
	}
}
