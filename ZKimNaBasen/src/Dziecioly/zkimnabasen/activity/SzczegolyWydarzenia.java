package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.List_Custom_ListaWydarzen;
import Dziecioly.zkimnabasen.baza.model.RowBeanListaWyd;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SzczegolyWydarzenia extends Activity {

	static final String DEBUG_TAG = "LOG";
	
	private TextView tv_tworca;
	private TextView tv_nazwa;
	private TextView tv_data;
	private TextView tv_od;
	private TextView tv_do;
	private TextView tv_opis; 
	private ListView rozbudowana_lista;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.szczegoly_wydarzenia);
		context = getApplicationContext();
		
		Intent intent = getIntent();
		int id = intent.getIntExtra("id_wydarzenia", -1);
		
		tv_tworca = (TextView) findViewById(R.id.tv_tworca);
		tv_nazwa = (TextView) findViewById(R.id.tv_nazwa_wydarzenia);
		tv_data = (TextView) findViewById(R.id.tv_data);
		tv_od = (TextView) findViewById(R.id.tv_od);
		tv_do = (TextView) findViewById(R.id.tv_do);
		tv_opis = (TextView) findViewById(R.id.tv_opis);
		rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
		
		czytajWydarzenie(id);
	}
	
	private void czytajWydarzenie(int id) {
		id++; // bo id z listy jest o 1 mniejsze niz bazy
		
		WydarzenieDao wydDao = new WydarzenieDao();
		Wydarzenie wydarzenie = wydDao.find(id); // pobierz wydarzenie, select do bazy
		
		Log.d(DEBUG_TAG, "czytajWydarzenie2 id="+id);
		
		if (wydarzenie.equals(null))	// czy wydarzenie sie pobralo
			Log.d(DatabaseManager.DEBUG_TAG, "Brak wydarzenia");
		else { // jesli tak to dzialaj
			tv_tworca.setText(wydarzenie.getUzytkownik().getNazwa());	// wprowadzenie do wszystkich TextView info o wydarzeniu
			tv_nazwa.setText(wydarzenie.getNazwa());
			tv_data.setText(wydarzenie.getData());
			tv_od.setText(wydarzenie.getGodz_od());
			tv_do.setText(wydarzenie.getGodz_do());
			tv_opis.setText(wydarzenie.getOpis());
			
			List<Zaproszenie> zaproszeniaL = wydarzenie.getZaproszenia();
			Log.d(DEBUG_TAG, "Iloœc zaproszeñ: "+ zaproszeniaL.size());
			
			
			if(zaproszeniaL.isEmpty())
				Toast.makeText(this, "Lista zaproszeñ jest pusta w ciul!", Toast.LENGTH_LONG).show();

			else {
				RowBeanListaWyd[] ZaproszeniaRB = new RowBeanListaWyd[zaproszeniaL.size()];

				for (int i=0; i < ZaproszeniaRB.length; i++) {
					ZaproszeniaRB[i] = new RowBeanListaWyd();
					ZaproszeniaRB[i].setTekst(zaproszeniaL.get(i).getUzytkownik().getNazwa());
					ZaproszeniaRB[i].setIcon(R.drawable.wiewiorka);
				}

				rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
				List_Custom_ListaWydarzen adapter_listy = new List_Custom_ListaWydarzen(this, R.layout.view_row_item_lista_wydarzen, ZaproszeniaRB);
				rozbudowana_lista.setAdapter(adapter_listy);
			}
		}
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


