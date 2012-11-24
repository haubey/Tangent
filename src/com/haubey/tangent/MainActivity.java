package com.haubey.tangent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
//Can also use a ButtonHandler to be responsible for the button
//	private class ButtonHandler implements View.OnClickListener
//	{
//		public void onClick(View v)
//		{
//			handleButtonClick();
//		}	
//	}
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
//		 Button b = new Button(this);
//	        b.setText("Start Activity");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	public void onButtonClick(View view)
	{
		startActivity(new Intent(this, OrientationTest.class));
	}

	public void goToInput(View view)
	{
		startActivity(new Intent(this, EnterFunction.class));
	}
	
	public void enterFunctionButton(View view)
	{
		startActivity(new Intent(this, EnterFunction.class));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
