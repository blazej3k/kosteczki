package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import Dziecioly.zkimnabasen.fragment.ChecboxListFragment;
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

public class MojKalendarz extends FragmentActivity implements
		ChecboxListFragment.NoticeDialogListener {

	Context context;
	private Button btnNoweWydarzenie;
	private Button btnListaWydarzen;
	private Button btnLogowanie;

	private ChecboxListFragment frag;

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

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
}
