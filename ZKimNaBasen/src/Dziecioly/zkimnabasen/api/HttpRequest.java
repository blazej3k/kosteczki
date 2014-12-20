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
import Dziecioly.zkimnabasen.activity.Mapa2;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class HttpRequest {

	private String username = "48514168764";
	private String password = "NhmQ8cUyZdksr5S";
	private Mapa2 mapa;
	
	
	public HttpRequest() {
		// TODO Auto-generated constructor stub
	}

	public HttpRequest(Mapa2 mapa) {
		this.mapa = mapa;
	}

	private String getB64Auth(String login, String pass) {
		String source = login + ":" + pass;
		String ret = "Basic "
				+ Base64.encodeToString(source.getBytes(), Base64.URL_SAFE
						| Base64.NO_WRAP);
		return ret;
	}



	public String getFromUrl(String url, boolean auth) {
		MyHttpClient httpclient = new MyHttpClient();
		HttpResponse response;
		String responseString = null;
		try {
			HttpGet get = new HttpGet(url);
			if (auth)
				get.addHeader("Authorization", getB64Auth(username, password));

			response = httpclient.execute(get);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				Log.d(DatabaseManager.DEBUG_TAG, responseString);
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

}
