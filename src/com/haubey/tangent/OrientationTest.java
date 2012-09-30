/**
 * 
 */
package com.haubey.tangent;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

/**
 * @author Descartes Holland
 * New Activity for testing the orientation sensor, specifically to obtain
 * the angle above the horizontal that the phone is being held at, by using
 * the new (non-deprecated) methods from the Android API.
 */
public class OrientationTest extends Activity implements SensorEventListener
{
	SensorManager manager;
	Sensor acc;
	Sensor mag;
	
	float[] magFieldValues/* = new float[3]*/;		 //Will hold raw data from magnetic field sensor
	float[] accelerometerValues/* = new float[3]*/;	 //Will hold raw data from accelerometer
	float[] r = new float[16];		 //Rotation Matrix
	float[] i = new float[16];
	float[] orientationValues = new float[3];	 //Holds the computed orientation values (azimuth (yaw), pitch, roll) in rads
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	TextView textView;
	StringBuilder builder = new StringBuilder();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		setContentView(textView);
		
		magFieldValues = new float[3];
		accelerometerValues = new float[3];
		
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		acc = (Sensor) manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mag = (Sensor) manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		
		//Use Magnetic Field sensor in combination with Accelerometer to determine the Rotation Matrix:
		manager.registerListener(this, mag, SensorManager.SENSOR_DELAY_GAME);
		manager.registerListener(this, acc, SensorManager.SENSOR_DELAY_GAME);
		
		if(SensorManager.getRotationMatrix(r, i, accelerometerValues, magFieldValues))
		{ 
//			builder.append("Got in");
			SensorManager.getOrientation(r, orientationValues);
			float yaw = orientationValues[0] * 57.2957795f;
			builder.setLength(0);
			builder.append("Azimuth: "+yaw+"\n");
			textView.setText(builder.toString());
		}
		
		//		manager.getRotationMatrix(rotationMat, null , magFieldValues, accelerometerValues);
		//		//SensorManager.getRotationMatrix(rotationMat, null, accelerometerValues, magFieldValues); //stores values in rot. mat.
		//		//SensorManager.getOrientation(rotationMat, orientationValues);		//stores values in orientation array
		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event)
	{ 
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			System.arraycopy(event.values, 0, accelerometerValues, 0, 3);
		
		if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			System.arraycopy(event.values, 0, magFieldValues, 0, 3);
		
		reOrient();
		
//		builder.setLength(0);
//		builder.append("Accel: "+accelerometerValues[0]+" "+accelerometerValues[1]+" "+accelerometerValues[2]+"\n");
//		builder.append("Mag: "+magFieldValues[0]+" "+magFieldValues[1]+" "+magFieldValues[2]+"\n");
//		textView.setText(builder.toString());	
	}
	
	private void reOrient()
	{
		r = new float[16];		 //Rotation Matrix
		i = new float[16];
		
		if(SensorManager.getRotationMatrix(r, i, accelerometerValues, magFieldValues))
		{ 
			SensorManager.getOrientation(r, orientationValues);
//			float azi = orientationValues[0] * 57.2957795f;
			float pitch = orientationValues[1] * 57.2957795f;
//			float roll = orientationValues[2] * 57.2957795f;
			builder.setLength(0);
//			builder.append("Azimuth: "+(azi)+"\n"+" "+(pitch)+"\n "+roll);
			builder.append("Degrees above horizontal: "+/*pitch+"\n"+*/(Math.round(pitch*100.)/100.));
			textView.setText(builder.toString());
		}
	}

	protected void onPause()
	{
		super.onPause();
		manager.registerListener(this, mag, SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(this, acc, SensorManager.SENSOR_DELAY_GAME);
	}
	
	protected void onResume()
	{
		super.onResume();
		manager.registerListener(this, mag, SensorManager.SENSOR_DELAY_GAME);
		manager.registerListener(this, acc, SensorManager.SENSOR_DELAY_GAME);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1)
	{
		//Nothing
	}
}
