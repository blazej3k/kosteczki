package Dziecioly.zkimnabasen.baza.model;

import Dziecioly.zkimnabasen.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class List_Custom_ListaWydarzen extends ArrayAdapter<RowBeanListaWyd> {

	Context context;
    int layoutResourceId;  
    RowBeanListaWyd data[] = null;
	
	public List_Custom_ListaWydarzen(Context context, int layoutResourceId, RowBeanListaWyd[] data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.data = data;
	}
	
	public int getCount() {
		return data.length;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderPattern view_holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.view_row_item_lista_wydarzen, parent, false);

			view_holder = new ViewHolderPattern();
			view_holder.tekst_w_layoucie = (TextView) convertView.findViewById(R.id.txtTitle);
			view_holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
			view_holder.data = (TextView) convertView.findViewById(R.id.txtData);
			
			convertView.setTag(view_holder);
		}
		else {
			view_holder = (ViewHolderPattern) convertView.getTag();
		} 

		RowBeanListaWyd object = data[position];
		view_holder.tekst_w_layoucie.setText(object.getTekst());
		view_holder.imgIcon.setImageResource(object.getIcon());
		view_holder.data.setText(object.getData());
		
		return convertView;
	}
	
	private class ViewHolderPattern {
		TextView tekst_w_layoucie;
		TextView data;
		ImageView imgIcon;
	}
	
/*	public Object getItem(int position) {
	return null;
}

@Override
public long getItemId(int position) {
	return 0;
}*/
	
}
