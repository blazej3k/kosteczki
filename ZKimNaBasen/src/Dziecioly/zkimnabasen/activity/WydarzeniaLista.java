package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.PompeczkaWydarzenia;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.List_Custom_ListaWydarzen;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class WydarzeniaLista extends Activity {

	static final String DEBUG_TAG = "LOG";

	private ListView rozbudowana_lista;
	
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wydarzenia_lista);
		context = getApplicationContext();

		new PompeczkaWydarzenia();

		WydarzenieDao wydDao = new WydarzenieDao();
		List<Wydarzenie> wydarzenia = wydDao.pobierzWydarzenia();
		String [] sWydarzenia = null;

		if (wydarzenia.size() == 0) {
			Log.d(DatabaseManager.DEBUG_TAG, "PUSTA LISTA");
			
			Toast.makeText(this, "Brak wydarzeñ", Toast.LENGTH_LONG).show();
		}
		
		else {
			sWydarzenia = new String[wydarzenia.size()];

			for (int i=0; i < wydarzenia.size(); i++)
				sWydarzenia[i] = wydarzenia.get(i).getNazwa();

			for (Wydarzenie x: wydarzenia)
				Log.d(DatabaseManager.DEBUG_TAG, "ID: "+ x.getId());
			
			//String[] przykladowe_dane = {"Wlaz³ kotek", "na p³otek", "i mruga.", "piêkna to", "piosneczka nied³uga.", "Wlaz³ kurek", "na murek", "i pieje", "niech siê nikt", "z tych piosnek", "nie œmieje."};
			rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
			List_Custom_ListaWydarzen adapter_listy = new List_Custom_ListaWydarzen(sWydarzenia, this);
			rozbudowana_lista.setAdapter(adapter_listy);
			
			initOnItemClickListener();
		}

		
		
	}
	
	private void initOnItemClickListener() {
		rozbudowana_lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d(DEBUG_TAG, "Listener");
				
				Intent startSzczegolowyWidok = new Intent(WydarzeniaLista.this, SzczegolyWydarzenia.class);
				startSzczegolowyWidok.putExtra("id_wydarzenia", position+1); //BO ID Z LISTY JEST O 1 MNIEJSZE NIZ Z BAZY
				Log.d(DEBUG_TAG, "PutExtra");
				startActivity(startSzczegolowyWidok);
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			General.clearSharedPrefs(context);
			return true;
		}
		else if(id == R.id.action_clear)
		{
			General.clearData(context);	
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
