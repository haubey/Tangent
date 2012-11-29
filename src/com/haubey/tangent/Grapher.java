package com.haubey.tangent;

import com.android.graphbutton.plot2d;

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> cc471cba482f275880ada684e6b00aad8c804779
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
	
<<<<<<< HEAD
=======
	
>>>>>>> cc471cba482f275880ada684e6b00aad8c804779
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LinearLayout buttonGraph = new LinearLayout(this);
		buttonGraph.setOrientation(LinearLayout.VERTICAL);
		
		//Get string extra:
<<<<<<< HEAD
		function_string = new String(getIntent().getStringExtra("function"));
=======
		Intent intent = getIntent();
		function_string = new String(intent.getStringExtra("function"));
>>>>>>> cc471cba482f275880ada684e6b00aad8c804779
		
		//Set up graph:
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); //not sure about this
		
		float[] xvalues = new float[10]; //stores x values
		float[] yvalues = new float[10]; //stores y values
<<<<<<< HEAD
		int i = 0; //counter for acquiring values
		
=======
		int i = 0;//counter to be used for x values
		
//		Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
//		Toast.makeText(getApplicationContext(), function_string, Toast.LENGTH_SHORT).show();

>>>>>>> cc471cba482f275880ada684e6b00aad8c804779
		try {
			//Set up function as a Calculable:
			Calculable function_calc = new ExpressionBuilder(function_string).withVariable("x", i).build();
			
			//Fill arrays:
			for(i = 0; i < yvalues.length; i++)
			{
				xvalues[i] = i;
				yvalues[i] = (float) function_calc.calculate(i);
			}
			
<<<<<<< HEAD
			//Plot x and y:
=======
>>>>>>> cc471cba482f275880ada684e6b00aad8c804779
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
	
<<<<<<< HEAD
=======
=======
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Grapher extends Activity
{
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout buttonGraph = new LinearLayout(this);
        buttonGraph.setOrientation(LinearLayout.VERTICAL);
        
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); // Verbose!

        
        float[] xvalues = {1, 2, 3, 4, 5}; //new float[10];
        float[] yvalues = {2, 4, 6, 8, 10}; //new float[10];
        
        plot2d graph = new plot2d(this, xvalues, yvalues, 1);

        buttonGraph.addView(graph, lp);

        setContentView(buttonGraph);
        
    }
    
>>>>>>> c3e471fe1ac51031176e69281a7bff3cd7dcf8e0
>>>>>>> cc471cba482f275880ada684e6b00aad8c804779
	protected void onPause()
	{
		super.onPause();
	}
	
	protected void onResume()
	{
		super.onResume();
	}
}
