package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Logowanie extends Activity {

	private Context context;
	private EditText login;
	private EditText haslo;
	private Button zaloguj;
	private Button utworzKonto;

	UzytkownikDao uzytkownikDao = new UzytkownikDao();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logowanie);
		context = getApplicationContext();

		login = (EditText) findViewById(R.id.login);
		haslo = (EditText) findViewById(R.id.haslo);
		zaloguj = (Button) findViewById(R.id.zaloguj);
		utworzKonto = (Button) findViewById(R.id.utworzKonto);

		initBtnOnClickListeners();

	}

	private void initBtnOnClickListeners() {
		zaloguj.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				zaloguj();
			}
		});

		utworzKonto.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				utworzKonto();
			}
		});
	}

	private void zaloguj() {
		String u_login = login.getText().toString();
		String u_haslo = haslo.getText().toString();

		if (!uzytkownikDao.zaloguj(u_login, u_haslo))
			Toast.makeText(context, "B³êdny login lub has³o", Toast.LENGTH_SHORT)
					.show();
		else {
			Intent intent = new Intent(context, WydarzeniaLista.class);
			startActivity(intent);

			int id = uzytkownikDao.pobierzZalogowanegoUzytkownika(u_login)
					.getId();
			SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
			Editor editor = pref.edit();
			editor.clear();
			editor.putString("loggedIn", u_login);
			editor.putInt("id", id);
			editor.commit();

		}
	}

	private void utworzKonto() {
		Intent intent = new Intent(context, Rejestracja.class);
		startActivity(intent);
	}

}
