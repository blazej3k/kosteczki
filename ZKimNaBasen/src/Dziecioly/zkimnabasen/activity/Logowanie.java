package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Logowanie extends ActionBarActivity {

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
			Toast.makeText(context, "B³êdny login lub has³o", Toast.LENGTH_LONG)
					.show();
		else {
			Intent intent = new Intent(context, MojKalendarz.class);
			startActivity(intent);
			
			 SharedPreferences pref = context.getSharedPreferences("MyPref", 0); 
			 Editor editor = pref.edit();
			 editor.clear();
			 editor.putString("loggedIn", u_login);
			 editor.commit();
			 
			 
		}
	}

	private void utworzKonto() {
		Intent intent = new Intent(context, Rejestracja.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.szczegoly_wydarzenia, menu);
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
}
