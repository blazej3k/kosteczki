package dziecioly.zkimnabasen.fragment;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import dziecioly.zkimnabasen.activity.NoweWydarzenie;

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
