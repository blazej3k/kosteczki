package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.picker.ChecboxListFragment;
import Dziecioly.zkimnabasen.picker.DatePickerFragment;
import Dziecioly.zkimnabasen.picker.TimePickerFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
		OnDateSetListener, OnTimeSetListener {

	private Context context;
	private EditText nazwa;
	private EditText lokalizacja;
	private Button data;
	private Button godzinaRozpoczecia;
	private Button godzinaZakonczenia;
	private Button zaprosZnajomych;
	private Button zapisz;
	private CheckBox czyOtwarte;

	TimePickerFragment timePickerFragment = new TimePickerFragment();

	WydarzenieDao wydarzenieDao = new WydarzenieDao();
	UzytkownikDao uzytkownikDao = new UzytkownikDao();

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

		initBtnOnClickListeners();

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

		Uzytkownik u = uzytkownikDao.list().get(0);
		w.setUzytkownik(u);

		wydarzenieDao.add(w);

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
				ChecboxListFragment frag = new ChecboxListFragment();
				frag.show(getSupportFragmentManager(), "Checkbox list");
			}
		});
	}
}
