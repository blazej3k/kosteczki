package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.api.HttpRequest;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Mapa extends FragmentActivity implements OnMapClickListener {

	private GoogleMap map;

	private String swimmingPoolsUrl = "https://api.bihapi.pl/wfs/warszawa/swimmingPools?maxFeatures=1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new HttpRequest(this).execute(swimmingPoolsUrl);
		
		setContentView(R.layout.mapa);

		FragmentManager myFragmentManager = getSupportFragmentManager();
		SupportMapFragment myMapFragment = (SupportMapFragment) myFragmentManager
				.findFragmentById(R.id.map);
		map = myMapFragment.getMap();
	}

	public void addMarkers(String response) {
		Log.d(DatabaseManager.DEBUG_TAG, "AAAA");
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}
}