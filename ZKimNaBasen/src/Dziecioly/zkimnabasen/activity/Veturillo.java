package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Veturillo extends Activity {

	private Context context;
	private TextView textDestination;
	private Button btnOrigin;
	private CheckBox checkBoxRowery;
	private CheckBox checkBoxMojaLokalizacja;
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
		btnWyznacz = (Button) findViewById(R.id.btnWyznacz);
		checkBoxRowery = (CheckBox) findViewById(R.id.checkBoxRowery);
		checkBoxMojaLokalizacja = (CheckBox) findViewById(R.id.checkBoxMojaLokalizacja);

		destLat = getIntent().getExtras().getDouble("lat");
		destLon = getIntent().getExtras().getDouble("lon");
		destAdres = getIntent().getExtras().getString("adres");

		textDestination.setText("Lokalizacja docelowa:\n" + destAdres);

		initBtnOnClickListeners();

	}

	@Override
	protected void onResume() {
		super.onResume();
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
		if (lokalizacja == null && !checkBoxMojaLokalizacja.isChecked()) {
			Toast.makeText(context, "Wybierz sk¹d wyznaczyæ trasê",
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (checkBoxMojaLokalizacja.isChecked()) {
			if (!pobierzMojaLokalizacje())
				return;
		}

		Intent intent = new Intent(context, VeturilloMapa.class);
		double originLat = lokalizacja.getLat();
		double originLon = lokalizacja.getLon();
		String originAdres = lokalizacja.getAdres();

		intent.putExtra("originLat", originLat);
		intent.putExtra("originLon", originLon);
		intent.putExtra("addrOrigin", originAdres);

		intent.putExtra("destLat", destLat);
		intent.putExtra("destLon", destLon);
		intent.putExtra("addrDest", destAdres);
		intent.putExtra("rowery", checkBoxRowery.isChecked());

		startActivity(intent);

	}

	private boolean pobierzMojaLokalizacje() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		String provider = locationManager.getBestProvider(new Criteria(), true);
		if (provider != null) {
			Log.d(DatabaseManager.DEBUG_TAG, provider);
			Location myLocation = locationManager
					.getLastKnownLocation(provider);
			if (myLocation != null) {
				double lat = myLocation.getLatitude();
				double lon = myLocation.getLongitude();
				lokalizacja = new Lokalizacja(lat, lon, null, null, false, null);
				return true;

			}
		}
		Toast.makeText(context, "Nie mo¿na okreœliæ bie¿¹cej lokalizacji",
				Toast.LENGTH_SHORT).show();
		return false;

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
				if (lokalizacja != null) {
					double originLat = lokalizacja.getLat();
					double originLon = lokalizacja.getLon();
					String originAdres = lokalizacja.getAdres();
					intent.putExtra("lok", true);
					intent.putExtra("lat", originLat);
					intent.putExtra("lon", originLon);
					intent.putExtra("adres", originAdres);
				} else {
					intent.putExtra("lok", false);
				}
				startActivity(intent);

			}
		});

		checkBoxMojaLokalizacja
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							btnOrigin.setText("Moja lokalizacja");
							btnOrigin.setEnabled(false);
						} else {
							if (lokalizacja == null)
								btnOrigin.setText("Sk¹d?");
							else
								btnOrigin.setText(lokalizacja.getAdres());
							btnOrigin.setEnabled(true);
						}
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

}
