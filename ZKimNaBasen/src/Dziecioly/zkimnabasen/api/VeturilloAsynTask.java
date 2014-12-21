package Dziecioly.zkimnabasen.api;

import Dziecioly.zkimnabasen.activity.VeturilloMapa;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class VeturilloAsynTask extends AsyncTask<Void, String, String> {

	private boolean veturilloIsReady;
	private String urlVeturillo = "https://api.bihapi.pl/wfs/warszawa/veturilo";
	private HttpRequest request = new HttpRequest();
	private VeturilloMapa mapa;
	private LatLng origin;
	private LatLng destination;

	public VeturilloAsynTask(VeturilloMapa mapa, LatLng origin,
			LatLng destination) {
		this.mapa = mapa;
		this.origin = origin;
		this.destination = destination;
	}

	@Override
	protected String doInBackground(Void... params) {
		veturilloIsReady = false;
		znajdzStacje(origin);
		return null;
	}

	private LatLng znajdzStacje(LatLng latLng) {

		String url = createUrl(latLng, 1000);
		String response = request.getFromUrl(url, true);
		Log.d(DatabaseManager.DEBUG_TAG, response);
		return null;
	}

	private String createUrl(LatLng latLng, int promien) {
		return urlVeturillo + "?circle=" + latLng.longitude + ","
				+ latLng.latitude + "," + promien;
	}
	
	
	public static float calcDistance(LatLng latLng1, LatLng latLng2) {
		double lat1 = latLng1.latitude;
		double lng1 = latLng1.longitude;
		double lat2 = latLng2.latitude;
		double lng2 = latLng2.longitude;

		double earthRadius = 6371; // kilometers
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);

		return dist;
	}

}
