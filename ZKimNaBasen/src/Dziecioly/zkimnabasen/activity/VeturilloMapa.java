package Dziecioly.zkimnabasen.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.api.HttpRequest;
import Dziecioly.zkimnabasen.api.Obs³ugaMapy;
import Dziecioly.zkimnabasen.api.VeturilloAsynTask;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class VeturilloMapa extends FragmentActivity implements
		OnMapReadyCallback {
	private final LatLng defaultLatLng = new LatLng(52.23, 21);

	LatLng origin;
	LatLng destination;
	String distance;

	private Context context;

	private Obs³ugaMapy obs³ugaMapy;

	private List<LatLng> lines;
	private GoogleMap map;

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
		String url = createUrl(origin, destination);

		VeturilloAsynTask asynTask = new VeturilloAsynTask(this, origin,
				destination);
		asynTask.execute();

		// wyznaczenie trasy
		HttpRequest request = new HttpRequest();
		String response = request.getFromUrl(url, false);
		lines = parseResponse(response);

		// wczytanie mapy
		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		myMapFragment.onCreate(savedInstanceState);
		myMapFragment.getMapAsync(this);

	}

	private String createUrl(LatLng origin, LatLng destination) {
		return "http://maps.googleapis.com/maps/api/directions/json?origin="
				+ origin.latitude + "," + origin.longitude + "&destination="
				+ destination.latitude + "," + destination.longitude
				+ "&sensor=false";
	}

	private List<LatLng> parseResponse(String response) {
		try {

			JSONObject result = new JSONObject(response);
			JSONArray routes = result.getJSONArray("routes");

			long distanceForSegment = routes.getJSONObject(0)
					.getJSONArray("legs").getJSONObject(0)
					.getJSONObject("distance").getInt("value");
			distance = new DecimalFormat("##.##")
					.format(distanceForSegment / 1000) + " km";

			Log.d(DatabaseManager.DEBUG_TAG, Long.toString(distanceForSegment));
			Log.d(DatabaseManager.DEBUG_TAG, distance);
			JSONArray steps = routes.getJSONObject(0).getJSONArray("legs")
					.getJSONObject(0).getJSONArray("steps");

			List<LatLng> lines = new ArrayList<LatLng>();

			for (int i = 0; i < steps.length(); i++) {
				String polyline = steps.getJSONObject(i)
						.getJSONObject("polyline").getString("points");

				for (LatLng p : obs³ugaMapy.decodePolyline(polyline)) {
					lines.add(p);
				}
			}

			return lines;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onMapReady(GoogleMap mapp) {
		this.map = mapp;
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12.0f));
		Polyline polylineToAdd = map.addPolyline(new PolylineOptions()
				.addAll(lines).width(3).color(Color.RED));

	}

}
