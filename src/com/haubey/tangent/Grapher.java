package com.haubey.tangent;

import com.android.graphbutton.plot2d;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Grapher extends Activity implements SensorEventListener
{
	int DEGREE_TOLERANCE = 10; //number of degrees difference acceptable
	
	String function_string = "";
	plot2d graphScreen;
	
	SensorManager sMgr;
	Sensor accelerometer;
	Sensor magField;
	
	int xValueCounter = 0;
	
	float[] magFieldData;			//hold raw data from magnetic field sensor
	float[] accData;				//hold raw data from accelerometer
	float[] rotMat = new float[16];	//Rotation Matrix
	float[] orientationValues = new float[3];	 //hold the computed orientation values (azimuth (yaw), pitch, roll) in rads
	
	float[] xvalues;
	float[] yvalues;
	
	//Exponential Smoothing variables
	private static float ALPHA;
	float s;
	boolean init; //whether an initial guess for s has been obtained
	Calculable function_calc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		LinearLayout buttonGraph = new LinearLayout(this);
		
		//Get string extra:
		Intent intent = getIntent();
		function_string = new String(intent.getStringExtra("function"));
		function_string = function_string.replace("e^x", "exp(x)");
		Log.d("G", "Function: "+function_string);
		
		//Set up graph:
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); //not sure about this
		
		xvalues = new float[1001]; //stores x values; 1001 is to allow dot to get to end of graph
		yvalues = new float[1001]; //stores y values
		float i = 0; //counter for acquiring values
		
		try {
			//Set up function as a Calculable:
			function_calc = new ExpressionBuilder(function_string).withVariable("x", i).build();
			
			//Fill arrays:
			Log.d("G", "Beginning array calculation");
			for(int j = 0; j < yvalues.length; j++)
			{
				xvalues[j] = i;
				yvalues[j] = (float) function_calc.calculate(i);
				i+=0.01;
			}
			Log.d("G", "Finished array calculation");
			
			Log.d("G", "Initializing graphscreen");
			graphScreen = new plot2d(this, xvalues, yvalues, 1);
			Log.d("G", "Graphscreen initialized");
			
			Log.d("G", "Invalidating");
			if(graphScreen.getVisibility()==View.VISIBLE) Log.d("G", "visible");
			else Log.d("G", "invisible");
			graphScreen.invalidate();
			
			buttonGraph.addView(graphScreen, 0);
			setContentView(buttonGraph);
			
			s = 0;
			init = false;
			magFieldData = new float[3];
			accData = new float[3];
			
			//Setup:
			sMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			accelerometer = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			magField = sMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
			
			Log.d("G", "Launching background thread");
			new HandleCircle().execute(""); //runs the AsyncTask
		}
		
		//These catch blocks should never be tripped
		catch (UnknownFunctionException e)
		{}
		catch (UnparsableExpressionException e)
		{} 
	}
	
	private double getDegreesAboveHorizontal()
	{
		rotMat = new float[16];		 //Rotation Matrix
		
		if(SensorManager.getRotationMatrix(rotMat, null, accData, magFieldData)) //enters only if the rotation matrix was properly created
		{ 
			SensorManager.getOrientation(rotMat, orientationValues);
			
			//Only interested in pitch (angle above horizontal):
			float pitch = orientationValues[1];
			Log.d("G", "Degrees: "+String.valueOf(Math.round(Math.toDegrees(pitch)*100.)/100.));
			return Math.round(Math.toDegrees(pitch)*100.)/100.;
		}
		Log.d("G", "returned 90");
		return 90;
	}
	
	private double getDerivative(int index) {
		double x1 = xvalues[index];
		double x2 = xvalues[index+1];
	
		double der =  ((function_calc.calculate(x2) - function_calc.calculate(x1)) / (x2-x1));
		
		Log.d("Derivative at " + index, String.valueOf(der));
		
		return der;
		
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
			sMgr.registerListener(Grapher.this, magField, SensorManager.SENSOR_DELAY_NORMAL);
			sMgr.registerListener(Grapher.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
			try {
				boolean advance = true;
				for(int i = 0; i < 200; i++) //Moves the dot from the point (0, f(0) to (100, f(100)).
				{
					Thread.sleep(100);
					float deg = (float) getDegreesAboveHorizontal();
					if(
							Math.abs(Math.toDegrees(Math.atan(getDerivative(xValueCounter)))-deg) <= DEGREE_TOLERANCE
						)
					{
						graphScreen = graphScreen.advanceCirc();
						graphScreen = graphScreen.setNextRotation(deg);
						graphScreen.postInvalidate();
						advance = true;
						xValueCounter++;
					}
					else advance = false;
					if(!advance)
						i--;
				}
			}
			catch (InterruptedException e){ }
			return "Executed";
		}      
		
		protected void onPostExecute(String result) {
//						graphScreen.invalidate(); //forces graphScreen to re-draw with the new coordinates of the circle
//						while(numTimesRun < 3)
//						{
//							new HandleCircle().execute("");
//							numTimesRun++;
//						}
		}
		
		@Override
		protected void onPreExecute() {
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}
	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
	
	
	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			System.arraycopy(event.values, 0, accData, 0, 3);
		
		if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			System.arraycopy(event.values, 0, magFieldData, 0, 3);
		
		reOrient(); //Recalculate orientation
		
	}
	
	private void reOrient()
	{
		rotMat = new float[16];		 //Rotation Matrix
		
		if(SensorManager.getRotationMatrix(rotMat, null, accData, magFieldData)) //enters only if the rotation matrix was properly created
		{ 
			SensorManager.getOrientation(rotMat, orientationValues);
			
			//Only interested in pitch (angle above horizontal):
			float pitch = orientationValues[1];
			
			//Exponential smoothing:
			if(!init)
			{
				init = true;
				s = (float) Math.toDegrees(orientationValues[1]);
			}
			s+= (float) (ALPHA*(Math.toDegrees(pitch)-s)); //exp. smoothing by the formula s_t = s_t-1 + alpha * (x_t-1 - s_t-1) | t > 1
		}
	}
}
