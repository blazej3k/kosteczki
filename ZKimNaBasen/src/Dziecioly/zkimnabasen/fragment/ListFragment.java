package Dziecioly.zkimnabasen.fragment;

import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class ListFragment extends DialogFragment {

	private NoticeDialogListener mListener;

	private String title;
	private String[] items;
	private String selectedItem;

	public ListFragment(String title, String[] items) {
		this.title = title;
		this.items = items;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title).setItems(items,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						selectedItem = items[which];
						mListener.onDialogClick(ListFragment.this);
					}
				});
		return builder.create();
	}

	public interface NoticeDialogListener {
		public void onDialogClick(DialogFragment dialog);

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

	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	

}
