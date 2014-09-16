package com.gss.findmytrainapp2;

import java.util.Arrays;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gss.findmytrainbackend.findmytrain.Findmytrain;
import com.gss.findmytrainbackend.findmytrain.model.User;

public class MainActivity extends ActionBarActivity {

	Button btnsubmit;
	Spinner spinner;
	ArrayAdapter<String> adapter;
	ListView stationsList;
	StationsAdapter stationsAdapter;
	EditText stationInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		 * //listing the stations spinner = (Spinner)
		 * findViewById(R.id.spinstations); adapter =
		 * ArrayAdapter.createFromResource(this, R.array.tempstations,
		 * android.R.layout.simple_spinner_item);
		 * adapter.setDropDownViewResource
		 * (android.R.layout.simple_spinner_dropdown_item);
		 * spinner.setAdapter(adapter);
		 */

		// station input using the auto complete text field
		// adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, (String[]) getResources()
		// .getStringArray(R.array.tempstations));
		// AutoCompleteTextView textView = (AutoCompleteTextView)
		// findViewById(R.id.stationinput);
		// textView.setAdapter(adapter);

		// filtering the stationslits on the first window
		 List<String> stations = Arrays.asList(getResources().getStringArray(
		 R.array.tempstations));
		 stationsAdapter = new StationsAdapter(this, stations);
		 stationsList = (ListView) findViewById(R.id.stationlist);
		 stationsList.setAdapter(stationsAdapter);

		 
		 stationInput = (EditText) findViewById(R.id.stationinput);
		 stationInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
				MainActivity.this.stationsAdapter.getFilter().filter(arg0);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		 
		 
		/*
		 * final TextView tempView = textView;
		 * 
		 * // submit button // btnsubmit = (Button)
		 * findViewById(R.id.btnsubmit);
		 * 
		 * btnsubmit.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub
		 * 
		 * 
		 * //persist check for a user // String timestamp = "12:00"; // String
		 * station = "Badulla"; // String[] params = { station, timestamp }; //
		 * new AddUserAsyncTask(MainActivity.this).execute(params);
		 * 
		 * String stationName = tempView.getText().toString();
		 * 
		 * //transfering to the next activity Intent myIntent = new Intent
		 * (MainActivity.this, SecondActivity.class); //sending data to the
		 * other activity myIntent.putExtra("station", stationName);
		 * startActivity(myIntent);
		 * 
		 * //
		 * 
		 * } });
		 */

	}

	/*
	 * private final String[] STATIONS = new String[] { "one", "two", "three",
	 * "four", "five", "six" };
	 */

	private class AddUserAsyncTask extends AsyncTask<String, Void, User> {

		Context context;
		private ProgressDialog pd;

		@Override
		protected User doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			User response = null;
			// Train response = null;

			try {

				Findmytrain.Builder builder = new Findmytrain.Builder(
						AndroidHttp.newCompatibleTransport(),
						new GsonFactory(), null);

				builder.setRootUrl("http://192.168.137.221:8888/_ah/api");
				builder.setApplicationName("FindMyTrain");

				// User user = new User();
				// user.setUserid("Krv Perera");
				// user.setRating(4.8);
				//
				// Findmytrain service = builder.build();
				// response = service.insertUser(user).execute();

			} catch (Exception e) {
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
			// this one is not needed at the moment
			// can be used to clear the fields
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
