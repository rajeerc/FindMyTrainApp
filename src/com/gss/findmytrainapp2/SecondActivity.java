package com.gss.findmytrainapp2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends ActionBarActivity {
	
	Button btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		//getting data from the previous activity
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String stationName = bundle.getString("station");
		
		TextView t = (TextView) findViewById(R.id.textView1);
		t.setText("Train list for " + stationName);
		
		
		//back button for the second window
		btnBack = (Button) findViewById(R.id.btnback);
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent myIntent = new Intent (SecondActivity.this, MainActivity.class);
				startActivity(myIntent);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
