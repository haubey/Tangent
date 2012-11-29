package com.haubey.tangent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity
{
	String functionToGraph = "";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((Button) findViewById(R.id.button3)).getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
		
	}
	
	public void onButtonClick(View view)
	{
		startActivity(new Intent(this, OrientationTest.class));
	}
	
	/**
	 * Run when the Graph button is clicked.
	 * @param view
	 */
	public void graph(View view)
	{
		//Starts the Grapher activity with the String function passed as an extra
		startActivity(new Intent(this, Grapher.class).putExtra("function", functionToGraph));
	}
	
	/**
	 * Goes to the activity that allows the user to input a function.
	 * @param view
	 */
	public void goToInputActivity(View view)
	{
		//Using this method allows a Result to be expected from the activity. The requestCode 
		//of 1 was picked arbitrarily.
		startActivityForResult(new Intent(this, EnterFunction.class), 1);
	}
	
	/**
	 * After the Input Activity finishes, code flow enters this follow-up method.
	 * @param requestCode
	 * @param resultCode
	 * @param i
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent i)
	{
		if(requestCode == 1)
		{
			if(resultCode == RESULT_OK)
			{
				functionToGraph = new String(i.getStringExtra("result"));
//				Toast.makeText(getApplicationContext(), functionToGraph, Toast.LENGTH_LONG).show();
			}
		}
	}
	
}
