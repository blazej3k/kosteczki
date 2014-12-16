package Dziecioly.zkimnabasen.activity;

import Dziecioly.zkimnabasen.R;
import Dziecioly.zkimnabasen.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
        
        prosta_lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					Intent startActivityCustomList = new Intent(WydarzeniaLista.this, View_List_ListaWydarzen.class);
					startActivity(startActivityCustomList);
					break;
				}
				
			}
        	
		});
	}
}
