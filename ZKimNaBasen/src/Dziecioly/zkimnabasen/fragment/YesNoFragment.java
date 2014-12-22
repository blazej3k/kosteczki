package Dziecioly.zkimnabasen.fragment;

import Dziecioly.zkimnabasen.activity.WydarzeniaLista;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class YesNoFragment extends DialogFragment {

	private int id_wydarzenia;

	public YesNoFragment(int id_wydarzenia) {
		this.id_wydarzenia = id_wydarzenia;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Czy na pewno chcesz usun¹c to wydarzenie?")
				.setPositiveButton("Tak",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								WydarzenieDao wydarzenieDao = new WydarzenieDao();
								Wydarzenie w = wydarzenieDao.find(id_wydarzenia);
								wydarzenieDao.remove(w);
								Intent intent = new Intent(getActivity(), WydarzeniaLista.class);
								startActivity(intent);
								Toast.makeText(getActivity(), "Wydarzenie usuniête", Toast.LENGTH_SHORT).show();
							}
						})
				.setNegativeButton("nie",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}

}
