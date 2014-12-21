package Dziecioly.zkimnabasen.activity;

import com.google.android.gms.maps.model.LatLng;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Veturillo extends Activity {

	private Context context;
	private TextView textDestination;
	private Button btnOrigin;
	private CheckBox checkBoxMojaLokalizacja;
	private CheckBox checkBoxRowery;
	private Button btnWyznacz;

	private double destLat;
	private double destLon;
	private String destAdres;

	public static Lokalizacja wybranaLokalizacja;
	private Lokalizacja lokalizacja;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.veturillo);
		context = getApplicationContext();

		textDestination = (TextView) findViewById(R.id.textDestination);
		btnOrigin = (Button) findViewById(R.id.btnOrigin);
		checkBoxMojaLokalizacja = (CheckBox) findViewById(R.id.checkBoxMojaLokalizacja);
		checkBoxRowery = (CheckBox) findViewById(R.id.checkBoxRowery);
		btnWyznacz = (Button) findViewById(R.id.btnWyznacz);

		destLat = getIntent().getExtras().getDouble("lat");
		destLon = getIntent().getExtras().getDouble("lon");
		destAdres = getIntent().getExtras().getString("adres");

		textDestination.setText("Lokalizacja docelowa:\n" + destAdres);

		initBtnOnClickListeners();

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(DatabaseManager.DEBUG_TAG, "onResume");
		if (wybranaLokalizacja != null) {
			lokalizacja = wybranaLokalizacja;
			String adres = wybranaLokalizacja.getAdres();
			Log.d(DatabaseManager.DEBUG_TAG, adres);
			if (adres != null) {
				btnOrigin.setText(adres);
				Log.d(DatabaseManager.DEBUG_TAG, adres);
			} else {
				btnOrigin.setText("");
			}
		}
		wybranaLokalizacja = null;
	}

	private void wyznacz() {
		if (lokalizacja == null)
			Toast.makeText(context, "Wybierz sk¹d wyznaczyæ trasê",
					Toast.LENGTH_SHORT).show();
		double originLat = lokalizacja.getLat();
		double originLon = lokalizacja.getLon();

		boolean wolneRowery = checkBoxRowery.isChecked();
		Intent intent = new Intent(context, VeturilloMapa.class);

		intent.putExtra("originLat", originLat);
		intent.putExtra("originLon", originLon);
		intent.putExtra("destLat", destLat);
		intent.putExtra("destLon", destLon);

		startActivity(intent);

	}

	private void initBtnOnClickListeners() {
		btnWyznacz.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				wyznacz();
			}
		});

		btnOrigin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						VeturilloMapaLokalizacja.class);
				startActivity(intent);

			}
		});
	}



}
