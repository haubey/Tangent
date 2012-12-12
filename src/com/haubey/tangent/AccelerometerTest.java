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
 * @author descartes.holland
 * Simple accelerometer-testing activity that prints the direction that the phone's accelerometer is registering.
 * Note:
 * Z direction is perpendicular to the screen of the phone no matter the orientation
 * Y direction is projected out of the top and bottom of the phone, bottom being where the touch-buttons are.
 * X direction is projected our of the sides of the phone, where your hands are when you hold it normally.
 */
public class AccelerometerTest extends Activity implements SensorEventListener
{
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	//Sensors.enableSensor(Sensors.SENSOR_ACCELEROMETER);
	TextView textView;
	StringBuilder builder = new StringBuilder();
	
	float[] locations = new float[3];
	String[] directions = {"NA", "NA", "NA"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_main);

		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		setContentView(textView);
		
		SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event)
	{
		//Get change in location (final - initial):
		float deltaX = event.values[0] - locations[0];
		float deltaY = event.values[1] - locations[1];
		float deltaZ = event.values[2] - locations[2];
		
		//Change initial location to current location:
		locations[0] = event.values[0];
		locations[1] = event.values[1];
		locations[2] = event.values[2];
		
		//Update Direction Strings
		if(deltaX > 0.7)
			directions[0] = "LEFT";
		else if(deltaX < -0.7)
			directions[0] = "RIGHT";
		
		if(deltaY > 0.7) 
			directions[1] = "FORWARD";
		else if(deltaY < -0.7)
			directions[1] = "BACKWARD";
		
		if(deltaZ > 0.7)
			directions[2] = "UP";
		else if(deltaZ < -0.7)
			directions[2] = "DOWN";
		
			builder.setLength(0);
        builder.append("X: ");
        builder.append(event.values[0]+"\n");
        builder.append(" Y: ");
        builder.append(event.values[0]+"\n");
        builder.append(" Z: "+event.values[0]);
        
        textView.setText(builder.toString());
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1)
	{
		//Nothing
	}
}
