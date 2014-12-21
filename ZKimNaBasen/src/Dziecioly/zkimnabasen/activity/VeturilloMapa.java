package Dziecioly.zkimnabasen.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.api.HttpRequest;
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

public class VeturilloMapa extends FragmentActivity implements OnMapReadyCallback {
	private final LatLng defaultLatLng = new LatLng(52.23, 21);
	
	LatLng origin = new LatLng(52.267218, 20.962397);
	LatLng destination = new LatLng(52.242196, 20.992990);
	String url = "http://maps.googleapis.com/maps/api/directions/json?origin="
			+ origin.latitude + "," + origin.longitude + "&destination="
			+ destination.latitude + "," + destination.longitude
			+ "&sensor=false";
	private Context context;

	private List<LatLng> lines;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		context = getApplicationContext();

		HttpRequest request = new HttpRequest();
		String response = request.getFromUrl(url, false);

		lines = parseResponse(response);
		
		Log.d(DatabaseManager.DEBUG_TAG, Integer.toString(lines.size()));

		SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		myMapFragment.onCreate(savedInstanceState);
		myMapFragment.getMapAsync(this);

	}

	private List<LatLng> parseResponse(String response) {
		try {

			JSONObject result = new JSONObject(response);
			JSONArray routes = result.getJSONArray("routes");

			long distanceForSegment = routes.getJSONObject(0)
					.getJSONArray("legs").getJSONObject(0)
					.getJSONObject("distance").getInt("value");

			JSONArray steps = routes.getJSONObject(0).getJSONArray("legs")
					.getJSONObject(0).getJSONArray("steps");

			List<LatLng> lines = new ArrayList<LatLng>();

			for (int i = 0; i < steps.length(); i++) {
				String polyline = steps.getJSONObject(i)
						.getJSONObject("polyline").getString("points");

				for (LatLng p : decodePolyline(polyline)) {
					lines.add(p);
				}
			}

			return lines;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<LatLng> decodePolyline(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();

		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((double) lat / 1E5, (double) lng / 1E5);
			poly.add(p);
		}

		return poly;
	}

	@Override
	public void onMapReady(GoogleMap mapp) {
		this.map = mapp;
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12.0f));
		Polyline polylineToAdd = map.addPolyline(new PolylineOptions()
				.addAll(lines).width(3).color(Color.RED));

	}

}
