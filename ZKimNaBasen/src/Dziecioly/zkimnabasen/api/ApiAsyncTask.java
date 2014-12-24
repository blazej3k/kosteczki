package Dziecioly.zkimnabasen.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Dziecioly.zkimnabasen.activity.Mapa;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.LokalizacjaDao;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import android.os.AsyncTask;
import android.util.Log;

public class ApiAsyncTask extends AsyncTask<Void, String, List<Lokalizacja>> {

	private final String swimmingPoolsUrl = "https://api.bihapi.pl/wfs/warszawa/swimmingPools";
	private final String sportFieldsUrl = "https://api.bihapi.pl/wfs/warszawa/sportFields?";

	private boolean listIsReady;

	private String kategoria;
	private Mapa mapa;

	HttpRequest request = new HttpRequest();
	LokalizacjaDao lokalizacjaDao = new LokalizacjaDao();

	public ApiAsyncTask(Mapa mapa, String kategoria) {
		this.mapa = mapa;
		this.kategoria = kategoria;
	}

	@Override
	protected List<Lokalizacja> doInBackground(Void... params) {
		listIsReady = false;
		List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();

		Log.d(DatabaseManager.DEBUG_TAG, kategoria);
		if (kategoria.equals("P³ywalnia"))
			lokalizacje = pobierzLokalizacjeApi(swimmingPoolsUrl);
		else if (kategoria.equals("Boisko"))
			lokalizacje = pobierzLokalizacjeApi(sportFieldsUrl);
		if (lokalizacje == null) {
			lokalizacje = new ArrayList<Lokalizacja>();
		}

		List<Lokalizacja> lokalizacjeDb = pobierzLokalizacjeDb(kategoria);
		Log.d(DatabaseManager.DEBUG_TAG, " z db " + Integer.toString(lokalizacjeDb.size()));
		lokalizacje.addAll(lokalizacjeDb);

		return lokalizacje;
	}

	private List<Lokalizacja> pobierzLokalizacjeDb(String kateroria) {

		return lokalizacjaDao.pobierzPubliczne(kateroria);
	}

	private List<Lokalizacja> pobierzLokalizacjeApi(String url) {
		Log.d(DatabaseManager.DEBUG_TAG, "pobieram z api...");
		List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();
		String responseString = request.getFromUrl(url, true);
		lokalizacje = parseResponse(responseString);
		return lokalizacje;
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

				Lokalizacja l = new Lokalizacja(lat, lon, adres, opis, false,
						kategoria);
				lokalizacje.add(l);

			}
			return lokalizacje;

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

}
