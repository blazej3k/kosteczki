package Dziecioly.zkimnabasen.activity;

import java.util.List;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.api.Obs³ugaMapy;
import Dziecioly.zkimnabasen.api.VeturilloAsynTask;
import Dziecioly.zkimnabasen.api.VeturilloAsynTask.VeturiloAsyncListener;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

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
		OnMapReadyCallback, VeturiloAsyncListener {

	private LatLng origin;
	private LatLng destination;

	private Context context;
	private Obs³ugaMapy obs³ugaMapy;

	private Marker markerOrigin;
	private Marker markerDest;

	private boolean rowery;

	private VeturilloAsynTask asyncTask;
	private List<LatLng> lines;
	private GoogleMap map;
	private float zoom = 15.0f;

	private BitmapDescriptor colorOriginMarker;
	private BitmapDescriptor colorDestMarker;
	private BitmapDescriptor colorVeturiloMarker;
	private String addrOrigin;
	private String addrDest;
	
	private boolean isReady = false;

	// private boolean myLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		context = getApplicationContext();

		obs³ugaMapy = new Obs³ugaMapy(context);

		Bundle bundle = getIntent().getExtras();

		destination = new LatLng(bundle.getDouble("destLat"),
				bundle.getDouble("destLon"));
		addrDest = bundle.getString("addrDest");
		rowery = bundle.getBoolean("rowery");

		origin = new LatLng(bundle.getDouble("originLat"),
				bundle.getDouble("originLon"));
		addrOrigin = bundle.getString("addrOrigin");

		// znajdz najblizsze stacje i wyznacz trase
		asyncTask = new VeturilloAsynTask(this, origin, destination,
				obs³ugaMapy, rowery);
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
		markerDest = map.addMarker(new MarkerOptions().position(destination)
				.icon(colorDestMarker));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, zoom));

		Toast.makeText(context, "Wyznaczanie trasy..", Toast.LENGTH_LONG)
				.show();

		if (isReady)
			narysujTrase(lines);

	}

	private void narysujTrase(List<LatLng> lines) {
		LatLng vetOrigin = asyncTask.getVetOrigin();
		LatLng vetDest = asyncTask.getVetDest();
		if (vetOrigin == null || vetDest == null) {
			Toast.makeText(
					context,
					"B³¹d pobierania API lub brak stacji spe³niaj¹cych kryteria",
					Toast.LENGTH_LONG).show();
			return;

		}

		map.addMarker(new MarkerOptions().position(vetOrigin).icon(
				colorVeturiloMarker));
		map.addMarker(new MarkerOptions().position(vetDest).icon(
				colorVeturiloMarker));

		markerOrigin.setTitle(addrOrigin);
		markerOrigin.showInfoWindow();

		markerDest.setTitle(addrDest);
		markerDest.showInfoWindow();

		if (lines == null) {
			Toast.makeText(context, "Nie mo¿na wyznaczyæ trasy",
					Toast.LENGTH_LONG).show();
			return;
		}
		if (lines.size() == 0) {
			Toast.makeText(context, "Lokalizacje s¹ zbyt blisko siebie ",
					Toast.LENGTH_LONG).show();
			return;
		}
		map.addPolyline(new PolylineOptions().addAll(lines).width(5)
				.color(Color.BLUE));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(vetOrigin, zoom));

		String distance = asyncTask.getDistance();
		String time = asyncTask.getTime();
		Toast.makeText(context, distance + ",   " + time, Toast.LENGTH_LONG)
				.show();
	}

	

	@Override
	public void doStuff(List<LatLng> lines) {
		this.lines = lines;
		isReady = true;
		if (map!=null)
			narysujTrase(lines);
	}

}