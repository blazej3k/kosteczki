package Dziecioly.zkimnabasen.activity;

import java.util.ArrayList;
import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.api.ApiAsyncTask;
import Dziecioly.zkimnabasen.api.HttpRequest;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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

public class Mapa extends FragmentActivity implements OnMapClickListener,
		OnMarkerClickListener, OnMapReadyCallback  {
	
	
	private ApiAsyncTask asyncTask;

	private GoogleMap map;
	private final LatLng defaultLatLng = new LatLng(52.23, 21);
	private boolean mapIsReady;
	
	private BitmapDescriptor colorApiMarker;
	private BitmapDescriptor colorDbMarker;
	private BitmapDescriptor colorUserMarker;
	private BitmapDescriptor colorSelectedMarker;
	
	private String kategoria;
	private List<Lokalizacja> lokalizacjeApi = new ArrayList<Lokalizacja>();
	private List<Lokalizacja> lokalizacjeDb = new ArrayList<Lokalizacja>();
	
	private Marker userMarker = null;
	private Marker selectedMarker = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa_text);
		
		//pobranie lokalizacji z API
		kategoria = (String) getIntent().getExtras().get("kategoria");
		asyncTask = new ApiAsyncTask(this, kategoria);
		
		
		
		//wczytanie mapy
		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map2);
		myMapFragment.onCreate(savedInstanceState);
		myMapFragment.getMapAsync(this);

		
		//kolory markerów
		colorApiMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		colorDbMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
		colorUserMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
		colorSelectedMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
	}
	

	@Override
	public void onMapReady(GoogleMap arg0) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12.0f));
		map.setOnMapClickListener(this);
		map.setOnMarkerClickListener(this);
		
		mapIsReady = true;
		if (asyncTask.isListIsReady())
			addMarkers(lokalizacjeApi);
	}
	
	
	private void addMarkers(List<Lokalizacja> lokalizacjeApi)
	{
		
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void setLokalizacje(List<Lokalizacja> lokalizacje) {
		this.lokalizacjeApi = lokalizacje;
		if (mapIsReady)
			addMarkers(lokalizacjeApi);

	}
}
