package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SzczegolyWydarzeniaMapa extends FragmentActivity implements
		OnMapReadyCallback {

	private LatLng latLng;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		
		Bundle bundle = getIntent().getExtras();
		double lat = bundle.getDouble("lat");
		double lon = bundle.getDouble("lon");
		latLng = new LatLng(lat, lon);

		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		myMapFragment.onCreate(savedInstanceState);
		myMapFragment.getMapAsync(this);

	}

	@Override
	public void onMapReady(GoogleMap mapp) {
		this.map = mapp;
		map.addMarker(new MarkerOptions().position(latLng));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

	}

}
