package com.gss.findmytrainapp2;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gss.findmytrainapp2.MainActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.gss.findmytrainbackend.findmytrain.Findmytrain;
import com.gss.findmytrainbackend.findmytrain.model.*;


public class MainActivity extends ActionBarActivity {

	Button btnsubmit;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnsubmit = (Button) findViewById(R.id.btnsubmit);
		btnsubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//persist check for a user
				String timestamp = "12:00";
				String station = "Badulla";
				String[] params = { station, timestamp };
				new AddUserAsyncTask(MainActivity.this).execute(params);
				
				
				//transfering to the next activity
				Intent myIntent = new Intent (MainActivity.this, SecondActivity.class);
				startActivity(myIntent);
				
				//
				
			}
		});
		
		
		
	}
	
	private class AddUserAsyncTask extends AsyncTask<String, Void, User>{

		
		Context context;
		private ProgressDialog pd;
		
		@Override
		protected User doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			User response = null;
			//Train response = null;
			
			try{
				
				Findmytrain.Builder builder = new Findmytrain.Builder(AndroidHttp.newCompatibleTransport(),
						new GsonFactory(), null);
				
				builder.setRootUrl("http://10.0.2.2:8888/_ah/api");
				builder.setApplicationName("FindMyTrain");
				
				User user = new User();
				user.setUserid("Krv Perera");
				user.setRating(4.8);
				
				Findmytrain service = builder.build();
				response = service.insertUser(user).execute();

			}
			catch(Exception e){
				Log.d("Could not add user 8:36", e.getMessage(), e);
			}
			return response;
		}

		public AddUserAsyncTask(Context context) {
			this.context = context;
		}
		
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(context);
			pd.setMessage("Sending station and timestamp (train stop details)...");
			pd.show();
		}
		
		protected void onPostExecute(User user) {
			//this one is not needed at the moment
			//can be used to clear the fields
			pd.dismiss();
		}
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
