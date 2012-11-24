package com.haubey.tangent;

import de.congrace.exp4j.ExpressionBuilder;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Descartes Holland
 * 
 * Testing activity for accepting a user-entered function (in infix notation).
 * An EditText object is linked to a button below. When pressed, the button displays a Toast
 * message with the text from the text box.
 */


public class EnterFunction extends Activity
{
	EditText functionTextBox; //The EditText object that will contain the function

	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input2);
		
		functionTextBox   = (EditText) findViewById(R.id.function_textbox);
	}		

	/**
	 * This method is run when the "Done" button below the textbox is clicked.
	 * It reads the text in the textbox and stores it as a string, which is then
	 * tested to see if the function is a valid expression (in terms of 1 variable,
	 * legal characters only)
	 * @param view
	 */
	public void acceptFunction(View view)
	{
		ExpressionBuilder exp = new ExpressionBuilder("2x+4");
		
		//Toast-print the value in the text field:
		Toast.makeText(getApplicationContext(), (functionTextBox.getText().toString()), Toast.LENGTH_SHORT).show();
		//	Integer.valueOf(mEdit.getText().toString()); //Will use this to send the text to another activity
	} 
	
	protected void onPause()
	{
		super.onPause();
	}
	
	protected void onResume()
	{
		super.onResume();
	}
}