package Dziecioly.zkimnabasen.fragment;

import java.util.Calendar;
import java.util.Date;

import Dziecioly.zkimnabasen.activity.General;
import Dziecioly.zkimnabasen.activity.NoweWydarzenie;
import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class DatePickerFragment extends DialogFragment {

	private int year;
	private int month;
	private int day;

	public DatePickerFragment(String data) {
		if (data.equals("Data"))
		{
			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
		}
		else {
				String[] i = data.split("\\.");
				day = Integer.parseInt(i[0]);
				month = Integer.parseInt(i[1])- 1;
				year = Integer.parseInt(i[2]);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(),
				(NoweWydarzenie) getActivity(), year, month, day);
	}
}
