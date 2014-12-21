package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Rejestracja extends ActionBarActivity {

	private Context context;
	private EditText nick;
	private EditText numerTel;
	private EditText haslo;
	private EditText powtorzHaslo;
	private Button zarejestruj;

	UzytkownikDao uzytkownikDao = new UzytkownikDao();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rejestracja);
		context = getApplicationContext();

		nick = (EditText) findViewById(R.id.nick);
		numerTel = (EditText) findViewById(R.id.numerTel);
		haslo = (EditText) findViewById(R.id.podajHaslo);
		powtorzHaslo = (EditText) findViewById(R.id.powtorzHaslo);
		zarejestruj = (Button) findViewById(R.id.zarejestruj);

		zarejestruj.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				zarejestruj();
			}
		});

	}

	private void zarejestruj() {
		String u_nick = nick.getText().toString();
		String u_numerTel = numerTel.getText().toString();
		String u_haslo = haslo.getText().toString();
		String u_powtorzHaslo = powtorzHaslo.getText().toString();

		if (!u_haslo.equals(u_powtorzHaslo))
			Toast.makeText(context, "Podaj takie samo has³o", Toast.LENGTH_LONG)
					.show();
		else {
			Uzytkownik u = new Uzytkownik(u_nick, u_numerTel, u_haslo);
			int result = uzytkownikDao.add(u);
			if (result == 0)
				Toast.makeText(context, "U¿ytkownik istnieje",
						Toast.LENGTH_LONG).show();
			else {
				SharedPreferences pref = context.getSharedPreferences("MyPref",
						0);
				Editor editor = pref.edit();
				editor.clear();
				editor.putString("loggedIn", u_nick);
				editor.commit();

				Intent intent = new Intent(context, MojKalendarz.class);
				startActivity(intent);
			}
		}

	}
}
