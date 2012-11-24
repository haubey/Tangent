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
 * Tests the orientation sensor of the phone to determine it's orientation.
 * 
 * No longer needed as of 9/28/2012 because the method used to determine the Azimuth
 * (the angle of rotation around Z-axis) is not very accurate and uses deprecated
 * methods.
 */
public class OldOrientationTest extends Activity implements SensorEventListener
{
	private SensorManager manager;
	private Sensor orient;
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	TextView textView;
	StringBuilder builder = new StringBuilder();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		setContentView(textView);
		
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		orient = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event)
	{
		//All values in degrees
		double azi = event.values[0]; //0 degrees when top of phone (y-axis) faces north
		double pitch = event.values[1];
		double roll = event.values[2];
		
		
		//Print:
		builder.setLength(0);
		builder.append("Azimuth: "+azi+"\n");
		builder.append("Pitch: "+pitch+"\n");
		builder.append("Roll: "+roll);
		
		textView.setText(builder.toString());
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		manager.registerListener(this, orient, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		manager.registerListener(this, orient, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1)
	{
		//Nothing
	}
}
