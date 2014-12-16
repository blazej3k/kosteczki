package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WydarzeniaLista extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wydarzenia_lista);
		
        String[] elementy_listy = {"Element numer 1", "Drugi element", "Kutas!"};
        ListView prosta_lista = (ListView) findViewById(R.id.lv_prostalista);
        
        ArrayAdapter adapter_listy = new ArrayAdapter(this, android.R.layout.simple_list_item_1, elementy_listy);
        prosta_lista.setAdapter(adapter_listy);
	}
}
