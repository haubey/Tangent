package com.haubey.tangent;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PreBuiltFunctionList extends ListFragment {
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		String[] values = {"sin(x)", "cos(x)", "e^x", "x^2"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView list, View view, int position, long id) {
		Log.d("G", "Commencing graphing");
		getActivity().startActivity(new Intent(getActivity(), Grapher.class).putExtra("function", getListAdapter().getItem(position).toString()));
	}
	
}
