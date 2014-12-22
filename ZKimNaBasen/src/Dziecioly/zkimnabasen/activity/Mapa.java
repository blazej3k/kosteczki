package Dziecioly.zkimnabasen.activity;

import java.util.ArrayList;
import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.api.ApiAsyncTask;
import Dziecioly.zkimnabasen.api.Obs�ugaMapy;
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
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapClickListener,
		OnMarkerClickListener, OnMapReadyCallback {

	private EditText editText;
	private Button btnZnajdz;
	private Context context;

	private ApiAsyncTask asyncTask;

	private Obs�ugaMapy obs�ugaMapy;
	private GoogleMap map;
	private final LatLng defaultLatLng = new LatLng(52.23, 21);
	private boolean mapIsReady;
	private float zoom = 12.0f;

	private BitmapDescriptor colorApiMarker;
	private BitmapDescriptor colorDbMarker;
	private BitmapDescriptor colorUserMarker;
	private BitmapDescriptor colorSelectedMarker;

	private String kategoria;

	// z Api -> je�li publiczne = false, z DB-> je�li publiczne = true
	private List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();

	private Marker userMarker = null;
	private Marker selectedMarker = null;
	private LatLng selectedLanLon = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa_text);
		context = getApplicationContext();

		obs�ugaMapy = new Obs�ugaMapy(context);
		btnZnajdz = (Button) findViewById(R.id.btnZnajdz);
		editText = (EditText) findViewById(R.id.editText);
		initBtnOnClickListener();

		Bundle bundle = getIntent().getExtras();

		if (bundle.getBoolean("lok")) {
			selectedLanLon = new LatLng(bundle.getDouble("lat"),
					bundle.getDouble("lon"));
			Log.d(DatabaseManager.DEBUG_TAG, "selectedLanLon");
		}

		// pobranie lokalizacji z API i bazy
		kategoria = bundle.getString("kategoria");
		asyncTask = new ApiAsyncTask(this, kategoria);
		asyncTask.execute();

		// wczytanie mapy
		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map2);
		myMapFragment.onCreate(savedInstanceState);
		myMapFragment.getMapAsync(this);

		// kolory marker�w
		colorApiMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		colorDbMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
		colorUserMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
		colorSelectedMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
	}

	@Override
	public void onMapReady(GoogleMap mapp) {
		this.map = mapp;
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, zoom));
		map.setOnMapClickListener(this);
		map.setOnMarkerClickListener(this);

		mapIsReady = true;
		if (asyncTask.isListIsReady())
			addMarkers(lokalizacje);
	}

	private void addMarkers(List<Lokalizacja> lokalizacjeApi) {
		Log.d(DatabaseManager.DEBUG_TAG, "Rysuj� markery..");
		if (lokalizacjeApi == null)
			return;
		for (Lokalizacja lok : lokalizacjeApi) {
			LatLng latLng = new LatLng(lok.getLat(), lok.getLon());

			if (selectedLanLon != null && selectedLanLon.equals(latLng)) {
				Log.d(DatabaseManager.DEBUG_TAG,
						"rysuje wcze�niej wybrany marker - z api lub bazy");
				selectedMarker = map.addMarker(new MarkerOptions()
						.position(latLng).title(lok.getAdres())
						.snippet(lok.getOpis()).icon(colorSelectedMarker));
				selectedMarker.showInfoWindow();
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(
						selectedLanLon, map.getCameraPosition().zoom));
			}

			else if (lok.isPubliczna())
				map.addMarker(new MarkerOptions().position(latLng)
						.title(lok.getAdres()).snippet(lok.getOpis())
						.icon(colorDbMarker));

			else
				map.addMarker(new MarkerOptions().position(latLng)
						.title(lok.getAdres()).snippet(lok.getOpis())
						.icon(colorApiMarker));
		}
		// jesli wzi�ty z intentu latlon nie by� z api ani bazy -> narysuj
		// marker usera
		if (selectedLanLon != null && selectedMarker == null) {
			Log.d(DatabaseManager.DEBUG_TAG,
					"rysuje wcze�niej wybrany marker - usera");
			userMarker = map.addMarker(new MarkerOptions().position(
					selectedLanLon).icon(colorSelectedMarker));
			selectedMarker = userMarker;
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLanLon,
					map.getCameraPosition().zoom));

			// pobierz adres punktu i wy�wietl go
			String adres = obs�ugaMapy.pobierzAdres(selectedLanLon);
			if (adres == null) {
				Log.d(DatabaseManager.DEBUG_TAG, "Nie mo�na ogkre�li� adresu");
				Toast.makeText(context, "Nie mo�na ogkre�li� adresu",
						Toast.LENGTH_SHORT).show();
			} else {
				userMarker.setTitle(adres);
				userMarker.showInfoWindow();
			}

		}

	}

	boolean isApiMarker(LatLng arg) {
		if (!(kategoria.equals("P�ywalnia") || kategoria.equals("Pi�ka no�na")))
			return false;
		else {
			for (Lokalizacja lok : lokalizacje)
				if (!lok.isPubliczna() && lok.getLat() == arg.latitude
						&& lok.getLon() == arg.longitude)
					return true;
		}
		return false;

	}

	@Override
	public boolean onMarkerClick(Marker clickedMarker) {
		// je�li istnieje zielony marker
		if (selectedMarker != null) {
			// je�li klikni�to na zielony marker -> odznacz
			if (clickedMarker.getPosition()
					.equals(selectedMarker.getPosition())) {
				// je�li klikni�ty marker jest markerem u�ytkownika -> pokoloruj
				// na niebiesko
				if (userMarker != null
						&& clickedMarker.getPosition().equals(
								userMarker.getPosition()))
					clickedMarker.setIcon(colorUserMarker);
				// je�li klikni�ty marker jest markerem z api -> pokoloruj na
				// czerwono
				else if (isApiMarker(clickedMarker.getPosition()))
					clickedMarker.setIcon(colorApiMarker);
				// je�li klikni�ty marker jest markerem z bazy -> pokoloruj na
				// niebiesko
				else
					clickedMarker.setIcon(colorDbMarker);
				// odznacz marker i nie wy�wietlaj tytu�u
				selectedMarker = null;
				return true;
			}
			// je�li istnieje zielony marker i istnieje marker u�ytkownika,
			// kt�ry jest zielony -> pokoloruj go na zolto
			else if (userMarker != null
					&& selectedMarker.getPosition().equals(
							userMarker.getPosition()))
				selectedMarker.setIcon(colorUserMarker);
			// je�li istnieje zielony marker i istnieje marker api,
			// kt�ry jest zielony -> pokoloruj go na czerwono
			else if (isApiMarker(clickedMarker.getPosition()))
				selectedMarker.setIcon(colorApiMarker);
			// je�li istnieje zielony marker i istnieje marker z bazy,
			// kt�ry jest zielony -> pokoloruj go na niebiesko
			else
				selectedMarker.setIcon(colorDbMarker);

		}

		// klikni�ty marker pokoloruj na zielono, poka� tytu�
		clickedMarker.setIcon(colorSelectedMarker);
		selectedMarker = clickedMarker;
		return false;
	}

	@Override
	public void onMapClick(LatLng arg) {
		pyknijMapke(arg);
	}

	private void pyknijMapke(LatLng arg) {

		// jesli by� wybrany jakis marker -> zaznacz go na czerwono lub
		// niebiesko
		if (selectedMarker != null) {
			if (isApiMarker(selectedMarker.getPosition()))
				selectedMarker.setIcon(colorApiMarker);
			else
				selectedMarker.setIcon(colorDbMarker);
		}
		// jesli istnia� marker usera -> usu� (wyzej m�g� zosta� pomalowany na
		// niebiesko, ale i tak tutaj jest usuwany)
		if (userMarker != null)
			userMarker.remove();

		userMarker = map.addMarker(new MarkerOptions().position(arg).icon(
				colorSelectedMarker));
		selectedMarker = userMarker;

		// pobierz adres punktu i wy�wietl go
		String adres = obs�ugaMapy.pobierzAdres(arg);
		if (adres == null) {
			Log.d(DatabaseManager.DEBUG_TAG, "Nie mo�na ogkre�li� adresu");
			Toast.makeText(context, "Nie mo�na ogkre�li� adresu",
					Toast.LENGTH_SHORT).show();
		} else {
			userMarker.setTitle(adres);
			userMarker.showInfoWindow();
		}
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(arg, map.getCameraPosition().zoom));
	}

	public void setLokalizacje(List<Lokalizacja> lokalizacje) {
		this.lokalizacje = lokalizacje;
		if (mapIsReady)
			addMarkers(lokalizacje);

	}

	

	private void zaznaczWpisanyAdresNaMapie(String wpisanyAdres) {
		if (wpisanyAdres != null && !wpisanyAdres.equals("")) {
			LatLng latlon = obs�ugaMapy.pobierzMarker(wpisanyAdres);

			if (latlon == null) {
				Log.d(DatabaseManager.DEBUG_TAG, "Nieznany adres");
				Toast.makeText(context, "Nieznany adres", Toast.LENGTH_SHORT)
						.show();
			} else {
				pyknijMapke(latlon);
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, map.getCameraPosition().zoom));
			}
		}
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Log.d(DatabaseManager.DEBUG_TAG, "back button pressed");
			NoweWydarzenie.wybranaLokalizacja = getSelectedMarker();
		}
		return super.onKeyDown(keyCode, event);
	}

	public Lokalizacja getSelectedMarker() {
		if (selectedMarker == null)
			return null;
		//lokalizacja usera
		else if (userMarker != null
				&& selectedMarker.getPosition()
						.equals(userMarker.getPosition())) {
			Lokalizacja l = new Lokalizacja(
					selectedMarker.getPosition().latitude,
					selectedMarker.getPosition().longitude,
					selectedMarker.getTitle(), null, false, kategoria);
			l.setLokalizacjaUzytkownika(true);
			return l;
		//lokalizacja z bazy lub api
		} else {
			for (Lokalizacja l : lokalizacje) {
				LatLng p = new LatLng(l.getLat(), l.getLon());
				if (selectedMarker.getPosition().equals(p)) {
					l.setLokalizacjaUzytkownika(false);
					return l;

				}
			}
		}
		return null;

	}

	public void setSelectedMarker(Marker selectedMarker) {
		this.selectedMarker = selectedMarker;
	}
}
