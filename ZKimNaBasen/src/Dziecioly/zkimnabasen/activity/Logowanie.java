package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Logowanie extends ActionBarActivity {
	
	private Context context;
	private EditText login;
	private EditText haslo;
	private Button zaloguj;
	private Button utworzKonto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logowanie);
		context = getApplicationContext();
		
		login = (EditText) findViewById(R.id.login);
		haslo = (EditText) findViewById(R.id.haslo);
		zaloguj = (Button) findViewById(R.id.zaloguj);
		utworzKonto = (Button) findViewById(R.id.utworzKonto);
		
		zaloguj.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				zaloguj();
			}
		});
		
		utworzKonto.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				utworzKonto();
			}
		});
	}
	
	private void zaloguj()
	{
		String u_login = login.getText().toString();
		String u_haslo = haslo.getText().toString();
	}
	
	
	private void utworzKonto()
	{
		Intent intent = new Intent(context, Rejestracja.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.szczegoly_wydarzenia, menu);
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
}
