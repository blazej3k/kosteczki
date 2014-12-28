package dziecioly.zkimnabasen.fragment;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import dziecioly.zkimnabasen.activity.WydarzeniaLista;
import dziecioly.zkimnabasen.baza.dao.LokalizacjaDao;
import dziecioly.zkimnabasen.baza.dao.WydarzenieDao;
import dziecioly.zkimnabasen.baza.dao.ZaproszenieDao;
import dziecioly.zkimnabasen.baza.model.Lokalizacja;
import dziecioly.zkimnabasen.baza.model.Wydarzenie;
import dziecioly.zkimnabasen.baza.model.Zaproszenie;

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
								usun(id_wydarzenia);
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
