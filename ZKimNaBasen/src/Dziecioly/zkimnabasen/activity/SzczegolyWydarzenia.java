package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.List_Custom_ListaWydarzen;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class SzczegolyWydarzenia extends Activity {

	static final String DEBUG_TAG = "LOG";
	
	private TextView tv_tworca;
	private TextView tv_nazwa;
	private TextView tv_od;
	private TextView tv_do;
	private ListView rozbudowana_lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.szczegoly_wydarzenia);
		
		Intent intent = getIntent();
		int id = intent.getIntExtra("id_wydarzenia", -1);
		
		tv_tworca = (TextView) findViewById(R.id.tv_tworca);
		tv_nazwa = (TextView) findViewById(R.id.tv_nazwa_wydarzenia);
		tv_od = (TextView) findViewById(R.id.tv_od);
		tv_do = (TextView) findViewById(R.id.tv_do);
		rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
		
		czytajWydarzenie(id);
	}
	
	private void czytajWydarzenie(int id) {
		id++;
		
		WydarzenieDao wydDao = new WydarzenieDao();
		Wydarzenie wydarzenie = wydDao.find(id);
		
		Log.d(DEBUG_TAG, "czytajWydarzenie2 id="+id);
		
		String [] sWydarzenie = null;

		if (wydarzenie.equals(null))
			Log.d(DatabaseManager.DEBUG_TAG, "Brak wydarzenia");
		else {
//			tv_tworca.setText(wydarzenie.getUzytkownik().getNazwa());
			tv_nazwa.setText(wydarzenie.getNazwa());
			tv_od.setText(wydarzenie.getGodz_od());
			tv_do.setText(wydarzenie.getGodz_do());
			
		}

		//String[] przykladowe_dane = {"Wlaz³ kotek", "na p³otek", "i mruga.", "piêkna to", "piosneczka nied³uga.", "Wlaz³ kurek", "na murek", "i pieje", "niech siê nikt", "z tych piosnek", "nie œmieje."};
//		rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
//		List_Custom_ListaWydarzen adapter_listy = new List_Custom_ListaWydarzen(sWydarzenie, this);
//		rozbudowana_lista.setAdapter(adapter_listy);
	}

}
