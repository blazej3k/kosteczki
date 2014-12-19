package Dziecioly.zkimnabasen.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Dziecioly.zkimnabasen.R;
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
import Dziecioly.zkimnabasen.fragment.TimePickerFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class NoweWydarzenie extends FragmentActivity implements
		OnDateSetListener, OnTimeSetListener,
		ChecboxListFragment.NoticeDialogListener,
		ListFragment.NoticeDialogListener {

	private Context context;
	private EditText nazwa;
	private EditText lokalizacja;
	private Button data;
	private Button godzinaRozpoczecia;
	private Button godzinaZakonczenia;
	private Button zaprosZnajomych;
	private Button zapisz;
	private Button btnMapa;
	private Button btnKategoria;
	private CheckBox czyOtwarte;

	private List<Uzytkownik> wszyscyZnajomi = new ArrayList<Uzytkownik>();
	private boolean[] wybraniZnajomi;
	private String wybranaKategoria;

	private WydarzenieDao wydarzenieDao = new WydarzenieDao();
	private UzytkownikDao uzytkownikDao = new UzytkownikDao();
	private ZaproszenieDao zaproszenieDao = new ZaproszenieDao();

	private ChecboxListFragment checkboxFrag;
	private ListFragment listFrag;
	public static final int FLAG_START_TIME = 0;
	public static final int FLAG_END_TIME = 1;
	private int flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.nowe_wydarzenie);

		nazwa = (EditText) findViewById(R.id.nazwa);
		lokalizacja = (EditText) findViewById(R.id.lokalizacja);
		data = (Button) findViewById(R.id.data);
		godzinaRozpoczecia = (Button) findViewById(R.id.godzinaRozpoczecia);
		godzinaZakonczenia = (Button) findViewById(R.id.godzinaZakonczenia);
		zaprosZnajomych = (Button) findViewById(R.id.zaprosZnajomych);
		zapisz = (Button) findViewById(R.id.zapisz);
		czyOtwarte = (CheckBox) findViewById(R.id.czyOtwarte);
		btnKategoria = (Button) findViewById(R.id.kategoria);
		btnMapa = (Button) findViewById(R.id.mapa);

		initBtnOnClickListeners();

		wszyscyZnajomi = pobierzZnajomych();
		wybraniZnajomi = new boolean[wszyscyZnajomi.size()];
		Arrays.fill(wybraniZnajomi, Boolean.FALSE);

	}

	private void zapisz() {
		Intent intent = new Intent(context, MojKalendarz.class);

		String w_nazwa = nazwa.getText().toString();
		String w_lokalizacja = lokalizacja.getText().toString();
		String w_data = data.getText().toString();
		String w_godzinaRozpoczecia = godzinaRozpoczecia.getText().toString();
		String w_godzinaZakonczenia = godzinaZakonczenia.getText().toString();
		boolean w_czyOtwarte = czyOtwarte.isChecked();

		Wydarzenie w = new Wydarzenie(w_nazwa, w_lokalizacja, w_data,
				w_godzinaRozpoczecia, w_godzinaZakonczenia, null, w_czyOtwarte);

		SharedPreferences pref = context.getSharedPreferences("MyPref", 0);

		String login = pref.getString("loggedIn", "null");
		Uzytkownik uzytkownik = uzytkownikDao
				.pobierzZalogowanegoUzytkownika(login);
		w.setUzytkownik(uzytkownik);

		wydarzenieDao.add(w);

		for (int i = 0; i < wybraniZnajomi.length; i++) {
			if (wybraniZnajomi[i] == true) {
				Uzytkownik u = wszyscyZnajomi.get(i);
				Zaproszenie z = new Zaproszenie(false);
				z.setUzytkownik(u);
				z.setWydarzenie(w);
				zaproszenieDao.add(z);
			}
		}

		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nowe_wydarzenie, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		String dateText = day + "-" + String.valueOf(month + 1) + "-" + year;
		data.setText(dateText);
	}

	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		String timeText = hour + ":" + minute;
		if (flag == FLAG_START_TIME) {
			godzinaRozpoczecia.setText(timeText);
		} else if (flag == FLAG_END_TIME) {
			godzinaZakonczenia.setText(timeText);
		}

	}

	private void initBtnOnClickListeners() {
		zapisz.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				zapisz();
			}
		});

		data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerFragment date = new DatePickerFragment();
				date.show(getSupportFragmentManager(), "Date Picker");
			}
		});

		godzinaRozpoczecia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = 0;
				TimePickerFragment time = new TimePickerFragment();
				time.show(getSupportFragmentManager(), "Time Picker");
			}
		});

		godzinaZakonczenia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = 1;
				TimePickerFragment time = new TimePickerFragment();
				time.show(getSupportFragmentManager(), "Time Picker");
			}
		});

		zaprosZnajomych.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
				checkboxFrag = new ChecboxListFragment("Wybierz znajomych",
						null, wybraniZnajomi);
				checkboxFrag.setItems(wszyscyZnajomi);
				checkboxFrag.show(getSupportFragmentManager(), "Checkbox list");
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
				Intent intent = new Intent(context, Mapa.class);
				intent.putExtra("kategoria", wybranaKategoria);
				startActivity(intent);
			}
		});

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		this.wybraniZnajomi = checkboxFrag.getCheckedItems();
		Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
	}

	public List<Uzytkownik> pobierzZnajomych() {
		List<Uzytkownik> lista = uzytkownikDao.list();
		return lista;
	}

	@Override
	public void onDialogClick(DialogFragment dialog) {
		wybranaKategoria = listFrag.getSelectedItem();
		btnKategoria.setText(wybranaKategoria);
	}
}
