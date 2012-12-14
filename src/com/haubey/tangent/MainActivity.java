package com.haubey.tangent;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class MainActivity extends FragmentActivity {
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
	 * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
	 * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	List<CustomFunction> custom_functions;
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// Create the adapter that will return a fragment for each of the three primary sections
		// of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		
		
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		
		//Get the list of custom functions
//		List<CustomFunction> custom_functions = setupCustomFunctions();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//        getMenuInflater().inflate(R.menu.activity_main, menu);
		return false;
	}
	
	public void acceptFunction(View view) throws InvalidCustomFunctionException
	{
		EditText functionTextBox = (EditText) findViewById(R.id.function_textbox);
		String functionString = functionTextBox.getText().toString();
		
		if (functionString.trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Please Enter A Function", Toast.LENGTH_LONG).show();
			return;
		}
		
				if (functionString.contains("e^x")) {
					functionString = functionString.replace("e^", "exp(");
					Toast.makeText(getApplicationContext(), "Replacing e^x", Toast.LENGTH_LONG).show();
				}
		
		try {
			Calculable exp = new ExpressionBuilder(functionString).withVariable("x", 10).build();
			exp.calculate();
			String function = functionString;
			
			startActivity(new Intent(this, Grapher.class).putExtra("function", function));   
		}
		
		catch(UnparsableExpressionException e)
		{
			Toast.makeText(getApplicationContext(), "Invalid Function, try again", Toast.LENGTH_SHORT).show();
		}
		catch(UnknownFunctionException e)
		{
			Toast.makeText(getApplicationContext(), "Invalid Function, try again", Toast.LENGTH_SHORT).show();
		}
		
	} 
	
	public void addCarrot(View view)
	{
		EditText func = (EditText) findViewById(R.id.function_textbox);
		int start = func.getSelectionStart();
		int end = func.getSelectionEnd();
		func.getText().replace(Math.min(start, end), Math.max(start, end), "^", 0, 1);
	}
	
	public void addlParen(View view)
	{
		EditText func = (EditText) findViewById(R.id.function_textbox);
		int start = func.getSelectionStart();
		int end = func.getSelectionEnd();
		func.getText().replace(Math.min(start, end), Math.max(start, end), "(", 0, 1);
	}
	
	public void addrParen(View view)
	{
		EditText func = (EditText) findViewById(R.id.function_textbox);
		int start = func.getSelectionStart();
		int end = func.getSelectionEnd();
		func.getText().replace(Math.min(start, end), Math.max(start, end), ")", 0, 1);
	}
	
	public void addX(View view)
	{
		EditText func = (EditText) findViewById(R.id.function_textbox);
		int start = func.getSelectionStart();
		int end = func.getSelectionEnd();
		func.getText().replace(Math.min(start, end), Math.max(start, end), "x", 0, 1);
	}
	
	/**
	 * (Kind of) neatly sets up and returns custom functions
	 * @return a list of CustomFunction objects of each of the custom functions
	 */
	private List<CustomFunction> setupCustomFunctions()
	{
		//Creates a custom function to handle logs of any base.
		//Form: log(5, 10) is evaluated as: log_5(10)
		CustomFunction log_b;
		CustomFunction ln;
//		CustomFunction e;
		try
		{
			log_b = new CustomFunction("log", 2) {
				public double applyFunction(double... value) {
					return Math.log10(value[1])/Math.log10(value[0]);
				}
			};
			
			
			//Handles 'ln' as a natural log
			ln = new CustomFunction("ln") {
				public double applyFunction(double... value) {
					return Math.log(value[0]);
				}
			};
			
			//Handles "e^"
//			e = new CustomFunction("e^") {
//				public double applyFunction(double... value) {
//					return Math.pow(Math.E, value[0]);
//				}
//			};
		}
		catch (InvalidCustomFunctionException e1)
		{
			log_b = null;
			ln = null;
//			e = null;
		} 
		
		return Arrays.asList(log_b, ln);
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
	 * sections of the app.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int i) {
			Fragment fragment = null;
			switch (i) {
			case 0:
				fragment = new PreBuiltFunctionList();
				break;
			case 1:
				fragment = new EnterFunction();
				break;
			case 2:
				fragment = new DummySectionFragment();
				break;
			}
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
			fragment.setArguments(args);
			return fragment;
		}
		
		@Override
		public int getCount() {
			return 3;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0: return getString(R.string.title_section1).toUpperCase();
			case 1: return getString(R.string.title_section2).toUpperCase();
			case 2: return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}
	
	/**
	 * A dummy fragment representing a section of the app, but that simply displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		public DummySectionFragment() {
		}
		
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Button button = new Button(getActivity());
			button.setGravity(Gravity.CENTER);
			button.setText("Orientation Test");
			button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					getActivity().startActivity(new Intent(getActivity(), OrientationTest.class));   
					
				}
			});
			return button;
		}
	}
}
