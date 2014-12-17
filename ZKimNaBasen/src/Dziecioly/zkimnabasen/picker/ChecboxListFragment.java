package Dziecioly.zkimnabasen.picker;

import java.util.ArrayList;
import java.util.List;

import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ChecboxListFragment extends DialogFragment {

	NoticeDialogListener mListener;

	private CharSequence[] wszyscyZnajomi;
	private boolean[] wybraniZnajomi;

	public CharSequence[] getWszyscyZnajomi() {
		return wszyscyZnajomi;
	}

	public void setWszyscyZnajomi(List<Uzytkownik> lista) {
		List<String> nazwy = new ArrayList<String>();
		for (Uzytkownik uzytkownik : lista)
			nazwy.add(uzytkownik.getNazwa());
		this.wszyscyZnajomi = nazwy.toArray(new CharSequence[nazwy.size()]);
	}

	public boolean[] getWybraniZnajomi() {
		return wybraniZnajomi;
	}

	public void setWybraniZnajomi(boolean[] wybraniZnajomi) {
		this.wybraniZnajomi = wybraniZnajomi;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Wybierz znajomych");

		builder.setMultiChoiceItems(wszyscyZnajomi, wybraniZnajomi,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						if (isChecked) {
							wybraniZnajomi[which] = true;
						} /*else if (wybraniZnajomi[which] == true) {
							wybraniZnajomi[which]
						}*/
					}
				});

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onDialogPositiveClick(ChecboxListFragment.this);

			}
		});

		builder.setNegativeButton("Analuj",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						mListener
								.onDialogNegativeClick(ChecboxListFragment.this);

					}
				});

		return builder.create();
	}

	public interface NoticeDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);

		public void onDialogNegativeClick(DialogFragment dialog);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (NoticeDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

}
