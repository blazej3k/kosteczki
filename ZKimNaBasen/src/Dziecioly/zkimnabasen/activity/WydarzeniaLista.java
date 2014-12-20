package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.PompeczkaWydarzenia;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.List_Custom_ListaWydarzen;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class WydarzeniaLista extends Activity {

	static final String DEBUG_TAG = "LOG";

	private ListView rozbudowana_lista;
	
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

		//String[] przykladowe_dane = {"Wlaz� kotek", "na p�otek", "i mruga.", "pi�kna to", "piosneczka nied�uga.", "Wlaz� kurek", "na murek", "i pieje", "niech si� nikt", "z tych piosnek", "nie �mieje."};
		rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
		List_Custom_ListaWydarzen adapter_listy = new List_Custom_ListaWydarzen(sWydarzenia, this);
		rozbudowana_lista.setAdapter(adapter_listy);
		
		initOnItemClickListener();
	}
	
	private void initOnItemClickListener() {
		rozbudowana_lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d(DEBUG_TAG, "Listener");
				
				Intent startSzczegolowyWidok = new Intent(WydarzeniaLista.this, SzczegolyWydarzenia.class);
				startSzczegolowyWidok.putExtra("id_wydarzenia", position);
				Log.d(DEBUG_TAG, "PutExtra");
				startActivity(startSzczegolowyWidok);
			}
			
		});
	}
}
