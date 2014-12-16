package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.model.List_Custom_ListaWydarzen;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class WydarzeniaLista extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wydarzenia_lista);

		ListView rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
		String[] przykladowe_dane = {"Wlaz³ kotek", "na p³otek", "i mruga.", "piêkna to", "piosneczka nied³uga.", "Wlaz³ kurek", "na murek", "i pieje", "niech siê nikt", "z tych piosnek", "nie œmieje."};

		List_Custom_ListaWydarzen adapter_listy = new List_Custom_ListaWydarzen(przykladowe_dane, this);
		rozbudowana_lista.setAdapter(adapter_listy);
	}
}
