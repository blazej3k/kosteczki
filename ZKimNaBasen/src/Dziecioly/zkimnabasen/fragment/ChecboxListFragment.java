package Dziecioly.zkimnabasen.fragment;

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

	private NoticeDialogListener mListener;

	private String title;
	private CharSequence[] items;
	private boolean[] checkedItems;

	public ChecboxListFragment(String title, CharSequence[] items, boolean[] checkedItems) {
		this.title = title;
		this.items = items;
		this.checkedItems = checkedItems;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title);


		builder.setMultiChoiceItems(items, checkedItems,
				new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which,
					boolean isChecked) {
				if (isChecked) {
					checkedItems[which] = true;
				}
			}
		});

		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onDialogPositiveClick(ChecboxListFragment.this);
			}
		});

		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				mListener.onDialogNegativeClick(ChecboxListFragment.this);

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

	public CharSequence[] getItems() {
		return items;
	}

	public void setItems(CharSequence[] items) {
		this.items = items;
	}


	public boolean[] getCheckedItems() {
		return checkedItems;
	}


	public void setCheckedItems(boolean[] checkedItems) {
		this.checkedItems = checkedItems;
	}


	public void setItems(List<Uzytkownik> lista) {
		List<String> nazwy = new ArrayList<String>();
		for (Uzytkownik uzytkownik : lista)
			nazwy.add(uzytkownik.getNazwa());
		this.items = nazwy.toArray(new CharSequence[nazwy.size()]);
	}
}
