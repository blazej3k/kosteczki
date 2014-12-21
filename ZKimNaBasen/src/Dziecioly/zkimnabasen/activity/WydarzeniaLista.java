package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.PompeczkaWydarzenia;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.List_Custom_ListaWydarzen;
import Dziecioly.zkimnabasen.baza.model.RowBeanListaWyd;
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
		List<Wydarzenie> wydarzeniaL = wydDao.list();
		RowBeanListaWyd[] WydarzeniaRB = null;

		if (wydarzeniaL.size() == 0) {
			Log.d(DatabaseManager.DEBUG_TAG, "PUSTA LISTA");
			
			Toast.makeText(this, "Lista wydarzeñ jest pusta w ciul!", Toast.LENGTH_LONG).show();
		}
		
		else {
			WydarzeniaRB = new RowBeanListaWyd[wydarzeniaL.size()];
			
			for (int i=0; i < wydarzeniaL.size(); i++) {
				WydarzeniaRB[i] = new RowBeanListaWyd();
				WydarzeniaRB[i].setTekst(wydarzeniaL.get(i).getNazwa());
				WydarzeniaRB[i].setIcon(R.drawable.niebieski);
			}

			for (Wydarzenie x: wydarzeniaL)
				Log.d(DatabaseManager.DEBUG_TAG, "ID: "+ x.getId());
			
			//String[] przykladowe_dane = {"Wlaz³ kotek", "na p³otek", "i mruga.", "piêkna to", "piosneczka nied³uga.", "Wlaz³ kurek", "na murek", "i pieje", "niech siê nikt", "z tych piosnek", "nie œmieje."};
			rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
			List_Custom_ListaWydarzen adapter_listy = new List_Custom_ListaWydarzen(this, R.layout.view_row_item_lista_wydarzen, WydarzeniaRB);

			rozbudowana_lista.setAdapter(adapter_listy);
		}

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
