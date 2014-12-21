package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.api.Obs³ugaMapy;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class VeturilloMapaLokalizacja extends FragmentActivity implements
		OnMapClickListener, OnMapReadyCallback {

	private Context context;

	private GoogleMap map;
	private Obs³ugaMapy obs³ugaMapy;
	private final LatLng defaultLatLng = new LatLng(52.23, 21);
	private float zoom = 12.0f;

	private EditText editText;
	private Button btnZnajdz;

	private Marker selectedMarker = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa_text);
		context = getApplicationContext();

		obs³ugaMapy = new Obs³ugaMapy(context);
		btnZnajdz = (Button) findViewById(R.id.btnZnajdz);
		editText = (EditText) findViewById(R.id.editText);
		initBtnOnClickListener();

		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map2);
		myMapFragment.onCreate(savedInstanceState);
		myMapFragment.getMapAsync(this);

	}

	@Override
	public void onMapReady(GoogleMap mapp) {
		this.map = mapp;
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, zoom));
		map.setOnMapClickListener(this);

	}

	@Override
	public void onMapClick(LatLng arg) {
		pyknijMapke(arg, true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Log.d(DatabaseManager.DEBUG_TAG, "back button pressed");
			Veturillo.wybranaLokalizacja = getSelectedMarker();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initBtnOnClickListener() {
		btnZnajdz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String wpisanyAdres = editText.getText().toString();
				Log.d(DatabaseManager.DEBUG_TAG, wpisanyAdres);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				zaznaczWpisanyAdresNaMapie(wpisanyAdres);
			}
		});
	}

	public Lokalizacja getSelectedMarker() {
		if (selectedMarker == null)
			return null;
		Lokalizacja l = new Lokalizacja(selectedMarker.getPosition().latitude,
				selectedMarker.getPosition().longitude, editText.getText()
						.toString(), null, false, null);
		return l;

	}

	private void pyknijMapke(LatLng arg, boolean onclick) {
		if (selectedMarker != null)
			selectedMarker.remove();

		selectedMarker = map.addMarker(new MarkerOptions().position(arg));

		String adres = obs³ugaMapy.pobierzAdres(arg);
		if (adres == null) {
			Log.d(DatabaseManager.DEBUG_TAG, "Nie mo¿na ogkreœliæ adresu");
			Toast.makeText(context, "Nie mo¿na ogkreœliæ adresu",
					Toast.LENGTH_SHORT).show();
		} else if (onclick)
			editText.setText(adres);
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(arg, zoom));
	}

	private void zaznaczWpisanyAdresNaMapie(String wpisanyAdres) {
		if (wpisanyAdres != null && !wpisanyAdres.equals("")) {
			LatLng latlon = obs³ugaMapy.pobierzMarker(wpisanyAdres);

			if (latlon == null) {
				Log.d(DatabaseManager.DEBUG_TAG, "Nieznany adres");
				Toast.makeText(context, "Nieznany adres", Toast.LENGTH_SHORT)
						.show();
			} else {
				pyknijMapke(latlon, false);
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, zoom));
			}
		}
	}

}
