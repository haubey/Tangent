package com.haubey.tangent;

import com.android.graphbutton.plot2d;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Grapher extends Activity
{
	String function_string = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LinearLayout buttonGraph = new LinearLayout(this);
		buttonGraph.setOrientation(LinearLayout.VERTICAL);
		
		//Get string extra:
		function_string = new String(getIntent().getStringExtra("function"));
		
		//Set up graph:
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); //not sure about this
		
		float[] xvalues = new float[10]; //stores x values
		float[] yvalues = new float[10]; //stores y values
		int i = 0; //counter for acquiring values
		
		try {
			//Set up function as a Calculable:
			Calculable function_calc = new ExpressionBuilder(function_string).withVariable("x", i).build();
			
			//Fill arrays:
			for(i = 0; i < yvalues.length; i++)
			{
				xvalues[i] = i;
				yvalues[i] = (float) function_calc.calculate(i);
			}
			
			//Plot x and y:
			plot2d graph = new plot2d(this, xvalues, yvalues, 1);
			
			buttonGraph.addView(graph, lp);
			
			setContentView(buttonGraph);
		}
		
		//These catch blocks should never be tripped
		catch (UnknownFunctionException e)
		{}
		catch (UnparsableExpressionException e)
		{} 
		
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
