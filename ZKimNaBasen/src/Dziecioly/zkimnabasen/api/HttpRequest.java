package Dziecioly.zkimnabasen.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Base64;

public class HttpRequest {

	private String username = "48514168764";
	private String password = "NhmQ8cUyZdksr5S";
	
	private String getB64Auth(String login, String pass) {
		String source = login + ":" + pass;
		String ret = "Basic "
				+ Base64.encodeToString(source.getBytes(), Base64.URL_SAFE
						| Base64.NO_WRAP);
		return ret;
	}



	public String getFromUrl(String url, boolean auth) {
		final HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
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
