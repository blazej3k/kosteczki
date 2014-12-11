package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.DbAdapter;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MojKalendarz extends ActionBarActivity {

	Context context;
	Button noweWydarzenie;
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
		Uzytkownik u = new Uzytkownik("Karolina", 8654444, "haslo");
		uzytkownikDao.add(u);
		
		Wydarzenie w = new Wydarzenie("basen", "Mycka 4/11", "2014-12-12", "17:00", "19:00", "bla bla", false);
		wydarzenieDao.add(w);

		Uzytkownik us = uzytkownikDao.find(1);
		List<Wydarzenie> wyd = wydarzenieDao.list();
		Toast toast = Toast
				.makeText(context, us.getNazwa() + " " + us.getNr_tel()
						+ "  " + us.getHaslo(), Toast.LENGTH_SHORT);
		toast.show();
		
		toast = Toast
				.makeText(context, wyd.get(0).getLokalizacja() + "  " + wyd.get(0).getData(), Toast.LENGTH_SHORT);
		toast.show();
		
		//DatabaseManager.getInstance().close();

		/*db = new DbAdapter(context);
		db.open();

		db.insertUzytkownik("Janek", "kuktas", "6666666");
		System.out.println("malcziki");
		db.close();*/

		noweWydarzenie = (Button) findViewById(R.id.noweWydarzenie);
		noweWydarzenie.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NoweWydarzenie.class);
				startActivity(intent);
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
