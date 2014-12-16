package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.DbAdapter;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import android.content.Context;
import android.content.Intent;
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
	private DbAdapter db;

	UzytkownikDao uzytkownikDao = new UzytkownikDao();
	ZaproszenieDao zaproszenieDao = new ZaproszenieDao();
	WydarzenieDao wydarzenieDao = new WydarzenieDao();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.moj_kalendarz);

		DatabaseManager.init(this);
		// Uzytkownik u = new Uzytkownik("Karolina", 333355888, "dzieciol");
		// uzytkownikDao.add(u);


		// DatabaseManager.getInstance().close();

		/*
		 * db = new DbAdapter(context); db.open();
		 * 
		 * db.insertUzytkownik("Janek", "kuktas", "6666666");
		 * System.out.println("malcziki"); db.close();
		 */

		btnNoweWydarzenie = (Button) findViewById(R.id.btnNoweWydarzenie);
		btnListaWydarzen = (Button) findViewById(R.id.btnListaWydarzen);
		btnLogowanie = (Button) findViewById(R.id.btnLogowanie);
		
		initBtnOnClickListeners();
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

