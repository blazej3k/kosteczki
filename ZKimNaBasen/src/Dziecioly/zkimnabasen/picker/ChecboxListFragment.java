package Dziecioly.zkimnabasen.picker;

import java.util.ArrayList;
import java.util.List;

import Dziecioly.zkimnabasen.baza.dao.UzytkownikDao;
import Dziecioly.zkimnabasen.baza.model.Uzytkownik;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ChecboxListFragment extends DialogFragment {

	private UzytkownikDao uzytkownikDao = new UzytkownikDao();
	private List<Integer> mSelectedItems;
	NoticeDialogListener mListener;
	
	

	public List<Integer> getmSelectedItems() {
		return mSelectedItems;
	}

	public void setmSelectedItems(List<Integer> mSelectedItems) {
		this.mSelectedItems = mSelectedItems;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		mSelectedItems = new ArrayList<Integer>();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Wybierz znajomych");

		builder.setMultiChoiceItems(znajomi(), null,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						if (isChecked) {
							mSelectedItems.add(which);
						} else if (mSelectedItems.contains(which)) {
							mSelectedItems.remove(Integer.valueOf(which));
						}
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

	public CharSequence[] znajomi() {
		List<Uzytkownik> lista = uzytkownikDao.list();
		List<String> nazwy = new ArrayList<String>();
		for (Uzytkownik uzytkownik : lista)
			nazwy.add(uzytkownik.getNazwa());

		final CharSequence[] charSequence = nazwy
				.toArray(new CharSequence[nazwy.size()]);

		return charSequence;

	}
}