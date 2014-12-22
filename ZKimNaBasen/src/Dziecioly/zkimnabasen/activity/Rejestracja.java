package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Rejestracja extends Activity {

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
		if (u_nick == null || u_nick.equals("") || u_numerTel == null
				|| u_numerTel.equals("") || u_haslo == null
				|| u_haslo.equals("") || u_powtorzHaslo == null
				|| u_powtorzHaslo.equals("")) {
			Toast.makeText(context, "Wype³nij wszystkie pola",
					Toast.LENGTH_LONG).show();
			return;

		}

		if (!u_haslo.equals(u_powtorzHaslo)) {
			Toast.makeText(context, "Podaj takie samo has³o", Toast.LENGTH_LONG)
					.show();
			return;
		}
		Uzytkownik u = new Uzytkownik(u_nick, u_numerTel, u_haslo);
		Uzytkownik ur = uzytkownikDao.add(u);
		if (ur == null)
			Toast.makeText(context, "U¿ytkownik istnieje", Toast.LENGTH_LONG)
					.show();
		else {
			Intent intent = new Intent(context, Logowanie.class);
			startActivity(intent);
		}

	}
}
