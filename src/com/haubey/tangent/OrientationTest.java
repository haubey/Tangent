package com.haubey.tangent;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

/**
 * @author Descartes Holland
 * 
 * New Activity for testing the orientation sensor, specifically to obtain
 * the angle above the horizontal that the phone is being held at, by using
 * the new (non-deprecated) methods from the Android API.
 * 
 * This Activity combines raw data from the magnetometer and accelerometer 
 * to get orientation data by calculating a 'rotation matrix'.
 */
public class OrientationTest extends Activity implements SensorEventListener
{
	TextView textView;
	StringBuilder builder = new StringBuilder();
	
	SensorManager sMgr;
	Sensor accelerometer;
	Sensor magField;
	
	float[] magFieldData;			//hold raw data from magnetic field sensor
	float[] accData;				//hold raw data from accelerometer
	float[] rotMat = new float[16];	//Rotation Matrix
	float[] orientationValues = new float[3];	 //hold the computed orientation values (azimuth (yaw), pitch, roll) in rads
	
	//Exponential Smoothing variables
	private static float ALPHA;
	float s;
	boolean init; //whether an initial guess for s has been obtained
	
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//Change to landscape:
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		textView = new TextView(this);
		setContentView(textView);
		
		s = 0;
		init = false;
		magFieldData = new float[3];
		accData = new float[3];
		
		//Setup:
		sMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = (Sensor) sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magField = (Sensor) sMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		
		//Use Magnetic Field sensor in combination with Accelerometer to determine the Rotation Matrix:
		sMgr.registerListener(this, magField, SensorManager.SENSOR_DELAY_NORMAL);
		sMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	/**
	 * Takes a sensor event, identifies the type of sensor it came from, and puts
	 * the raw data into the respective array.
	 */
	@Override
	public void onSensorChanged(SensorEvent event)
	{ 
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			System.arraycopy(event.values, 0, accData, 0, 3);
		
		if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			System.arraycopy(event.values, 0, magFieldData, 0, 3);
		
		reOrient(); //Recalculate orientation
		
		//printRawSensorData(); //Debugging
	}
	
	/**
	 * Uses the TextView to display the accelerometer and magnetometer raw data.
	 */
	@SuppressWarnings("unused")
	private void printRawSensorData()
	{
		builder.setLength(0);
		builder.append("Accelerometer: "+accData[0]+" "+accData[1]+" "+accData[2]+"\n");
		builder.append("Magnetometer: "+magFieldData[0]+" "+magFieldData[1]+" "+magFieldData[2]+"\n");
		textView.setText(builder.toString());	
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
//			float azi = orientationValues[0];
//			float roll = orientationValues[2];
//			textView.setText("Azimuth: "+(azi)+"\n"+" "+(pitch)+"\n "+roll);
			
			textView.setText("Degrees above horizontal: "+(Math.round(Math.toDegrees(pitch)*100.)/100.)+"\n"+String.valueOf(Math.round(s*100.)/100.));
		}
	}

	protected void onPause()
	{
		super.onPause();
		sMgr.registerListener(this, magField, SensorManager.SENSOR_DELAY_NORMAL);
		sMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	protected void onResume()
	{
		super.onResume();
		sMgr.registerListener(this, magField, SensorManager.SENSOR_DELAY_NORMAL);
		sMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1)
	{
	}
}
