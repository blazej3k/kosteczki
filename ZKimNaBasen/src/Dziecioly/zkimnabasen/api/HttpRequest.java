package Dziecioly.zkimnabasen.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Dziecioly.zkimnabasen.activity.Mapa;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import android.os.AsyncTask;
import android.util.Base64;

public class HttpRequest extends AsyncTask<String, String, List<Lokalizacja>> {

	private String username = "48514168764";
	private String password = "NhmQ8cUyZdksr5S";
	private Mapa mapa;
	private boolean listIsReady;

	public HttpRequest(Mapa mapa) {
		this.mapa = mapa;
	}
	

	@Override
	protected List<Lokalizacja> doInBackground(String... uri) {
		listIsReady = false;
		MyHttpClient httpclient = new MyHttpClient();
		HttpResponse response;
		String responseString = null;
		List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();
		try {
			HttpGet get = new HttpGet(uri[0]);
			get.addHeader("Authorization", getB64Auth(username, password));

			response = httpclient.execute(get);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				lokalizacje = parseResponse(responseString);
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lokalizacje;
	}

	private List<Lokalizacja> parseResponse(String responseString) {
		try {
			JSONObject json = new JSONObject(responseString);
			JSONArray data = json.getJSONArray("data");
			List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();

			for (int i = 0; i < data.length(); i++) {
				JSONObject coord = data.getJSONObject(i)
						.getJSONObject("geometry").getJSONObject("coordinates");
				double lat = coord.getDouble("lat");
				double lon = coord.getDouble("lon");

				JSONArray prop = data.getJSONObject(i).getJSONArray(
						"properties");

				String ulica = prop.getJSONObject(0).getString("value");
				String numer = prop.getJSONObject(1).getString("value");
				String opis = prop.getJSONObject(2).getString("value");

				String adres = ulica + " " + numer;

				Lokalizacja l = new Lokalizacja(lat, lon, adres, opis, false, Lokalizacja.kategorie[7]);
				lokalizacje.add(l);

			}
			return lokalizacje;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getB64Auth(String login, String pass) {
		String source = login + ":" + pass;
		String ret = "Basic "
				+ Base64.encodeToString(source.getBytes(), Base64.URL_SAFE
						| Base64.NO_WRAP);
		return ret;
	}

	@Override
	protected void onPostExecute(List<Lokalizacja> result) {
		super.onPostExecute(result);
		mapa.setLokalizacje(result);
		listIsReady = true;
	}
	
	public boolean isListIsReady() {
		return listIsReady;
	}


	public void setListIsReady(boolean listIsReady) {
		this.listIsReady = listIsReady;
	}

}
