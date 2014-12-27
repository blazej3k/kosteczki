package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import Dziecioly.zkimnabasen.baza.model.List_Custom_ListaWydarzen;
import Dziecioly.zkimnabasen.baza.model.RowBeanListaWyd;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;
import Dziecioly.zkimnabasen.fragment.YesNoFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SzczegolyWydarzenia extends FragmentActivity {

	static final String DEBUG_TAG = "LOG";

	private TextView tv_tworca;
	private TextView tv_nazwa;
	private TextView tv_data;
	private TextView tv_od;
	private TextView tv_do;
	private TextView tv_opis;
	private TextView tv_lokalizacja;
	private TextView tv_zaproszeni;
	private ListView rozbudowana_lista;
	private Button btnEdytuj;
	private Button btnUsun;
	private Context context;
	private Button btn_mapa;
	private Button btn_veturilo;

	WydarzenieDao wydDao = new WydarzenieDao();
	ZaproszenieDao zaproszenieDao = new ZaproszenieDao();
	UzytkownikDao uzytkownikDao = new UzytkownikDao();

	private int tryb;
	private int id;
	private int zalogowany;
	private Wydarzenie wydarzenie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.szczegoly_wydarzenia);
		context = getApplicationContext();

		Intent intent = getIntent();
		id = intent.getIntExtra("id_wydarzenia", -1);
		tryb = intent.getIntExtra("tryb", 0);

		zalogowany = General.loggedUser(context);

		tv_tworca = (TextView) findViewById(R.id.tv_tworca);
		tv_nazwa = (TextView) findViewById(R.id.tv_nazwa_wydarzenia);
		tv_data = (TextView) findViewById(R.id.tv_data);
		tv_od = (TextView) findViewById(R.id.tv_od);
		tv_do = (TextView) findViewById(R.id.tv_do);
		tv_opis = (TextView) findViewById(R.id.tv_opis);
		tv_zaproszeni = (TextView) findViewById(R.id.tv_zaproszeni);
		tv_lokalizacja = (TextView) findViewById(R.id.tv_lokalizacja);
		rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
		btn_mapa = (Button) findViewById(R.id.btn_mapa);
		btn_veturilo = (Button) findViewById(R.id.btn_veturilo);

		btnEdytuj = (Button) findViewById(R.id.btn_edytuj);
		btnUsun = (Button) findViewById(R.id.btn_usun);

		pokazButtony(tryb);
		initOnBtnClickListeners();

		wydarzenie = wydDao.find(id);
		czytajWydarzenie(wydarzenie);
	}

	private void pokazButtony(int tryb) {
		// moje wydarzenie (pokaz edytuj i usun)
		if (tryb == 0) {
			btnUsun.setEnabled(true);
			btnUsun.setVisibility(View.VISIBLE);

			btnEdytuj.setText("Edytuj");
		}
		// pokaz dolacz/rezygnuj
		else {
			btnUsun.setEnabled(false);
			btnUsun.setVisibility(View.INVISIBLE);

			btnEdytuj.setText("Do³¹cz");
			if (tryb == 1)
				btnEdytuj.setText("Rezygnuj");
		}

	}

	private void czytajWydarzenie(Wydarzenie wydarzenie) {

		if (wydarzenie.equals(null)) // czy wydarzenie sie pobralo
			Log.d(DatabaseManager.DEBUG_TAG, "Brak wydarzenia");
		else { // jesli tak to dzialaj
			tv_tworca.setText(wydarzenie.getUzytkownik().getNazwa()); // wprowadzenie
			// do
			// wszystkich
			// TextView
			// info
			// o
			// wydarzeniu
			tv_nazwa.setText(wydarzenie.getNazwa());
			tv_data.setText(General.stringFromDate(wydarzenie.getData()));
			tv_od.setText(wydarzenie.getGodz_od());
			tv_do.setText(wydarzenie.getGodz_do());
			tv_opis.setText(wydarzenie.getOpis());
			if (wydarzenie.getLokalizacja() != null)
				tv_lokalizacja.setText(wydarzenie.getLokalizacja().getAdres());

			List<Zaproszenie> zaproszeniaL = wydarzenie.getZaproszenia();
			Log.d(DEBUG_TAG, "Iloœc zaproszeñ: " + zaproszeniaL.size());

			if (zaproszeniaL.isEmpty()) {
				if (wydarzenie.isOtwarte())
					tv_zaproszeni.setText("Nikt jeszcze nie do³¹czy³ do wydarzenia.");
				else 
					tv_zaproszeni.setText("Lista zaproszonych osób jest pusta");
			}

			else {
				RowBeanListaWyd[] ZaproszeniaRB = new RowBeanListaWyd[zaproszeniaL.size()];
				
				if (wydarzenie.isOtwarte())
					tv_zaproszeni.setText("Osoby bior¹ce udzia³ w wydarzeniu:");

				for (int i = 0; i < ZaproszeniaRB.length; i++) {
					ZaproszeniaRB[i] = new RowBeanListaWyd();
					ZaproszeniaRB[i].setTekst(zaproszeniaL.get(i).getUzytkownik().getNazwa());
					
					if (zaproszeniaL.get(i).isWezmie_udzial())
						ZaproszeniaRB[i].setIcon(R.drawable.zielony);
				}

				rozbudowana_lista = (ListView) findViewById(R.id.lv_prostalista);
				List_Custom_ListaWydarzen adapter_listy = new List_Custom_ListaWydarzen(
						this, R.layout.view_row_item_lista_wydarzen,
						ZaproszeniaRB);
				rozbudowana_lista.setAdapter(adapter_listy);
			}
		}
	}

	private void initOnBtnClickListeners() {
		btnEdytuj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (tryb) {
				case 0:
					Intent intent = new Intent(context, NoweWydarzenie.class);
					intent.putExtra("id", id);
					startActivity(intent);
					break;
				case 1:
					rezygnuj();
					break;
				case 2:
					akceptujZaproszenie();
					break;
				case 3:
					dolaczDoWydarzeniaOtwartego();
					break;
				default:
					break;
				}
			}
		});

		btnUsun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				YesNoFragment frag = new YesNoFragment(id);
				frag.show(getSupportFragmentManager(), "YesNo");

			}
		});

		btn_mapa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (wydarzenie.getLokalizacja() == null)
					Toast.makeText(context, "Lokalizacja nie jest okreœlona",
							Toast.LENGTH_SHORT).show();
				else {
					Intent intent = new Intent(context,
							SzczegolyWydarzeniaMapa.class);
					intent.putExtra("lat", wydarzenie.getLokalizacja().getLat());
					intent.putExtra("lon", wydarzenie.getLokalizacja().getLon());
					intent.putExtra("adres", wydarzenie.getLokalizacja().getAdres());
					startActivity(intent);
				}
			}
		});

		btn_veturilo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (wydarzenie.getLokalizacja() == null)
					Toast.makeText(context, "Lokalizacja nie jest okreœlona",
							Toast.LENGTH_SHORT).show();
				else {
					Intent intent = new Intent(context, Veturillo.class);
					intent.putExtra("lat", wydarzenie.getLokalizacja().getLat());
					intent.putExtra("lon", wydarzenie.getLokalizacja().getLon());
					intent.putExtra("adres", wydarzenie.getLokalizacja()
							.getAdres());
					startActivity(intent);
				}
			}
		});

	}

	private void rezygnuj() {

		Zaproszenie zaproszenie = zaproszenieDao
				.pobierzZaproszenieUseraNawydarzenie(zalogowany, id);

		if (wydarzenie.isOtwarte()) {
			zaproszenieDao.remove(zaproszenie);
		}

		else {
			zaproszenie.setWezmie_udzial(false);
			zaproszenieDao.update(zaproszenie);
		}

		Intent intent = new Intent(context, WydarzeniaLista.class);
		startActivity(intent);

	}

	private void akceptujZaproszenie() {
		Zaproszenie zaproszenie = zaproszenieDao
				.pobierzZaproszenieUseraNawydarzenie(zalogowany, id);
		zaproszenie.setWezmie_udzial(true);
		zaproszenieDao.update(zaproszenie);

		Intent intent = new Intent(context, WydarzeniaLista.class);
		startActivity(intent);
	}

	private void dolaczDoWydarzeniaOtwartego() {
		Zaproszenie zaproszenie = new Zaproszenie(true);
		zaproszenie.setWydarzenie(wydarzenie);
		String login = General.loggedUserLogin(context);
		Uzytkownik uzytkownik = uzytkownikDao
				.pobierzZalogowanegoUzytkownika(login);
		zaproszenie.setUzytkownik(uzytkownik);
		zaproszenieDao.add(zaproszenie);
		Intent intent = new Intent(context, WydarzeniaLista.class);
		startActivity(intent);
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
		} else if (id == R.id.action_clear) {
			General.clearData(context);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        Intent intent = new Intent(context, WydarzeniaLista.class);
	        startActivity(intent);
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
}
