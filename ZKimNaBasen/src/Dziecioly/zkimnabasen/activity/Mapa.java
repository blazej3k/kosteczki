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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapClickListener,
		OnMarkerClickListener, OnMapReadyCallback {

	private String swimmingPoolsUrl = "https://api.bihapi.pl/wfs/warszawa/swimmingPools";
	private String sportFieldsUrl = "https://api.bihapi.pl/wfs/warszawa/swimmingPools";
	private HttpRequest request = new HttpRequest(this);
	
	private GoogleMap map;
	private boolean mapIsReady;
	private final LatLng defaultLatLng = new LatLng(52.23, 21);
	
	private List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();
	private Marker userMarker;
	private Marker selectedMarker;
	
	private String kategoria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		kategoria = (String) getIntent().getExtras().get("kategoria");
		if (kategoria.equals(Lokalizacja.kategorie[7]))
			request.execute(swimmingPoolsUrl);
		else if (kategoria.equals(Lokalizacja.kategorie[5]))
			request.execute(sportFieldsUrl);

		mapIsReady = false;

		setContentView(R.layout.mapa);

		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		myMapFragment.onCreate(savedInstanceState);

		myMapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap mapp) {
		this.map = mapp;
		Log.d(DatabaseManager.DEBUG_TAG, "kk");
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12.0f));
		map.setOnMapClickListener(this);
		map.setOnMarkerClickListener(this);

		mapIsReady = true;
		if (request.isListIsReady())
			addMarkers(lokalizacje);

	}

	public void addMarkers(List<Lokalizacja> lokalizacje) {
		for (Lokalizacja l : lokalizacje)
			map.addMarker(new MarkerOptions()
					.position(new LatLng(l.getLat(), l.getLon()))
					.title(l.getOpis()).snippet(l.getAdres()));

	}

	@Override
	public void onMapClick(LatLng arg) {

		if (selectedMarker != null)
			selectedMarker.setIcon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		if (userMarker != null)
			userMarker.remove();

		userMarker = map.addMarker(new MarkerOptions().position(arg).icon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
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
				if (clickedMarker.getPosition()
						.equals(userMarker.getPosition()))
					clickedMarker.setIcon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
				// jeœli klikniêty marker jest markerem z api -> pokoloruj na
				// czerwono
				else

					clickedMarker.setIcon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				// odznacz marker i nie wyœwietlaj tytu³u
				selectedMarker = null;
				return true;
			}
			// jeœli istnieje zielony marker i istnieje marker u¿ytkownika,
			// który jest zielony -> pokoloruj go na niebiesko
			else if (userMarker != null
					&& selectedMarker.getPosition().equals(
							userMarker.getPosition()))
				selectedMarker.setIcon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
			// jeœli istnieje zielony marker i istnieje marker api,
			// który jest zielony -> pokoloruj go na czerwono
			else
				selectedMarker.setIcon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED));

		}

		// klikniêty marker pokoloruj na zielono, poka¿ tytu³
		clickedMarker.setIcon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		selectedMarker = clickedMarker;
		return false;

	}

	public Marker getSelectedMarker() {
		return selectedMarker;
	}

	public void setSelectedMarker(Marker selectedMarker) {
		this.selectedMarker = selectedMarker;
	}

}