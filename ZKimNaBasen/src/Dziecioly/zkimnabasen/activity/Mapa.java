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

	private GoogleMap map;
	private boolean mapIsReady;
	private String swimmingPoolsUrl = "https://api.bihapi.pl/wfs/warszawa/swimmingPools";
	private final LatLng defaultLatLng = new LatLng(52.23, 21);
	private List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();
	private Marker userMarker;
	private Marker selectedMarker;
	private HttpRequest request = new HttpRequest(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mapIsReady = false;
		request.execute(swimmingPoolsUrl);
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
		if (selectedMarker != null) {
			if (userMarker != null && selectedMarker.getPosition().equals(userMarker.getPosition()))
				selectedMarker.setIcon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
			else
				selectedMarker.setIcon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		}

		clickedMarker.setIcon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		selectedMarker = clickedMarker;
		return false;
	}
}