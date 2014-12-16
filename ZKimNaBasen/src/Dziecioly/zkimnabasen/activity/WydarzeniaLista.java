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
		String[] przykladowe_dane = {"Wlaz� kotek", "na p�otek", "i mruga.", "pi�kna to", "piosneczka nied�uga.", "Wlaz� kurek", "na murek", "i pieje", "niech si� nikt", "z tych piosnek", "nie �mieje."};

		List_Custom_ListaWydarzen adapter_listy = new List_Custom_ListaWydarzen(przykladowe_dane, this);
		rozbudowana_lista.setAdapter(adapter_listy);
	}
}
