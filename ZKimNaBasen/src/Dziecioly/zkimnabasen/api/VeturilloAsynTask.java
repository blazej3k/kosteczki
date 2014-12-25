package Dziecioly.zkimnabasen.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Dziecioly.zkimnabasen.activity.VeturilloMapa;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class VeturilloAsynTask extends AsyncTask<Void, Void, List<LatLng>> {

	private boolean veturilloIsReady = false;
	private String urlVeturillo = "https://api.bihapi.pl/wfs/warszawa/veturilo";

	private HttpRequest request = new HttpRequest();
	private VeturilloMapa mapa;
	private LatLng origin;
	private LatLng destination;
	private Obs³ugaMapy obs³ugaMapy;

	private List<LatLng> lines = new ArrayList<LatLng>();
	private String distance;
	private String time;
	private LatLng vetOrigin;
	private LatLng vetDest;

	private boolean rowery;

	public VeturilloAsynTask(VeturilloMapa mapa, LatLng origin,
			LatLng destination, Obs³ugaMapy obs³ugaMapy, boolean rowery) {
		this.mapa = mapa;
		this.origin = origin;
		this.destination = destination;
		this.obs³ugaMapy = obs³ugaMapy;
		this.rowery = rowery;
	}

	@Override
	protected List<LatLng> doInBackground(Void... params) {
		veturilloIsReady = false;

		Log.d(DatabaseManager.DEBUG_TAG,
				"Origin " + Double.toString(origin.latitude) + "  "
						+ Double.toString(origin.longitude));
		Log.d(DatabaseManager.DEBUG_TAG,
				"Dest " + Double.toString(destination.latitude) + "  "
						+ Double.toString(destination.longitude));
		// znajdz najblizsze stacje
		vetOrigin = znajdzStacje(origin, rowery);
		if (vetOrigin != null)
			vetDest = znajdzStacje(destination, rowery);

/*		Log.d(DatabaseManager.DEBUG_TAG,
				"VETURILLO Origin " + Double.toString(vetOrigin.latitude)
						+ "  " + Double.toString(vetOrigin.longitude));
		Log.d(DatabaseManager.DEBUG_TAG,
				"VETURILLO Dest " + Double.toString(vetDest.latitude) + "  "
						+ Double.toString(vetDest.longitude));
*/
		// wyznacz trase
		if (vetOrigin != null && vetDest != null && !vetOrigin.equals(vetDest)) {
			lines = wyznaczTrase(vetOrigin, vetDest);
		}

		return lines;
	}

	@Override
	protected void onPostExecute(List<LatLng> result) {
		super.onPostExecute(result);
		mapa.setLines(result);
		veturilloIsReady = true;
	}

	private List<LatLng> wyznaczTrase(LatLng vetOrigin, LatLng vetDest) {
		String url = createUrlGoogle(vetOrigin, vetDest);
		String response = request.getFromUrl(url, false);
		lines = parseResponseGoogle(response);
		return lines;
	}

	private LatLng znajdzStacje(LatLng punkt, boolean rowery) {
		int promien = 1000;

		while (promien < 5000) {
			String url = createUrl(punkt, promien, rowery);
			String response = request.getFromUrl(url, true);
			// Log.d(DatabaseManager.DEBUG_TAG, response);
			LatLng[] lista = parseResponse(response);
			// Log.d(DatabaseManager.DEBUG_TAG, Integer.toString(lista.length));
			if (lista == null || lista.length == 0)
				promien += 1000;
			else if (lista.length == 1)
				return lista[0];
			else
				return znajdzNajblizsza(lista, punkt);
		}
		return null;

	}

	private LatLng znajdzNajblizsza(LatLng[] lista, LatLng punkt) {
		LatLng najblizsza = null;
		float najlepsza = 5000f;
		for (LatLng elem : lista) {
			float temp = calcDistance(elem, punkt);
			if (temp < najlepsza) {
				najlepsza = temp;
				najblizsza = elem;

			}
		}
		return najblizsza;
	}

	private String createUrl(LatLng latLng, int promien, boolean rowery) {
		String url = urlVeturillo + "?circle=" + latLng.longitude + ","
				+ latLng.latitude + "," + promien;

		if (rowery) {
			try {
				url += "&filter=";
				url += URLEncoder
						.encode("<Filter><PropertyIsNotEqualTo><PropertyName>ROWERY</PropertyName><Literal>0</Literal></PropertyIsNotEqualTo></Filter>",
								"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return url;

	}

	private String createUrlGoogle(LatLng origin, LatLng destination) {
		return "http://maps.googleapis.com/maps/api/directions/json?mode=bicycling&origin="
				+ origin.latitude
				+ ","
				+ origin.longitude
				+ "&destination="
				+ destination.latitude
				+ ","
				+ destination.longitude
				+ "&sensor=false";
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

	private LatLng[] parseResponse(String response) {
		try {
			if (response == null || response.equals("null"))
				return null;

			JSONObject json = new JSONObject(response);

			JSONArray data = null;
			try {
				data = json.getJSONArray("data");
			} catch (JSONException e) {
				Log.d(DatabaseManager.DEBUG_TAG, "Vetrurillo - 1 wynik");
				JSONObject coord = json.getJSONObject("data")
						.getJSONObject("geometry").getJSONObject("coordinates");
				LatLng[] listPoj = { new LatLng(coord.getDouble("lat"),
						coord.getDouble("lon")) };
				return listPoj;
			}
			LatLng[] list = new LatLng[data.length()];
			for (int i = 0; i < data.length(); i++) {
				JSONObject coord = data.getJSONObject(i)
						.getJSONObject("geometry").getJSONObject("coordinates");
				double lat = coord.getDouble("lat");
				double lon = coord.getDouble("lon");
				list[i] = new LatLng(lat, lon);
			}

			return list;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<LatLng> parseResponseGoogle(String response) {
		try {
			if (response == null || response.equals("null"))
				return null;

			JSONObject result = new JSONObject(response);
			JSONArray legs = result.getJSONArray("routes").getJSONObject(0)
					.getJSONArray("legs");

			distance = legs.getJSONObject(0).getJSONObject("distance")
					.getString("text");
			time = legs.getJSONObject(0).getJSONObject("duration")
					.getString("text");
			time = time.substring(0, time.length() - 1);

			Log.d(DatabaseManager.DEBUG_TAG, time);
			Log.d(DatabaseManager.DEBUG_TAG, distance);
			JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");

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

	public boolean isVeturilloIsReady() {
		return veturilloIsReady;
	}

	public void setVeturilloIsReady(boolean veturilloIsReady) {
		this.veturilloIsReady = veturilloIsReady;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public LatLng getVetOrigin() {
		return vetOrigin;
	}

	public void setVetOrigin(LatLng vetOrigin) {
		this.vetOrigin = vetOrigin;
	}

	public LatLng getVetDest() {
		return vetDest;
	}

	public void setVetDest(LatLng vetDest) {
		this.vetDest = vetDest;
	}

}
