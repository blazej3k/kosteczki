package Dziecioly.zkimnabasen.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.LokalizacjaDao;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;
import Dziecioly.zkimnabasen.fragment.ChecboxListFragment;
import Dziecioly.zkimnabasen.fragment.DatePickerFragment;
import Dziecioly.zkimnabasen.fragment.ListFragment;
import Dziecioly.zkimnabasen.fragment.TextFragment;
import Dziecioly.zkimnabasen.fragment.TimePickerFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.j256.ormlite.dao.ForeignCollection;

public class NoweWydarzenie extends FragmentActivity implements
		OnDateSetListener, OnTimeSetListener,
		ChecboxListFragment.NoticeDialogListener,
		ListFragment.NoticeDialogListener, TextFragment.NoticeDialogListener {

	private Context context;
	private EditText nazwa;
	private Button data;
	private Button godzinaRozpoczecia;
	private Button godzinaZakonczenia;
	private Button zaprosZnajomych;
	private Button zapisz;
	private Button btnMapa;
	private Button btnKategoria;
	private EditText opis;
	private CheckBox czyOtwarte;
	private Button btnVeturillo;

	private List<Uzytkownik> wszyscyZnajomi = new ArrayList<Uzytkownik>();
	// private boolean[] wybraniZnajomi;
	private String wybranaKategoria = "";
	public static Lokalizacja wybranaLokalizacja;
	private Lokalizacja lokalizacja = null;

	private WydarzenieDao wydarzenieDao = new WydarzenieDao();
	private UzytkownikDao uzytkownikDao = new UzytkownikDao();
	private ZaproszenieDao zaproszenieDao = new ZaproszenieDao();
	private LokalizacjaDao lokalizacjaDao = new LokalizacjaDao();

	private ChecboxListFragment checkboxFrag;
	private TextFragment textFrag;
	private ListFragment listFrag;
	public static final int FLAG_START_TIME = 0;
	public static final int FLAG_END_TIME = 1;
	private int flag;

	private int id_wydarzenia = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.nowe_wydarzenie);

		nazwa = (EditText) findViewById(R.id.nazwa);
		data = (Button) findViewById(R.id.data);
		godzinaRozpoczecia = (Button) findViewById(R.id.godzinaRozpoczecia);
		godzinaZakonczenia = (Button) findViewById(R.id.godzinaZakonczenia);
		zaprosZnajomych = (Button) findViewById(R.id.zaprosZnajomych);
		zapisz = (Button) findViewById(R.id.zapisz);
		opis = (EditText) findViewById(R.id.opis);
		czyOtwarte = (CheckBox) findViewById(R.id.czyOtwarte);
		btnKategoria = (Button) findViewById(R.id.kategoria);
		btnMapa = (Button) findViewById(R.id.mapa);
		btnVeturillo = (Button) findViewById(R.id.btnVeturillo);

		initBtnOnClickListeners();

		wszyscyZnajomi = pobierzZnajomych();
		wyczyscSharedPref();

		wybranaLokalizacja = null;

		id_wydarzenia = getIntent().getIntExtra("id", 0);
		if (id_wydarzenia != 0)
			wpiszDane();

	}

	private void wyczyscSharedPref() {
		boolean[] checkedItems = new boolean[wszyscyZnajomi.size()];
		Arrays.fill(checkedItems, Boolean.FALSE);
		zapiszWybranych(checkedItems);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (wybranaLokalizacja != null) {
			lokalizacja = wybranaLokalizacja;
			String adres = wybranaLokalizacja.getAdres();
			if (adres != null) {
				btnMapa.setText(adres);
			} else {
				btnMapa.setText("");
			}
		}
		wybranaLokalizacja = null;
	}

	private void zapisz() {
		String w_nazwa = nazwa.getText().toString();
		String w_data = data.getText().toString();
		String w_godzinaRozpoczecia = godzinaRozpoczecia.getText().toString();
		String w_godzinaZakonczenia = godzinaZakonczenia.getText().toString();
		String w_opis = opis.getText().toString();

		if (w_godzinaRozpoczecia.equals("Godzina rozpoczêcia"))
			w_godzinaRozpoczecia = null;
		if (w_godzinaZakonczenia.equals("Godzina zakoñczenia"))
			w_godzinaZakonczenia = null;

		boolean w_czyOtwarte = czyOtwarte.isChecked();

		Wydarzenie wydarzenie = new Wydarzenie(w_nazwa,
				General.dateFromString(w_data), w_godzinaRozpoczecia,
				w_godzinaZakonczenia, w_opis, w_czyOtwarte);

		int id = General.loggedUser(context);
		Uzytkownik uzytkownik = uzytkownikDao.find(id);
		wydarzenie.setUzytkownik(uzytkownik);

		if (lokalizacja != null) {
			Lokalizacja lokalizacjaDb = lokalizacjaDao
					.czyIstniejeJesliNieToDodaj(lokalizacja);
			wydarzenie.setLokalizacja(lokalizacjaDb);
		}

		if (!w_czyOtwarte) {
			ForeignCollection<Zaproszenie> zaproszenia = wydarzenieDao
					.getEmptyCollection();
			boolean[] wybraniZnajomi = pobierzWybranych();
			for (int i = 0; i < wybraniZnajomi.length; i++) {
				if (wybraniZnajomi[i] == true) {
					Uzytkownik u = wszyscyZnajomi.get(i);
					Zaproszenie z = new Zaproszenie(false);
					z.setUzytkownik(u);
					z.setWydarzenie(wydarzenie);
					zaproszenia.add(z);
				}
			}
			wydarzenie.setZaproszenia(zaproszenia);
		}
		int id_w;
		Log.d(DatabaseManager.DEBUG_TAG,
				"ID WYD " + Integer.toString(id_wydarzenia));
		// edycja -> najpierw usun, potem normalnie zapisz
		if (id_wydarzenia != 0)
			usun(id_wydarzenia);

		id_w = wydarzenieDao.add(wydarzenie).getId();
		Intent intent = new Intent(context, SzczegolyWydarzenia.class);
		Log.d(DatabaseManager.DEBUG_TAG, "ID WYD 2" + Integer.toString(id_w));
		intent.putExtra("id_wydarzenia", id_w);
		startActivity(intent);
	}

	private void usun(int id_wyd) {
		Wydarzenie w = wydarzenieDao.find(id_wyd);
		Lokalizacja lok = w.getLokalizacja();
		if (lok != null && !lok.isPubliczna())
			lokalizacjaDao.remove(lok);
		List<Zaproszenie> zaproszenia = w.getZaproszenia();
		if (zaproszenia != null)
			for (Zaproszenie z : zaproszenia)
				zaproszenieDao.remove(z);
		wydarzenieDao.remove(w);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		String dday = Integer.toString(day);
		String mmonth = String.valueOf(month + 1);
		if (day < 10)
			dday = "0" + dday;
		if (month < 9)
			mmonth = "0" + mmonth;
		String dateText = dday + "." + mmonth + "." + year;
		data.setText(dateText);
	}

	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		String hhour = Integer.toString(hour);
		String mminute = Integer.toString(minute);
		if (hour < 10)
			hhour = "0" + hhour;
		if (minute < 10)
			mminute = "0" + mminute;

		String timeText = hhour + ":" + mminute;
		if (flag == FLAG_START_TIME) {
			godzinaRozpoczecia.setText(timeText);
		} else if (flag == FLAG_END_TIME) {
			godzinaZakonczenia.setText(timeText);
		}

	}

	private void initBtnOnClickListeners() {
		zapisz.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String nazwa_w = nazwa.getText().toString();
				String data_w = data.getText().toString();
				if (nazwa_w == null || nazwa_w.equals(""))
					Toast.makeText(context, "Podaj nazwê wydarzenia",
							Toast.LENGTH_SHORT).show();
				else if (data_w.equals("Data")) {
					Toast.makeText(context, "Podaj datê wydarzenia",
							Toast.LENGTH_SHORT).show();
				}
				if (General.dateFromString(data_w).before(
						General.dateFromString(General
								.stringFromDate(new Date())))) {
					Toast.makeText(context, "Podana data ju¿ minê³a",
							Toast.LENGTH_SHORT).show();
				} else {
					if (lokalizacja != null
							&& lokalizacja.isLokalizacjaUzytkownika()) {
						textFrag = new TextFragment(btnMapa.getText()
								.toString());
						textFrag.show(getSupportFragmentManager(), "Text");
					} else {
						zapisz();
					}
				}

			}
		});

		data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerFragment date = new DatePickerFragment(data.getText()
						.toString());
				date.show(getSupportFragmentManager(), "Date Picker");
			}
		});

		godzinaRozpoczecia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = 0;
				TimePickerFragment time = new TimePickerFragment(
						godzinaRozpoczecia.getText().toString());
				time.show(getSupportFragmentManager(), "Time Picker");
			}
		});

		godzinaZakonczenia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = 1;
				TimePickerFragment time = new TimePickerFragment(
						godzinaZakonczenia.getText().toString());
				time.show(getSupportFragmentManager(), "Time Picker");
			}
		});

		zaprosZnajomych.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (czyOtwarte.isChecked())
					Toast.makeText(context, "Zaznaczono wydarzenie otwarte",
							Toast.LENGTH_SHORT).show();
				else {

					boolean[] checkedItems = pobierzWybranych();
					checkboxFrag = new ChecboxListFragment("Wybierz znajomych",
							null, checkedItems);
					checkboxFrag.setItems(wszyscyZnajomi);
					checkboxFrag.show(getSupportFragmentManager(),
							"Checkbox list");
				}
			}
		});

		btnKategoria.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listFrag = new ListFragment("Wybierz kategoriê",
						Lokalizacja.kategorie);
				listFrag.show(getSupportFragmentManager(), "List");
			}
		});

		btnMapa.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (btnKategoria.getText().toString().equals("Kategoria"))
					Toast.makeText(context, "Wybierz kategoriê",
							Toast.LENGTH_SHORT).show();
				else
					wczytajMape();
			}
		});

		btnVeturillo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (lokalizacja == null)
					Toast.makeText(context, "Wybierz lokalizacjê",
							Toast.LENGTH_SHORT).show();
				else {
					Intent intent = new Intent(context, Veturillo.class);
					intent.putExtra("lat", lokalizacja.getLat());
					intent.putExtra("lon", lokalizacja.getLon());
					intent.putExtra("adres", lokalizacja.getAdres());
					startActivity(intent);
				}
			}
		});

		czyOtwarte.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					zaprosZnajomych.setText("Zaproszeni: wszyscy");
				else {
					boolean[] wybraniZnajomi = pobierzWybranych();
					int licz = policzWybranych(wybraniZnajomi);
					if (licz != 0)
						zaprosZnajomych.setText("Zaproszonych: " + licz);
					else
						zaprosZnajomych.setText("Zaproœ znajomych");

				}

			}
		});

	}

	private void zapiszWybranych(boolean[] checkedItems) {
		SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
		Editor editor = pref.edit();
		for (int i = 0; i < wszyscyZnajomi.size(); i++) {
			String key = "z" + i;
			editor.putBoolean(key, checkedItems[i]);

		}
		editor.commit();
	}

	private boolean[] pobierzWybranych() {
		SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
		boolean[] checkedItems = new boolean[wszyscyZnajomi.size()];
		for (int i = 0; i < wszyscyZnajomi.size(); i++) {
			String key = "z" + i;
			checkedItems[i] = pref.getBoolean(key, false);

		}
		return checkedItems;

	}

	private void wczytajMape() {
		Intent intent = new Intent(context, Mapa.class);
		intent.putExtra("kategoria", wybranaKategoria);

		String wpisanyAdres = btnMapa.getText().toString();

		if (wpisanyAdres != null && !wpisanyAdres.equals("")
				&& lokalizacja != null && !wpisanyAdres.equals("Lokalizacja")) {
			intent.putExtra("lok", true);
			intent.putExtra("lat", lokalizacja.getLat());
			intent.putExtra("lon", lokalizacja.getLon());
			intent.putExtra("adres", lokalizacja.getAdres());
		} else {
			intent.putExtra("lok", false);
		}

		startActivity(intent);
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {

		boolean[] checkedItems = checkboxFrag.getCheckedItems();
		zapiszWybranych(checkedItems);
		zaprosZnajomych.setText("Zaproszonych: "
				+ policzWybranych(checkedItems));

	}

	private int policzWybranych(boolean[] checkedItems) {
		int licznik = 0;
		for (boolean b : checkedItems)
			if (b)
				licznik++;
		return licznik;
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
	}

	@Override
	public void onTextDialogPositiveClick(DialogFragment dialog) {
		String opisLokalizacji = textFrag.getOpis();
		if (opisLokalizacji != "")
			lokalizacja.setOpis(opisLokalizacji);
		lokalizacja.setPubliczna(true);
		zapisz();

	}

	@Override
	public void onTextDialogNeutralClick(DialogFragment dialog) {
		String opisLokalizacji = textFrag.getOpis();
		Toast.makeText(context, opisLokalizacji, Toast.LENGTH_SHORT).show();
		if (opisLokalizacji != "")
			lokalizacja.setOpis(opisLokalizacji);
		zapisz();
	}

	@Override
	public void onTextDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub

	}

	public List<Uzytkownik> pobierzZnajomych() {
		int id = General.loggedUser(context);
		List<Uzytkownik> lista = uzytkownikDao.pobierzZnajomych(id);
		return lista;
	}

	@Override
	public void onDialogClick(DialogFragment dialog) {
		wybranaKategoria = listFrag.getSelectedItem();
		btnKategoria.setText(wybranaKategoria);
		lokalizacja = null;
		btnMapa.setText("Lokalizacja");
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

	private void wpiszDane() {
		Wydarzenie w = wydarzenieDao.find(id_wydarzenia);
		String w_nazwa = w.getNazwa();
		String w_data = General.stringFromDate(w.getData());
		String w_godzOd = w.getGodz_od();
		String w_godzDo = w.getGodz_do();
		String w_opis = w.getOpis();
		boolean w_otwarte = w.isOtwarte();

		nazwa.setText(w_nazwa);
		data.setText(w_data);

		if (w_godzOd != null)
			godzinaRozpoczecia.setText(w_godzOd);

		if (w_godzDo != null)
			godzinaRozpoczecia.setText(w_godzDo);

		if (w_opis != null)
			opis.setText(w_opis);

		czyOtwarte.setChecked(w_otwarte);

		Lokalizacja lok = w.getLokalizacja();
		if (lok != null) {
			lokalizacja = lok;
			btnKategoria.setText(lok.getKategoria());
			btnMapa.setText(lok.getAdres());
			wybranaKategoria = lok.getKategoria();
		}

		List<Zaproszenie> list = w.getZaproszenia();
		if (list != null) {
			boolean[] wybraniZnajomi = new boolean[wszyscyZnajomi.size()];
			for (int i = 0; i < list.size(); i++) {
				int id_zaproszonego = list.get(i).getUzytkownik().getId();
				Integer j = index(id_zaproszonego);
				if (j != null)
					wybraniZnajomi[j] = true;
			}
			zapiszWybranych(wybraniZnajomi);
		}

		if (w_otwarte)
			zaprosZnajomych.setText("Zaproszeni: wszyscy");
		else if (list == null || list.size() == 0)
			zaprosZnajomych.setText("Zaproœ znajomych");
		else
			zaprosZnajomych.setText("Zaproszonych: " + list.size());

	}

	private Integer index(int id) {
		for (int i = 0; i < wszyscyZnajomi.size(); i++)
			if (wszyscyZnajomi.get(i).getId() == id) {
				Log.d(DatabaseManager.DEBUG_TAG,
						"B " + Integer.toString(wszyscyZnajomi.get(i).getId()));
				return i;
			}
		return null;

	}
}
