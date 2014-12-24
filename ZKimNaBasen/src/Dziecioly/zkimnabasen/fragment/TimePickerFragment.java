package Dziecioly.zkimnabasen.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

public class TimePickerFragment extends DialogFragment {

	int hour = 0;
	int minute = 0;

	public TimePickerFragment(String time) {
		if (time.equals("Godzina rozpoczêcia")
				|| time.equals("Godzina zakoñczenia")) {
			hour = 12;
			minute = 0;
		}
		else {
			String[] i = time.split(":");
			hour = Integer.parseInt(i[0]);
			minute = Integer.parseInt(i[1]);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(),
				(OnTimeSetListener) getActivity(), hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

}
