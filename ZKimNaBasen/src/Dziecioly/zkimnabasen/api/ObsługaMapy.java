package Dziecioly.zkimnabasen.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

public class ObsługaMapy {

	HttpRequest request = new HttpRequest();

	public ObsługaMapy(Context context) {
	}

	public String pobierzAdres(LatLng arg) {
		String url = stworzUrl(arg);
		String response = request.getFromUrl(url, false);
		return getAdressFromResponse(response);
	}

	public String[] pobierzMarker(String arg) {
		String url = stworzUrl(arg);
		String response = request.getFromUrl(url, false);

		String[] latLng = getLatLngFromResponse(response);
		if (latLng == null)
			return null;
		String adres = getAdressFromResponse(response);
		String[] ret = new String[3];
		ret[0] = latLng[0];
		ret[1] = latLng[1];
		ret[2] = adres;
		return ret;
	}

	private String getAdressFromResponse(String response) {
		try {
			if (response == null)
				return null;
			JSONObject json = new JSONObject(response);
			String addr = json.getJSONArray("results").getJSONObject(0)
					.getString("formatted_address");
			String[] tab = addr.split(",");
			return tab[0];

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String[] getLatLngFromResponse(String response) {
		try {
			if (response == null)
				return null;
			JSONObject json = new JSONObject(response);
			JSONObject loc = json.getJSONArray("results").getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location");
			String lat = loc.getString("lat");
			String lon = loc.getString("lng");
			String[] latlon = { lat, lon };
			return latlon;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String stworzUrl(LatLng arg) {
		String geoUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
		geoUrl += arg.latitude;
		geoUrl += ",";
		geoUrl += arg.longitude;
		geoUrl += "&key=AIzaSyCDu59Epm8rTQAdgd2IfpEwnozCqbe98Rk";
		return geoUrl;
	}

	private String stworzUrl(String arg) {
		String geoUrl = null;
		try {
			geoUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=";
			geoUrl += URLEncoder.encode(arg, "UTF-8") + "Warszawa";
			geoUrl += "&key=AIzaSyCDu59Epm8rTQAdgd2IfpEwnozCqbe98Rk";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return geoUrl;

	}

	public List<LatLng> decodePolyline(String encoded) {

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
}
