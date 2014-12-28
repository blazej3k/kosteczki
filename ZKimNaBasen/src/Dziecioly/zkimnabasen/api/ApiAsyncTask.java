package Dziecioly.zkimnabasen.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.LokalizacjaDao;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import android.os.AsyncTask;
import android.util.Log;

public class ApiAsyncTask extends AsyncTask<Void, String, List<Lokalizacja>> {

	private AsyncListener listener;
	private final String swimmingPoolsUrl = "https://api.bihapi.pl/wfs/warszawa/swimmingPools";
	private final String sportFieldsUrl = "https://api.bihapi.pl/wfs/warszawa/sportFields?";
	private String kategoria;

	HttpRequest request = new HttpRequest();
	LokalizacjaDao lokalizacjaDao = new LokalizacjaDao();

	public ApiAsyncTask(AsyncListener listener, String kategoria) {
		this.kategoria = kategoria;
		this.listener = listener;
	}

	@Override
	protected List<Lokalizacja> doInBackground(Void... params) {
		List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();

		if (kategoria.equals("P³ywalnia"))
			lokalizacje = pobierzLokalizacjeApi(swimmingPoolsUrl);
		else if (kategoria.equals("Boisko"))
			lokalizacje = pobierzLokalizacjeApi(sportFieldsUrl);
		if (lokalizacje == null) {
			Log.d(DatabaseManager.DEBUG_TAG, "Brak API");
			lokalizacje = new ArrayList<Lokalizacja>();
		}

		List<Lokalizacja> lokalizacjeDb = pobierzLokalizacjeDb(kategoria);
		lokalizacje.addAll(lokalizacjeDb);

		return lokalizacje;
	}

	private List<Lokalizacja> pobierzLokalizacjeDb(String kateroria) {

		return lokalizacjaDao.pobierzPubliczne(kateroria);
	}

	private List<Lokalizacja> pobierzLokalizacjeApi(String url) {
		List<Lokalizacja> lokalizacje = new ArrayList<Lokalizacja>();
		String responseString = request.getFromUrl(url, true);
		lokalizacje = parseResponse(responseString);
		return lokalizacje;
	}

	@Override
	protected void onPostExecute(List<Lokalizacja> result) {
		super.onPostExecute(result);
		listener.doStuff(result);
	}

	private List<Lokalizacja> parseResponse(String responseString) {
		try {
			if (responseString == null || responseString.equals("null"))
				return null;
			
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
	
	
	public interface AsyncListener {
		public void doStuff(List<Lokalizacja> result);
	}
}
