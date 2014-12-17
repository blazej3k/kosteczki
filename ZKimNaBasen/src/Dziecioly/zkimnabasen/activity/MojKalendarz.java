package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
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

public class MojKalendarz extends ActionBarActivity {

	Context context;
	private Button btnNoweWydarzenie;
	private Button btnListaWydarzen;
	private Button btnLogowanie;

	UzytkownikDao uzytkownikDao = new UzytkownikDao();
	ZaproszenieDao zaproszenieDao = new ZaproszenieDao();
	WydarzenieDao wydarzenieDao = new WydarzenieDao();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		DatabaseManager.init(this);

		SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
		
		if (!pref.contains("loggedIn")) {
			Intent intent = new Intent(context, Logowanie.class);
			startActivity(intent);
		} else {
			setContentView(R.layout.moj_kalendarz);

			btnNoweWydarzenie = (Button) findViewById(R.id.btnNoweWydarzenie);
			btnListaWydarzen = (Button) findViewById(R.id.btnListaWydarzen);
			btnLogowanie = (Button) findViewById(R.id.btnLogowanie);

			initBtnOnClickListeners();
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
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initBtnOnClickListeners() {
		btnNoweWydarzenie.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NoweWydarzenie.class);
				startActivity(intent);
			}
		});

		btnListaWydarzen.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, WydarzeniaLista.class);
				startActivity(intent);
			}
		});

		btnLogowanie.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, Logowanie.class);
				startActivity(intent);
			}
		});

	}
}
