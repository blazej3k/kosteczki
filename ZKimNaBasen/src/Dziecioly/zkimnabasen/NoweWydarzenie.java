package Dziecioly.zkimnabasen;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class NoweWydarzenie extends FragmentActivity implements OnDateSetListener {

	private Context context;
	private EditText nazwa;
	private EditText lokalizacja;
	private Button data;
	private Button godzinaRozpoczecia;
	private Button godzinaZakonczenia;
	private Button zaprosZnajomych;
	private Button zapisz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.nowe_wydarzenie);

		nazwa = (EditText) findViewById(R.id.nazwa);
		lokalizacja = (EditText) findViewById(R.id.lokalizacja);
		data = (Button) findViewById(R.id.data);
		godzinaRozpoczecia = (Button) findViewById(R.id.godzinaRozpoczecia);
		godzinaZakonczenia = (Button) findViewById(R.id.godzinaZakonczenia);
		zaprosZnajomych = (Button) findViewById(R.id.zaprosZnajomych);
		zapisz = (Button) findViewById(R.id.zapisz);
		
		
		zapisz.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				zapisz();
			}
		});
		
		
		
		data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 DatePickerFragment date = new DatePickerFragment();
				  date.show(getSupportFragmentManager(), "Date Picker");
			}
		});
	
	}
	

	private void zapisz()
	{
		Intent intent = new Intent(context, MainActivity.class);
		
		
		String nazwaString = nazwa.getText().toString();
		String lokalizacjaString = lokalizacja.getText().toString();
		
		intent.putExtra("nazwa", nazwaString);
		intent.putExtra("lokalizacja", lokalizacjaString);

		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nowe_wydarzenie, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onDateSet(DatePicker view, int year, int month,
			int day) {
		String dateText = day+"-"+String.valueOf(month+1)+"-"+year;
		data.setText(dateText);
		
	}
}
