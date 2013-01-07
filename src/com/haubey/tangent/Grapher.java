package com.haubey.tangent;

import com.android.graphbutton.plot2d;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Grapher extends Activity
{
	String function_string = "";
	plot2d graphScreen;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		LinearLayout buttonGraph = new LinearLayout(this);
		buttonGraph.setOrientation(LinearLayout.HORIZONTAL);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//Get string extra:
		Intent intent = getIntent();
		function_string = new String(intent.getStringExtra("function"));
		function_string = function_string.replace("e^x", "exp(x)");
		
		//Set up graph:
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); //not sure about this
		
		float[] xvalues = new float[1000]; //stores x values
		float[] yvalues = new float[1000]; //stores y values
		float i = 0; //counter for acquiring values
		
		try {
			//Set up function as a Calculable:
			Calculable function_calc = new ExpressionBuilder(function_string).withVariable("x", i).build();
			
			//Fill arrays:
			for(int j = 0; j < yvalues.length; j++)
			{
				xvalues[j] = i;
				yvalues[j] = (float) function_calc.calculate(i);
				i+=0.01;
			}
			
			graphScreen = new plot2d(this, xvalues, yvalues, 1);
			
			buttonGraph.addView(graphScreen, 0);
			setContentView(buttonGraph);
			
			new HandleCircle().execute(""); //runs the AsyncTask
		}
		
		//These catch blocks should never be tripped
		catch (UnknownFunctionException e)
		{}
		catch (UnparsableExpressionException e)
		{} 
	}
	
	
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}
	
	
	private class HandleCircle extends AsyncTask<String, Void, String>
	{
		
		protected String doInBackground(String... params)
		{
			try {
				for(int i = 0; i < 100; i++) //Moves the dot from the point (0, f(0) to (100, f(100)).
				{
					Thread.sleep(100);
//					graphScreen = graphScreen.translateCirc(30, 30); //this changes the position of the circle
					graphScreen = graphScreen.advanceCirc();
					graphScreen.postInvalidate();
				}
			}
			catch (InterruptedException e)
			{}
			return "Executed";
		}      
		
		protected void onPostExecute(String result) {
			//			graphScreen.invalidate(); //forces graphScreen to re-draw with the new coordinates of the circle
			//			while(numTimesRun < 3)
			//			{
			//				new HandleCircle().execute("");
			//				numTimesRun++;
			//			}
		}
		
		@Override
		protected void onPreExecute() {
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}
}
