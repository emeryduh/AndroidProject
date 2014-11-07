package com.comp313;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.Gravity;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText txtEmail, txtPassword;

	// instance for login activity task
	LoginActivityTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// hide the title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		// gets the font style to asset resource
		Typeface type = Typeface.createFromAsset(getAssets(),
				"fonts/DEVROYE.ttf");

		// gets the text view instance using id
		TextView tvHeader = (TextView) findViewById(R.id.tvLogoText);

		// sets the font style to text view
		tvHeader.setTypeface(type);

		// get and assign the username instance
		txtEmail = (EditText) findViewById(R.id.txtEmail);

		// get and assign the password instance
		txtPassword = (EditText) findViewById(R.id.txtPassword);
	}

	// occurs when user clicks button
	public void callIntent(android.view.View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.btnLogin:
			if (validateEmail(txtEmail.getText().toString())
					&& validatePassword(txtPassword.getText().toString())) {
				task = new LoginActivityTask();
				task.execute(new String[] { "" });
			}
			break;
		}
	}

	// This will logint he user provided the userId
	public void login(String strResponse) {
		// get the tower location
		String[] value = getCellTowerInfo();
		// to store the userId locally
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"AppPref", 0);
		// to store and retrieve value using editor
		Editor editor = pref.edit();
		// store userId
		editor.putString("userId", strResponse);
		// commit the changes
		editor.commit();
		// intent
		Intent intent = new Intent(this, ChatActivity.class);
		intent.putExtra("userId", strResponse);
		intent.putExtra("roomid", value[1]);
		startActivity(intent);
	}

	// method to navigate to next activity
	@SuppressLint("CommitPrefEdits")
	public void navigate(String strResponse, int code) {
		// trim the response to just the ID
		strResponse = strResponse.substring(strResponse.indexOf(":") + 2, strResponse.length() - 2);
		
		switch (code) {
		case 200:
			showToast(strResponse);
			login(strResponse);
			break;
		case 400:
			showToast(strResponse);
			break;
		case 403:
			showToast(strResponse);
			break;
		case 500:
			showToast("Server cannot be reached");
			break;
		default:
			login(strResponse);
			break;
		}
	}

	// method to get cell tower info
	public String[] getCellTowerInfo() {
		// provides access to information about the telephony services on the
		// device.
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		// represents the cell location on a GSM phone.
		GsmCellLocation cellLocation = (GsmCellLocation) tm.getCellLocation();

		String[] value = new String[2];
		try {
			// returns cell id
			value[0] = String.valueOf(cellLocation.getCid() % 0xffff);

			// returns cell location
			value[1] = String.valueOf(cellLocation.getLac() % 0xffff);
		} catch (NullPointerException e) {
			value[0] = Utility.noRoomFoundID;
			value[1] = Utility.noRoomFoundID;
			return value;
		}
		return value;
	}

	// show toast
	public void showToast(String text) {
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
		toast.show();
	}

	// method to validate email
	private boolean validateEmail(String txtEmail) {
		if (txtEmail != null
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail)
						.matches()) {
			return true;
		} else {
			this.txtEmail.setError("Invalid Email");
			return false;
		}
	}

	// method to validate password
	private boolean validatePassword(String txtPassword) {
		if (txtPassword != null) {
			return true;
		} else {
			this.txtPassword.setError("Password cannot be empty");
			return false;
		}
	}

	// class to execute the asynchronous task. Since, we cannot the run the http
	// post and get in UI thread.
	public class LoginActivityTask extends AsyncTask<String, Void, String> {
		String strResponse = "";
		int responseCode;

		@Override
		protected String doInBackground(String... params) {
			try {
				strResponse = login(txtEmail.getText().toString(), txtPassword
						.getText().toString());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			navigate(strResponse, responseCode);
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

		// method to login user
		private String login(String email, String password)
				throws ClientProtocolException, IOException, JSONException {

			// input stream object instance
			InputStream inputStream = null;

			// holds the response
			String result = "";

			// create HttpClient
			HttpClient client = new DefaultHttpClient();

			// make POST request to the given URL.
			HttpPost post = new HttpPost(Utility.loginURL);

			// create object for json object
			JSONObject registerJSON = new JSONObject();

			// populate our JSON object
			registerJSON.accumulate("email", email);
			registerJSON.accumulate("password", password);

			// create object for name value pairs
			List<NameValuePair> regParams = new ArrayList<NameValuePair>();

			regParams.add(new BasicNameValuePair("body", registerJSON
					.toString()));

			// set the header of our POST
			post.setHeader("Content-type", "application/json");

			// Set the HTTP Entity
			post.setEntity(new UrlEncodedFormEntity(regParams));

			// Set the entity for our JSON object
			StringEntity entityJSON = new StringEntity(registerJSON.toString(),
					HTTP.UTF_8);
			post.setEntity(entityJSON);

			// POST the message
			HttpResponse response = client.execute(post);

			// Show our response
			System.out.println("\nSending 'POST' request to URL : "
					+ Utility.registerURL);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : "
					+ response.getStatusLine().getStatusCode());

			// Get the response
			inputStream = response.getEntity().getContent();

			// get code
			responseCode = response.getStatusLine().getStatusCode();

			// Convert InputStream to String
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "no response";

			// Print our result
			System.out.println(result);

			return result;
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
}
