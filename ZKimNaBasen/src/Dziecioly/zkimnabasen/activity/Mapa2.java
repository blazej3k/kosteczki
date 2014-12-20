package Dziecioly.zkimnabasen.activity;

import java.util.ArrayList;
import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.api.HttpRequest;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
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

public class Mapa2 extends FragmentActivity implements OnMapClickListener,
		OnMarkerClickListener, OnMapReadyCallback {

	private String swimmingPoolsUrl = "https://api.bihapi.pl/wfs/warszawa/swimmingPools";
	private String sportFieldsUrl = "https://api.bihapi.pl/wfs/warszawa/sportFields?maxFeatures";
	private HttpRequest request = new HttpRequest(this);

	private GoogleMap map;
	private boolean mapIsReady;
	private final LatLng defaultLatLng = new LatLng(52.23, 21);

	private List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();
	private Marker userMarker;
	private Marker selectedMarker = null;

	private LatLng userLatLon;
	private boolean zMapy;
	private String adresZMapyText;

	private BitmapDescriptor colorApiMarker;
	private BitmapDescriptor colorUserMarker;
	private BitmapDescriptor colorSelectedMarker;

	private String kategoria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(DatabaseManager.DEBUG_TAG, "mapa onCreate");

		kategoria = (String) getIntent().getExtras().get("kategoria");
		if (kategoria.equals(Lokalizacja.kategorie[7]))
			request.execute(swimmingPoolsUrl);
		else if (kategoria.equals(Lokalizacja.kategorie[5]))
			request.execute(sportFieldsUrl);
		else
			request.setListIsReady(true);

		Object user = getIntent().getExtras().get("userAddress");
		if (user != null) {
			boolean userAddress = (Boolean) user;
			if (userAddress) {
				Double userLat = (Double) getIntent().getExtras().get("lat");
				Double userLon = (Double) getIntent().getExtras().get("lon");
				userLatLon = new LatLng(userLat, userLon);
				zMapy = (Boolean) getIntent().getExtras().get("zMapy");
				if (zMapy)
					adresZMapyText = (String) getIntent().getExtras().get(
							"adresZMapyText");

			} else {
				Log.d(DatabaseManager.DEBUG_TAG, "Nieznana lokalizacja");
				Toast.makeText(getApplicationContext(), "Nieznana lokalizacja",
						Toast.LENGTH_LONG).show();
			}

		}

		mapIsReady = false;

		setContentView(R.layout.mapa_text);

		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map2);
		myMapFragment.onCreate(savedInstanceState);

		myMapFragment.getMapAsync(this);

		colorApiMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		colorUserMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
		colorSelectedMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
		
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        Log.d(DatabaseManager.DEBUG_TAG, "back button pressed");
	        
	        NoweWydarzenie.wybranaLokalizacja = getSelectedMarker();
	        
	       
	    }
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onMapReady(GoogleMap mapp) {
		this.map = mapp;
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12.0f));
		map.setOnMapClickListener(this);
		map.setOnMarkerClickListener(this);

		mapIsReady = true;
		if (request.isListIsReady())
			addMarkers(lokalizacje);

	}

	public void addMarkers(List<Lokalizacja> lokalizacje) {
		Log.d(DatabaseManager.DEBUG_TAG, "Narysuj marker..");
		if (userLatLon != null && !zMapy) {
			// lokalizacja wpisana przez usera -> narysuj marker usera i zaznacz
			// go jako wybrany
			Log.d(DatabaseManager.DEBUG_TAG,
					"Rysuje marker " + Double.toString(userLatLon.latitude));
			userMarker = map.addMarker(new MarkerOptions().position(userLatLon)
					.icon(colorSelectedMarker));
			selectedMarker = userMarker;

		}
		// rysuj markery z api
		for (Lokalizacja l : lokalizacje) {
			LatLng apiMarkerLatLng = new LatLng(l.getLat(), l.getLon());

			// lokalizacja wybrana wczesniej z mapy -> narysuj zielony marker
			if (userLatLon != null && zMapy
					&& adresZMapyText.equalsIgnoreCase(l.getAdres())) {
				selectedMarker = map.addMarker(new MarkerOptions()
						.position(apiMarkerLatLng).title(l.getOpis())
						.snippet(l.getAdres()).icon(colorSelectedMarker));

			}
			// pozosta³e markeryApi - czerwone
			else {
				map.addMarker(new MarkerOptions().position(apiMarkerLatLng)
						.title(l.getOpis()).snippet(l.getAdres()));
			}

		}

	}

	@Override
	public void onMapClick(LatLng arg) {

		if (selectedMarker != null)
			selectedMarker.setIcon(colorApiMarker);
		if (userMarker != null)
			userMarker.remove();

		userMarker = map.addMarker(new MarkerOptions().position(arg).icon(
				colorSelectedMarker));
		selectedMarker = userMarker;

	}

	public void setLokalizacje(List<Lokalizacja> lokalizacje) {
		this.lokalizacje = lokalizacje;
		if (mapIsReady)
			addMarkers(lokalizacje);

	}

	@Override
	public boolean onMarkerClick(Marker clickedMarker) {
		// jeœli istnieje zielony marker
		if (selectedMarker != null) {
			// jeœli klikniêto na zielony marker -> odznacz
			if (clickedMarker.getPosition()
					.equals(selectedMarker.getPosition())) {
				// jeœli klikniêty marker jest markerem u¿ytkownika -> pokoloruj
				// na niebiesko
				if (userMarker != null
						&& clickedMarker.getPosition().equals(
								userMarker.getPosition()))
					clickedMarker.setIcon(colorUserMarker);
				// jeœli klikniêty marker jest markerem z api -> pokoloruj na
				// czerwono
				else

					clickedMarker.setIcon(colorApiMarker);
				// odznacz marker i nie wyœwietlaj tytu³u
				selectedMarker = null;
				return true;
			}
			// jeœli istnieje zielony marker i istnieje marker u¿ytkownika,
			// który jest zielony -> pokoloruj go na niebiesko
			else if (userMarker != null
					&& selectedMarker.getPosition().equals(
							userMarker.getPosition()))
				selectedMarker.setIcon(colorUserMarker);
			// jeœli istnieje zielony marker i istnieje marker api,
			// który jest zielony -> pokoloruj go na czerwono
			else
				selectedMarker.setIcon(colorApiMarker);

		}

		// klikniêty marker pokoloruj na zielono, poka¿ tytu³
		clickedMarker.setIcon(colorSelectedMarker);
		selectedMarker = clickedMarker;
		return false;

	}

	public Lokalizacja getSelectedMarker() {
		if (selectedMarker == null)
			return null;
		else if (userMarker != null
				&& selectedMarker.getPosition()
						.equals(userMarker.getPosition()))
			return new Lokalizacja(selectedMarker.getPosition().latitude,
					selectedMarker.getPosition().longitude, null, null, false,
					null, true);
		else {
			for (Lokalizacja l : lokalizacje) {
				LatLng p = new LatLng(l.getLat(), l.getLon());
				if (selectedMarker.getPosition().equals(p))
					return l;
			}
		}
		return null;

	}

	public void setSelectedMarker(Marker selectedMarker) {
		this.selectedMarker = selectedMarker;
	}

}