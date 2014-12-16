package Dziecioly.zkimnabasen.picker;

import java.util.ArrayList;
import java.util.List;

import Dziecioly.zkimnabasen.baza.DatabaseManager;
import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;


public class ChecboxListFragment extends DialogFragment {

	private UzytkownikDao uzytkownikDao = new UzytkownikDao();
	private List<Integer> mSelectedItems;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		mSelectedItems = new ArrayList<Integer>();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Wybierz znajomych:");

		final CharSequence[] items = { ".NET", "J2EE", "PHP" };

		builder.setMultiChoiceItems(items,
				new boolean[] { false, false, false },
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						//itemsChecked[which] = isChecked;
					}
				});

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Log.d(DatabaseManager.DEBUG_TAG,
						Integer.toString(mSelectedItems.size()));
			}
		});

		builder.setNegativeButton("Analuj",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
					}
				});

		return builder.create();
	}

	public CharSequence[] znajomi() {
		List<Uzytkownik> lista = uzytkownikDao.list();
		List<String> nazwy = new ArrayList<String>();
		for (Uzytkownik uzytkownik : lista)
			nazwy.add(uzytkownik.getNazwa());
		/*
		 * CharSequence[] charSequence = nazwy .toArray(new
		 * CharSequence[nazwy.size()]);
		 */
		final CharSequence[] charSequence = { "Ola", "Kasia", "basia" };
		return charSequence;

	}
}
