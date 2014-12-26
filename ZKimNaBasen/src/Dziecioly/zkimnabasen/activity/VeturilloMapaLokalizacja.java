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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class VeturilloMapaLokalizacja extends FragmentActivity implements
		OnMapClickListener, OnMapReadyCallback {

	private Context context;

	private GoogleMap map;
	private Obs³ugaMapy obs³ugaMapy;
	private LatLng defaultLatLng = new LatLng(52.23, 21);
	private float zoom = 12.0f;

	private EditText editText;
	private Button btnZnajdz;

	private Marker selectedMarker = null;
	private LatLng selectedLanLon = null;
	private String adres;

	private BitmapDescriptor colorSelectedMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa_text);
		context = getApplicationContext();

		obs³ugaMapy = new Obs³ugaMapy(context);
		btnZnajdz = (Button) findViewById(R.id.btnZnajdz);
		editText = (EditText) findViewById(R.id.editText);
		initBtnOnClickListener();

		Bundle bundle = getIntent().getExtras();
		if (bundle.getBoolean("lok")) {
			selectedLanLon = new LatLng(bundle.getDouble("lat"),
					bundle.getDouble("lon"));
			adres = bundle.getString("adres");
			defaultLatLng = selectedLanLon;
		}

		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map2);
		myMapFragment.onCreate(savedInstanceState);
		myMapFragment.getMapAsync(this);

		colorSelectedMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

	}

	@Override
	public void onMapReady(GoogleMap mapp) {
		this.map = mapp;
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, zoom));
		map.setOnMapClickListener(this);
		if (selectedLanLon != null) {
			selectedMarker = map.addMarker(new MarkerOptions()
					.position(selectedLanLon).title(adres)
					.icon(colorSelectedMarker));
			selectedMarker.showInfoWindow();
		}
	}

	@Override
	public void onMapClick(LatLng arg) {
		pyknijMapke(arg, null);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Veturillo.wybranaLokalizacja = getSelectedMarker();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initBtnOnClickListener() {
		btnZnajdz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String wpisanyAdres = editText.getText().toString();
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
				selectedMarker.getPosition().longitude,
				selectedMarker.getTitle(), null, false, null);
		return l;

	}

	private void pyknijMapke(LatLng arg, String adres) {
		if (selectedMarker != null)
			selectedMarker.remove();

		selectedMarker = map.addMarker(new MarkerOptions().position(arg).icon(
				colorSelectedMarker));

		if (adres == null)
			adres = obs³ugaMapy.pobierzAdres(arg);
		if (adres == null)
			Toast.makeText(context, "Nie mo¿na okreœliæ adresu",
					Toast.LENGTH_SHORT).show();
		else {
			selectedMarker.setTitle(adres);
			selectedMarker.showInfoWindow();
		}

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(arg,
				map.getCameraPosition().zoom));
	}

	private void zaznaczWpisanyAdresNaMapie(String wpisanyAdres) {
		if (wpisanyAdres != null && !wpisanyAdres.equals("")) {
			String[] res = obs³ugaMapy.pobierzMarker(wpisanyAdres);

			if (res == null)
				Toast.makeText(context, "Nieznany adres", Toast.LENGTH_SHORT)
						.show();
			else {
				LatLng latlon = new LatLng(Double.parseDouble(res[0]),
						Double.parseDouble(res[1]));
				pyknijMapke(latlon, res[2]);
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon,
						map.getCameraPosition().zoom));
			}
		}

	}
}
