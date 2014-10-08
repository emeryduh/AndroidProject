/**
 * 
 */
package com.comp313;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @author Vivekanandhan
 * 
 */
public class ChatActivity extends Activity {

	// custom array adapter to hold arbitrary objects
	private ChatArrayAdapter chatArrayAdapter;

	// used to hold our @id/lstChatMessages component in chat_activity
	private ListView lstChatMessages;

	// used to hold our @id/txtChatText component in chat_activity
	private EditText txtChatText;

	// used to hold our @id/btnSend component in chat_activity
	private Button btnSend;

	// holds the boolean value to determine left or right bubble
	private boolean side = false;

	// holds the room id
	private String strRoomId;
	
	private String strUsername;

	// holds the push message response
	private String strPushMessage = "";

	// holds the get message response
	private String strGetMessage = "";

	// instance id for chat activity task
	ChatActivityTask task;

	// instance for timer
	Timer timer;

	// the TimerTask class represents a task to run at a specified time
	TimerTask timerTask;

	// use a handler to be able to run in our TimerTask
	final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get the action bar instance
		ActionBar bar = getActionBar();
		// set the color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CF000F")));

		// set the layout for activity
		setContentView(R.layout.activity_chat);

		// get the intent
		Intent i = getIntent();

		// get the extras from intent
		Bundle extras = i.getExtras();

		// get extras from intent and assign them
		strRoomId = extras.getString("roomid");
		strUsername = extras.getString("username");

		// acquire our layout controls
		btnSend = (Button) findViewById(R.id.btnSend);
		lstChatMessages = (ListView) findViewById(R.id.lstChatMessages);
		txtChatText = (EditText) findViewById(R.id.txtChatText);

		// get the list view item style layout and assign to array adapter
		chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(),
				R.layout.chat_list_item);

		// set the adapter to list view control
		lstChatMessages.setAdapter(chatArrayAdapter);

		// hook the event to chat text control. this event will capture enter
		// button click
		txtChatText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					task = new ChatActivityTask();
					task.execute(new String[] { "POST" });					
				}
				return false;
			}
		});

		// hook the event to chat send button control. this occurs when users
		// click send button
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				task = new ChatActivityTask();
				task.execute(new String[] { "POST" });
			}
		});

		// puts the list or grid into transcript mode. In this mode the list or
		// grid will always scroll to the bottom to show new items.
		lstChatMessages
				.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

		// to scroll the list view to bottom on data change
		chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				lstChatMessages.setSelection(chatArrayAdapter.getCount() - 1);
			}
		});

		// call get message api to retrieve messages
		callGetMessages();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// onResume start the timer so it can start when the app comes from
		// the background
		startTimer();
	}

	@Override
	public void onPause() {
		super.onPause();
		// stop the timer when activity is not active
		timer.cancel();
		timer.purge();
	}

	// method to start the timer
	public void startTimer() {
		// set a new Timer
		timer = new Timer();

		// initialize the TimerTask's job
		initializeTimerTask();

		// schedule the timer, after the first 5000ms the TimerTask will run
		// every 5000ms
		timer.schedule(timerTask, 5000, 5000);
	}

	// execute the timer task
	public void initializeTimerTask() {

		timerTask = new TimerTask() {
			public void run() {

				// use a handler to call the http get request method
				handler.post(new Runnable() {
					public void run() {
						// call get
						callGetMessages();
					}
				});
			}
		};
	}

	// method to execute the asynchronous task
	private void callGetMessages() {
		// create new instance to chat activity class
		task = new ChatActivityTask();

		// start the task
		task.execute(new String[] { "GET" });
	}

	// method to parse the json messages and add to adapter
	private void LoadMessagesToList(String message) throws JSONException,
			ParseException {
		// clear the adapter to add new messages
		chatArrayAdapter.Clear();

		// holds to time stamp
		String timeStamp = "";

		// parse the json string
		JSONObject obj = new JSONObject(message);

		// get the json object using object name
		JSONArray arr = obj.getJSONArray("messages");

		// loop the array
		for (int i = 0; i < arr.length(); i++) {
			// This will determine which side a message goes on
			// Our Messages = Left side
			// All other message = Right side
			if (arr.getJSONObject(i).getString("username").equalsIgnoreCase(strUsername))
				side = false;
			else
				side = true;

			// parse string to long
			long time = Long.parseLong(arr.getJSONObject(i).getString("time"));

			// convert to date object
			Date date = new Date(time);

			// calendar instance to check todays date
			Calendar cal = Calendar.getInstance();
			cal.roll(Calendar.DATE, -1);

			// check if date is today
			if (date.before(cal.getTime())) {
				// if its previous day then show MMM:DD format
				timeStamp = new SimpleDateFormat("MMM:DD").format(date
						.getTime());
			} else {
				// If its todays then show h:mm aa format
				timeStamp = new SimpleDateFormat("h:mm aa").format(date
						.getTime());
			}
			
			// add message to adapter
			chatArrayAdapter.add(new Message(side, arr.getJSONObject(i)
					.getString("message"), arr.getJSONObject(i).getString(
					"username"), timeStamp));
		}
	}
	
	/* This is no longer used, refer to ChatActivityTask.class
	 * 
	// add the new text to adapter which push the message to list view
	private boolean sendChatMessage() {
		// get the current date time
		String timeStamp = new SimpleDateFormat("hh:mm aa").format(Calendar
				.getInstance().getTime());

		chatArrayAdapter.add(new Message(side,
				txtChatText.getText().toString(), strUsername, timeStamp));
		txtChatText.setText("");

		// change the bool value after text entered. this will be modified
		side = !side;
		return true;
	}
	*/

	// class to execute the asynchronous task. Since, we cannot the run the http
	// post and get in UI thread.
	public class ChatActivityTask extends
			AsyncTask<String, Void, ArrayList<String>> {

		@Override
		protected void onPreExecute() {
			/**
			 * show dialog
			 */
			super.onPreExecute();
		}

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			String strResponse = "";
			ArrayList<String> lstResponse = null;

			// check whether is post or get message based on given parameter
			if (params[0] == "POST") {
				try {
					// call the post request
					strResponse = PushMessage(Utility.postMesssageURL);

					// add the response to list to identify get or post
					// operation in onPostExecute
					lstResponse = new ArrayList<String>();
					lstResponse.add(strResponse);
					lstResponse.add("POST");
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				// call the get method
				strResponse = GetMessages(Utility.getMesssagesURL);

				// add the response to list to identify get or post operation in
				// onPostExecute
				lstResponse = new ArrayList<String>();
				lstResponse.add(strResponse);
				lstResponse.add("GET");
			}

			return lstResponse;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			/**
			 * update ui thread and remove dialog
			 */

			// execute methods based on operation
			if (result.get(1) == "POST") {
				// get the current date time
				String timeStamp = new SimpleDateFormat("hh:mm aa")
						.format(Calendar.getInstance().getTime());

				chatArrayAdapter.add(new Message(false, txtChatText.getText()
						.toString(), strUsername, timeStamp));
				
				txtChatText.setText("");
			} else {
				try {
					LoadMessagesToList(result.get(0).toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			super.onPostExecute(result);
		}

		// method to get the messages
		public String GetMessages(String url) {

			// input stream object instance
			InputStream inputStream = null;

			// holds the response
			String result = "";
			try {

				// create HttpClient
				HttpClient httpclient = new DefaultHttpClient();

				// make GET request to the given URL. Will change the user id
				// after registration is completed
				HttpResponse httpResponse = httpclient.execute(new HttpGet(url
						+ "/" + strUsername + "/" + strRoomId));

				// receive response as inputStream
				inputStream = httpResponse.getEntity().getContent();

				// convert input stream to string
				if (inputStream != null)
					result = convertInputStreamToString(inputStream);
				else
					result = "Did not work!";

			} catch (Exception e) {
				// Log.d("InputStream", e.getLocalizedMessage());
			}

			// return the response string
			return result;
		}

		// method to post the message
		public String PushMessage(String url) throws ClientProtocolException,
				IOException, JSONException {

			// input stream object instance
			InputStream inputStream = null;

			// holds the response
			String result = "";

			// create HttpClient
			HttpClient client = new DefaultHttpClient();

			// make POST request to the given URL.
			HttpPost post = new HttpPost(url + "/"+ strUsername + "/" + strRoomId);

			// create object for name value pairs
			List<NameValuePair> lstParams = new ArrayList<NameValuePair>();

			// create object for json object
			JSONObject oMessage = new JSONObject();

			// add message to object
			oMessage.accumulate("message", txtChatText.getText().toString());

			// add json object to list
			lstParams.add(new BasicNameValuePair("body", oMessage.toString()));

			// set the header
			post.setHeader("Content-type", "application/json");

			// set the http entity
			post.setEntity(new UrlEncodedFormEntity(lstParams));

			// set entity for json object
			StringEntity entity = new StringEntity(oMessage.toString(),
					HTTP.UTF_8);
			post.setEntity(entity);

			// post the message
			HttpResponse response = client.execute(post);

			// get the response
			inputStream = response.getEntity().getContent();

			// convert input stream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

			// return the response
			return result.toString();
		}

		// method to convert the stream to string
		private String convertInputStreamToString(InputStream inputStream)
				throws IOException {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));
			String line = "";
			String result = "";
			while ((line = bufferedReader.readLine()) != null)
				result += line;

			inputStream.close();
			return result;

		}

		// method to check whether device is connected to Internet
		public boolean isConnected() {
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected())
				return true;
			else
				return false;
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
