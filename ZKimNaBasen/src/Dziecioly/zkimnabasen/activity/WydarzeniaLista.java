package Dziecioly.zkimnabasen.activity;

import java.util.ArrayList;
import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.List_Custom_ListaWydarzen;
import Dziecioly.zkimnabasen.baza.model.RowBeanListaWyd;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;
import Dziecioly.zkimnabasen.fragment.ChecboxListFragment;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class WydarzeniaLista extends FragmentActivity implements
ChecboxListFragment.NoticeDialogListener {

	static final String DEBUG_TAG = "LOG";

	private ListView rozbudowana_lista;
	private Button btn_nowe_wydarzenie;
	private Button btn_wyswietlanie;

	private Context context;

	private WydarzenieDao wydarzenieDao = new WydarzenieDao();
	private UzytkownikDao uzytkownikDao = new UzytkownikDao();

	private ChecboxListFragment frag;
	private CharSequence[] items = { "Moje", "Wezmê udzia³",
			"Jestem zaproszona, ale nie biorê udzia³u",
	"Otwarte, w których nie biorê udzia³u" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wydarzenia_lista);
		context = getApplicationContext();
		btn_nowe_wydarzenie = (Button) findViewById(R.id.btn_nowe_wydarzenie);
		btn_wyswietlanie = (Button) findViewById(R.id.btn_wyswietlanie);

		List<Wydarzenie> wydarzeniaL = wydarzenieDao.pobierzWydarzenia();
		zbudujListe(wydarzeniaL);

		initOnBtnClickListeners();
	}

	private void zbudujListe(List<Wydarzenie> wydarzeniaL) {
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

	private List<Wydarzenie> wybierzWydarzenia(List<Wydarzenie> list) {
		SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
		boolean moje = pref.getBoolean("0", true);
		boolean wezmeUdzial = pref.getBoolean("1", true);
		boolean jestemZaproszona = pref.getBoolean("2", true);
		boolean otwarte = pref.getBoolean("3", true);

		int zalogowany = General.loggedUser(context);

		List<Wydarzenie> subList = new ArrayList<Wydarzenie>();
		for (Wydarzenie wydarzenie : list) {
			Uzytkownik uzytkownik = wydarzenie.getUzytkownik();
			List<Zaproszenie> zaproszenia = wydarzenie.getZaproszenia();
			boolean czyOtwarte = wydarzenie.isOtwarte();
			if (moje) {
				Log.d(DatabaseManager.DEBUG_TAG, "Moje");
				if (uzytkownik.getId() == zalogowany) {
					wydarzenie.setTryb(0);
					subList.add(wydarzenie);

				}
			}
			if (wezmeUdzial) {
				Log.d(DatabaseManager.DEBUG_TAG, "wezme udzial");
				for (Zaproszenie zaproszenie : zaproszenia) {
					if (zaproszenie.getUzytkownik().getId() == zalogowany
							&& zaproszenie.isWezmie_udzial()) {
						wydarzenie.setTryb(2);
						subList.add(wydarzenie);

					}
				}
			}

			if (jestemZaproszona) {
				Log.d(DatabaseManager.DEBUG_TAG, "jestem zaproszona");
				for (Zaproszenie zaproszenie : zaproszenia) {
					if (zaproszenie.getUzytkownik().getId() == zalogowany
							&& !zaproszenie.isWezmie_udzial()) {
						wydarzenie.setTryb(3);
						subList.add(wydarzenie);
					}

				}
			}

			if (otwarte) {
				Log.d(DatabaseManager.DEBUG_TAG, "otwarte");
				if (czyOtwarte) {
					boolean istniejeZaproszenie = false;
					for (Zaproszenie zaproszenie : zaproszenia) {
						if (zaproszenie.getUzytkownik().getId() == zalogowany
								&& zaproszenie.getWydarzenie().getId() == wydarzenie
								.getId()) {
							istniejeZaproszenie = true;
						}

					}

					if (!istniejeZaproszenie) {
						wydarzenie.setTryb(3);
						subList.add(wydarzenie);

					}
				}

			}

		}
		return subList;
	}

	private void initOnItemClickListener() {
		rozbudowana_lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d(DEBUG_TAG, "Listener");

				Intent startSzczegolowyWidok = new Intent(WydarzeniaLista.this, SzczegolyWydarzenia.class);
				startSzczegolowyWidok.putExtra("id_wydarzenia", position+1);
				Log.d(DEBUG_TAG, "PutExtra");
				startActivity(startSzczegolowyWidok);
			}

		});
	}

	private void initOnBtnClickListeners() {
		btn_nowe_wydarzenie.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, NoweWydarzenie.class);
				startActivity(intent);
			}
		});

		btn_wyswietlanie.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean[] checkedItems = new boolean[4];
				SharedPreferences pref = context.getSharedPreferences("MyPref",	0);
				checkedItems[0] = pref.getBoolean("0", true);
				checkedItems[1] = pref.getBoolean("1", true);
				checkedItems[2] = pref.getBoolean("2", true);
				checkedItems[3] = pref.getBoolean("3", true);

				frag = new ChecboxListFragment("Co chcesz wyœwietlaæ?", items, checkedItems);
				frag.show(getSupportFragmentManager(), "tryb");
				
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
	public void onDialogPositiveClick(DialogFragment dialog) {
		boolean checkedItems[] = frag.getCheckedItems();
		SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
		Editor editor = pref.edit();
		editor.putBoolean("0", checkedItems[0]);
		editor.putBoolean("1", checkedItems[1]);
		editor.putBoolean("2", checkedItems[2]);
		editor.putBoolean("3", checkedItems[3]);
		editor.commit();

		List<Wydarzenie> list = wydarzenieDao.pobierzWydarzenia();
		List<Wydarzenie> subList = wybierzWydarzenia(list);
		
		zbudujListe(subList);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {	}

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
