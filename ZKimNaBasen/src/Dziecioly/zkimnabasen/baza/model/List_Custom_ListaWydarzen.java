package Dziecioly.zkimnabasen.baza.model;

import Dziecioly.zkimnabasen.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class List_Custom_ListaWydarzen extends BaseAdapter {

	private String[] data;
	private Context context;
	
	public List_Custom_ListaWydarzen(String[] importeddata, Context context) {
		this.context = context;
		this.data = importeddata;
	}
	
	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolderPattern {
		TextView tekst_w_layoucie;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderPattern view_holder;
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_view_lista_wydarzen, parent, false);
		
		view_holder = new ViewHolderPattern();
		view_holder.tekst_w_layoucie = (TextView) convertView.findViewById(R.id.textView_item_custom);
		
		convertView.setTag(view_holder);
		}
		else {
			view_holder = (ViewHolderPattern) convertView.getTag();
		} 
		
		view_holder.tekst_w_layoucie.setText(data[position]);

		return convertView;
	}
	

}
