package com.gss.findmytrainapp2;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gss.findmytrainbackend.findmytrain.Findmytrain;
import com.gss.findmytrainbackend.findmytrain.model.CollectionResponseTrain;
import com.gss.findmytrainbackend.findmytrain.model.StringCollection;
import com.gss.findmytrainbackend.findmytrain.model.Train;

public class SecondActivity extends ActionBarActivity {

	// Button btnBack;

	private ListView listTrains = null;
	private ArrayList<Train> trainsList;
	private ArrayAdapter<String> adapter;
	public String[] details;
	private String stationName;
	private List<Train> trainList = new ArrayList<Train>();
	private StringCollection trainStringList = new StringCollection();
	final String SPLIT_STRING = "\n";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		// getting data from the previous activity
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		stationName = bundle.getString("station");

		// showing the list of trains available
		listTrains = (ListView) findViewById(R.id.listtrains);
		new TrainsListAsyncTask(this).execute();

		/*
		 * Log.d("details", details.toString());
		 * 
		 * for (String s : details) { Log.d("44X", s); }
		 */

		// adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, details);
		// listTrains.setAdapter(adapter);

		/*
		 * TextView t = (TextView) findViewById(R.id.textView1);
		 * t.setText("Train list for " + stationName);
		 */
		/*
		 * //back button for the second window btnBack = (Button)
		 * findViewById(R.id.btnback); btnBack.setOnClickListener(new
		 * View.OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub
		 * 
		 * Intent myIntent = new Intent (SecondActivity.this,
		 * MainActivity.class); startActivity(myIntent);
		 * 
		 * } });
		 */

	}

	private class TrainsListAsyncTask extends
			AsyncTask<Void, Void, StringCollection> {

		Context context;
		private ProgressDialog pd;

		public TrainsListAsyncTask(Context context) {
			this.context = context;
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(context);
			pd.setMessage("Retrieving Trains...");
			pd.show();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected StringCollection doInBackground(Void... unused) {
			CollectionResponseTrain trains = null;
			try {
				Findmytrain.Builder builder = new Findmytrain.Builder(
						AndroidHttp.newCompatibleTransport(),
						new GsonFactory(), null);

				builder.setRootUrl("http://10.0.2.2:8888/_ah/api");
				builder.setApplicationName("Findmytrain");
				Findmytrain service = builder.build();

				// ListView stationList = (ListView)
				// findViewById(R.id.listView1);

				// trainStringList =
				// service.listOfTrains(stationName).execute();
				// Log.d("station list" , trainStringList);
				// trains = service.listTrain().execute();
				// trains = service.listOfTrains(stationName).execute();

				trainStringList = service.listOfTrains(stationName).execute();
				// Log.d("train list strings", "this is the first entry : " +
				// trainsListStrings.get(0) + " => size: "+
				// trainsListStrings.size());

			} catch (Exception e) {
				Log.d("Could not retrieve Trains", e.getMessage(), e);
			}
			return trainStringList;
		}

		@Override
		protected void onPostExecute(StringCollection trains) {

			pd.dismiss();
			List<String> trainList = null;
			trainList = trains.getItems();
			// int i = 0;

			// this is where the whole list of available trains is displayed

			// List<Train> list = trains.getItems();
			//
			// details = new String[list.size()];
			//
			// for (Train train : list) {
			// details[i] = new String();
			// details[i++] = "From " + train.getStart() + "\nTo "
			// + train.getDestination();
			// }

			// data is taken

			// i=0;
			// if (trainStringList.getItems().toString() != null){
			// details[i] = new String();
			// details[i] = trainStringList.getItems().toString();}

			// Do something with the result. with the String list
			// forward by the listOfTrains method

			List<String> displayingList = new ArrayList<String>();
			String[] tempArray;

			if (trainList.size() != 0) {
				for (String s : trainList) {
					tempArray = s.split(SPLIT_STRING);
					displayingList.add(tempArray[0]);
					//Log.d("details", tempArray[0]);
				}
			}

			else {

				// displaying an alert dialog when there are not trains
				// available

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						SecondActivity.this);

				alertDialog.setTitle("Train not found");
				alertDialog
						.setMessage("Unfortunately there are no trains available at the moment");
				alertDialog.setIcon(android.R.drawable.ic_dialog_info);

				alertDialog.setPositiveButton("Back",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								Intent myIntent = new Intent(
										SecondActivity.this, MainActivity.class);
								startActivity(myIntent);
							}
						});

				alertDialog.show();

			}

			//
			// displayingList = new ArrayList<String>();
			// displayingList.add("ranji");
			// displayingList.add("ranji2");
			// displayingList.add("ranji3");
			//
			//
			// adapter = new ArrayAdapter<String>(SecondActivity.this,
			// android.R.layout.simple_list_item_1, displayingList);
			// listTrains.setAdapter(adapter);

			adapter = new ArrayAdapter<String>(SecondActivity.this,
					android.R.layout.simple_list_item_1, displayingList);
			listTrains.setAdapter(adapter);
		}
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
