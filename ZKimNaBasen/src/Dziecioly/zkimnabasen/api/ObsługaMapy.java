package Dziecioly.zkimnabasen.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

public class Obs³ugaMapy {
	
	private Geocoder geocoder;
	
	public Obs³ugaMapy(Context context)
	{
		geocoder = new Geocoder(context);
	}

	public String pobierzAdres(LatLng arg) {
		try {
			Address a = geocoder
					.getFromLocation(arg.latitude, arg.longitude, 1).get(0);
			if (a == null)
				return null;
			return a.getAddressLine(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public LatLng pobierzMarker(String adres) {
		double[] lewyDolnyRog = { 52.116507, 20.870993 };
		double[] prawyGornyRog = { 52.365419, 21.208823 };

		LatLng latlon = null;
		try {
			List<Address> ad = geocoder.getFromLocationName(
					adres + " Warszawa", 1, lewyDolnyRog[0], lewyDolnyRog[1],
					prawyGornyRog[0], prawyGornyRog[1]);

			if (ad.size() == 0)
				return null;
			else {
				Address a = ad.get(0);
				latlon = new LatLng(a.getLatitude(), a.getLongitude());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return latlon;
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
