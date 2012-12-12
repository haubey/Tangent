package com.haubey.tangent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//package com.haubey.tangent;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//
///**
// * @author Descartes Holland
// * 
// * Testing activity for accepting a user-entered function (in infix notation).
// * An EditText object is linked to a button below. When pressed, the button displays a Toast
// * message with the text from the text box.
// */
//
//
//public class EnterFunction extends Fragment
//{
//	EditText functionTextBox; //The EditText object that will contain the function
//	public static String function = "";
//	
//	
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		
//		functionTextBox = (EditText) getView().findViewById(R.id.function_textbox);
//		return inflater.inflate(R.layout.activity_input2, container, false);
//	}		
//	
////	/**
////	 * This method is run when the "Done" button below the textbox is clicked.
////	 * It reads the text in the textbox and stores it as a string, which is then
////	 * tested to see if the function is a valid expression (in terms of 1 variable,
////	 * legal characters only)
////	 * @param view
////	 */
////	public void acceptFunction(View view)
////	{
////		
////		try {
////			Calculable exp = new ExpressionBuilder(functionTextBox.getText().toString()).withVariable("x", 10).build();
////			exp.calculate();
////			function = functionTextBox.getText().toString();
////			
////			 Intent returnIntent = new Intent();
////			 returnIntent.putExtra("result",function);
////			 setResult(RESULT_OK, returnIntent);
//////			 setResult(RESULT_OK, new Intent().putExtra("functionString", function));   
////			 
////			finish();
////		}
////		catch(UnparsableExpressionException e)
////		{
////			Toast.makeText(getActivity(), "Invalid Function, try again", Toast.LENGTH_SHORT).show();
////		}
////		catch(UnknownFunctionException e)
////		{
////			Toast.makeText(getActivity(), "Invalid Function, try again", Toast.LENGTH_SHORT).show();
////		}
////		
////		
////		//Toast-print the value in the text field:
//////		Toast.makeText(getApplicationContext(), (functionTextBox.getText().toString()), Toast.LENGTH_SHORT).show();
////		//	Integer.valueOf(mEdit.getText().toString()); //Will use this to send the text to another activity
////	} 
//	
//	@Override
//	public void onPause()
//	{
//		super.onPause();
//	}
//	
//	@Override
//	public void onResume()
//	{
//		super.onResume();
//	}
//}

public  class EnterFunction extends Fragment {
        public EnterFunction() {
        }

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
//            TextView textView = new TextView(getActivity());
//            textView.setGravity(Gravity.CENTER);
//            Bundle args = getArguments();
//            textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER))+"Enter Function");
//            return textView;
        	return inflater.inflate(R.layout.activity_input2, container, false);
        }
        
        @SuppressLint("ShowToast")
		public void acceptFunction(View view) {
        	Toast.makeText(getActivity(), "Hello dolly", Toast.LENGTH_LONG);
        }
}
    