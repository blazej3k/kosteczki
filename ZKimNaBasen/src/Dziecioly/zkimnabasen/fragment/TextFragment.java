package Dziecioly.zkimnabasen.fragment;

import Dziecioly.zkimnabasen.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class TextFragment extends DialogFragment {

	private NoticeDialogListener mListener;

	private String adres;
	private String opis;
	private EditText editText;

	public TextFragment(String adres) {
		this.adres = adres;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(adres);

		LayoutInflater linf = LayoutInflater.from(getActivity());
		final View inflator = linf.inflate(R.layout.dialog, null);
		builder.setView(inflator);

		editText = (EditText) inflator.findViewById(R.id.editTextDialog);

		builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				opis = editText.getText().toString();
				mListener.onTextDialogPositiveClick(TextFragment.this);
			}
		});

		builder.setNeutralButton("Nie", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				opis = editText.getText().toString();
				mListener.onTextDialogNeutralClick(TextFragment.this);
			}
		});

		builder.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						mListener.onTextDialogNegativeClick(TextFragment.this);
					}
				});

		return builder.create();

	}

	public interface NoticeDialogListener {
		public void onTextDialogPositiveClick(DialogFragment dialog);

		public void onTextDialogNeutralClick(DialogFragment dialog);

		public void onTextDialogNegativeClick(DialogFragment dialog);
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

	public String getOpis() {
		return opis;
	}

}
