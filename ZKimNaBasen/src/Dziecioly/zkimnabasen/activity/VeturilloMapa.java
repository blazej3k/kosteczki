package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.api.Obs³ugaMapy;
import Dziecioly.zkimnabasen.api.VeturilloAsynTask;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.drive.internal.m;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class VeturilloMapa extends FragmentActivity implements
		OnMapReadyCallback {

	private LatLng origin;
	private LatLng destination;
	
	private Context context;
	private boolean mapIsReady;
	private Obs³ugaMapy obs³ugaMapy;
	
	private Marker markerOrigin;
	private Marker markerDest;

	private VeturilloAsynTask asyncTask;
	private List<LatLng> lines;
	private GoogleMap map;
	private float zoom = 15.0f;

	private BitmapDescriptor colorOriginMarker;
	private BitmapDescriptor colorDestMarker;
	private BitmapDescriptor colorVeturiloMarker;
	private String addrOrigin;
	private String addrDest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		context = getApplicationContext();

		obs³ugaMapy = new Obs³ugaMapy(context);

		Bundle bundle = getIntent().getExtras();
		origin = new LatLng(bundle.getDouble("originLat"),
				bundle.getDouble("originLon"));
		destination = new LatLng(bundle.getDouble("destLat"),
				bundle.getDouble("destLon"));
		addrOrigin = bundle.getString("addrOrigin");
		addrDest = bundle.getString("addrDest");
		
		// znajdz najblizsze stacje i wyznacz trase
		asyncTask = new VeturilloAsynTask(this, origin, destination,
				obs³ugaMapy);
		asyncTask.execute();

		colorOriginMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
		colorDestMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		colorVeturiloMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);

		// wczytanie mapy
		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		myMapFragment.onCreate(savedInstanceState);
		myMapFragment.getMapAsync(this);

	}

	@Override
	public void onMapReady(GoogleMap mapp) {
		this.map = mapp;
	
		markerOrigin = map.addMarker(new MarkerOptions().position(origin).icon(
				colorOriginMarker));
		markerDest = map.addMarker(new MarkerOptions().position(destination).icon(
				colorDestMarker));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, zoom));
		
		mapIsReady = true;
		if (asyncTask.isVeturilloIsReady())
			narysujTrase(lines);

	}

	private void narysujTrase(List<LatLng> lines) {
		LatLng vetOrigin = asyncTask.getVetOrigin();
		LatLng vetDest = asyncTask.getVetDest();
		map.addMarker(new MarkerOptions().position(vetOrigin).icon(
				colorVeturiloMarker));
		map.addMarker(new MarkerOptions().position(vetDest).icon(
				colorVeturiloMarker));

		map.addPolyline(new PolylineOptions().addAll(lines).width(5)
				.color(Color.BLUE));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(vetOrigin, zoom));
		
		markerOrigin.setTitle("AAA");
		markerOrigin.showInfoWindow();
		
		markerDest.setTitle(addrDest);
		markerDest.showInfoWindow();

		String distance = asyncTask.getDistance();
		String time = asyncTask.getTime();

		Toast.makeText(context, distance + ",   " + time, Toast.LENGTH_LONG)
				.show();
	}

	public void setLines(List<LatLng> lines) {
		this.lines = lines;
		if (mapIsReady)
			narysujTrase(lines);

	}

}
