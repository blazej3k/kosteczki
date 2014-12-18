package Dziecioly.zkimnabasen.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import Dziecioly.zkimnabasen.activity.Mapa;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class HttpRequest extends AsyncTask<String, String, String> {

	private String username = "48514168764";
	private String password = "NhmQ8cUyZdksr5S";
	private Mapa mapa;
	
	public HttpRequest(Mapa mapa) {
		this.mapa =  mapa;
	}

	@Override
	protected String doInBackground(String... uri) {
		MyHttpClient httpclient = new MyHttpClient();
		HttpResponse response;
		String responseString = null;
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
				parseResponse(responseString);
				
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
		return responseString;
	}
	
	private void parseResponse(String responseString)
	{
		try {
			JSONObject json = new JSONObject(responseString);
			JSONObject coord = json.getJSONObject("data").getJSONObject("geometry").getJSONObject("coordinates");
			JSONObject prop = json.getJSONObject("data").getJSONObject("properties");
			
			String adres = prop.getString("ulica")+" "+prop.getString("numer");
			String opis = prop.getString("opis");
			String lat = coord.getString("lat");
			String lon = coord.getString("lon");
			
			Log.d(DatabaseManager.DEBUG_TAG, lat);
			Log.d(DatabaseManager.DEBUG_TAG, lon);
			Log.d(DatabaseManager.DEBUG_TAG, adres);
			Log.d(DatabaseManager.DEBUG_TAG, opis);
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String getB64Auth(String login, String pass) {
		String source = login + ":" + pass;
		String ret = "Basic "
				+ Base64.encodeToString(source.getBytes(), Base64.URL_SAFE
						| Base64.NO_WRAP);
		return ret;
	}
	
	

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mapa.addMarkers(result);
	}
}
