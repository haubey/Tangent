package com.haubey.tangent;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

public  class EnterFunction extends Fragment {
        public EnterFunction() {
        }

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
        		      Context.INPUT_METHOD_SERVICE);
        		imm.hideSoftInputFromWindow(container.getWindowToken(), 0);
        		
        	return inflater.inflate(R.layout.custom_function, container, false);
        }
     
}
    