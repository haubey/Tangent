package com.haubey.tangent;

import com.android.graphbutton.plot2d;

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
    
	protected void onPause()
	{
		super.onPause();
	}
	
	protected void onResume()
	{
		super.onResume();
	}
}
