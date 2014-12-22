package Dziecioly.zkimnabasen.fragment;

import java.util.List;

import Dziecioly.zkimnabasen.activity.WydarzeniaLista;
import Dziecioly.zkimnabasen.baza.dao.LokalizacjaDao;
import Dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import Dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import Dziecioly.zkimnabasen.baza.model.Lokalizacja;
import Dziecioly.zkimnabasen.baza.model.Wydarzenie;
import Dziecioly.zkimnabasen.baza.model.Zaproszenie;
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
		builder.setMessage("Czy na pewno chcesz usun�c to wydarzenie?")
				.setPositiveButton("Tak",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								usun(id_wydarzenia);
								Intent intent = new Intent(getActivity(), WydarzeniaLista.class);
								startActivity(intent);
								Toast.makeText(getActivity(), "Wydarzenie usuni�te", Toast.LENGTH_SHORT).show();
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
	
	
	private void usun(int id_wyd) {
		WydarzenieDao wydarzenieDao = new WydarzenieDao();
		LokalizacjaDao lokalizacjaDao = new LokalizacjaDao();
		ZaproszenieDao zaproszenieDao = new ZaproszenieDao();
		
		Wydarzenie w = wydarzenieDao.find(id_wyd);
		Lokalizacja lok = w.getLokalizacja();
		if (lok != null && !lok.isPubliczna())
			lokalizacjaDao.remove(lok);
		List<Zaproszenie> zaproszenia = w.getZaproszenia();
		if (zaproszenia != null)
			for (Zaproszenie z : zaproszenia)
				zaproszenieDao.remove(z);
		wydarzenieDao.remove(w);
	}

}
