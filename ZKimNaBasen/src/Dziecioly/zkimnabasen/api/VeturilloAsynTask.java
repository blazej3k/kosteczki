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

}
