package Dziecioly.zkimnabasen.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.MenuItem;

public class General {

	public static void clearSharedPrefs(Context context) {
		SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();

		Intent intent = new Intent(context, Logowanie.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);

	}

	public static void clearData(Context context) {
		DatabaseManager.getInstance().upgrade(
				DatabaseManager.getInstance().getDb());
		Intent intent = new Intent(context, Logowanie.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static Date dateFromString(String dateInString) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		try {
			return formatter.parse(dateInString);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String stringFromDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		return formatter.format(date);
	}
	
	public static String loggedUser(Context context)
	{
		SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
		String login = pref.getString("loggedIn", "null");
		return login;
	}

}
