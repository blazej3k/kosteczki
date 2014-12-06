package Dziecioly.zkimnabasen;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

public class TimePickerFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// use the current time as the default values for the picker
		// final Calendar c = Calendar.getInstance();
		int hour = 0;
		int minute = 0;

		// create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(),
				(OnTimeSetListener) getActivity(), hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

}
