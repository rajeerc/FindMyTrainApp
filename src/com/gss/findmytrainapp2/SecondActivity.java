package com.gss.findmytrainapp2;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.gss.findmytrainbackend.findmytrain.Findmytrain;
import com.gss.findmytrainbackend.findmytrain.model.CollectionResponseTrain;
import com.gss.findmytrainbackend.findmytrain.model.Train;

import android.app.ProgressDialog;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SecondActivity extends ActionBarActivity {

	// Button btnBack;

	private ListView listTrains = null;
	private ArrayList<Train> trainsList;
	private ArrayAdapter<String> adapter;
	public String[] details;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		/*
		 * // getting data from the previous activity Intent intent =
		 * getIntent(); Bundle bundle = intent.getExtras(); String stationName =
		 * bundle.getString("station");
		 */

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
			AsyncTask<Void, Void, CollectionResponseTrain> {

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

		@Override
		protected CollectionResponseTrain doInBackground(Void... unused) {
			CollectionResponseTrain trains = null;
			try {
				Findmytrain.Builder builder = new Findmytrain.Builder(
						AndroidHttp.newCompatibleTransport(),
						new GsonFactory(), null);

				builder.setRootUrl("http://10.10.4.202:80/_ah/api");
				builder.setApplicationName("Findmytrain");
				Findmytrain service = builder.build();
				trains = service.listTrain().execute();

			} catch (Exception e) {
				Log.d("Could not retrieve Trains", e.getMessage(), e);
			}
			return trains;
		}

		@Override
		protected void onPostExecute(CollectionResponseTrain trains) {

			pd.dismiss();
			int i = 0;
			List<Train> _list = trains.getItems();

			details = new String[_list.size()];

			for (Train train : _list) {
				details[i] = new String();
				details[i++] = "From " + train.getStart() + "\nTo "
						+ train.getDestination();
			}

			// Do something with the result.

			adapter = new ArrayAdapter<String>(SecondActivity.this,
					android.R.layout.simple_list_item_1, details);
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
