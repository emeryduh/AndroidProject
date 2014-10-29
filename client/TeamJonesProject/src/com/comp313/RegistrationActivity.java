package com.comp313;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	// holds the error response
	private String strResponse;

	// user agent
	private final String USER_AGENT = "Mozilla/5.0";

	// instance variable for input fields
	private EditText txtUserName, txtNickName, txtEmail, txtPassword,
			txtConfirmPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// hide the title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		StrictMode.ThreadPolicy policy = new
		StrictMode.ThreadPolicy.Builder() .permitAll().build();
		StrictMode.setThreadPolicy(policy);

		setContentView(R.layout.activity_registration);

		// gets the font style to asset resource
		Typeface type = Typeface.createFromAsset(getAssets(),
				"fonts/DEVROYE.ttf");

		// gets the text view instance using id
		TextView tvHeader = (TextView) findViewById(R.id.tvLogoText);

		// sets the font style to text view
		tvHeader.setTypeface(type);

		// gets the instance of user name edit text
		txtUserName = (EditText) findViewById(R.id.txtUserName);

		// gets the instance of nick name edit text
		txtNickName = (EditText) findViewById(R.id.txtNickName);

		// gets the instance of email edit text
		txtEmail = (EditText) findViewById(R.id.txtEmail);

		// gets the instance of password edit text
		txtPassword = (EditText) findViewById(R.id.txtPassword);

		// gets the instance of confirm password edit text
		txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
	}

	// occurs when user clicks button
	public void callIntent(android.view.View view) {

		switch (view.getId()) {
		case R.id.btnSubmit:

			// Parse fields to strings for validation
			final String username = txtUserName.getText().toString();
			final String nickname = txtNickName.getText().toString();
			final String email = txtEmail.getText().toString();
			final String password = txtPassword.getText().toString();
			final String confirmPassword = txtConfirmPassword.getText()
					.toString();

			// If all fields pass validation
			if (validateUsername(username) && validateNickname(nickname)
					&& validateEmail(email) && validatePassword(password)
					&& validateConfirmPassword(confirmPassword)) {
				// Transition to our login activity
				try {
					String userHash = registerUser(username, nickname, email,
							password);
					startActivity(new Intent(this, LoginActivity.class));
				} catch (ClientProtocolException e) {
					Toast.makeText(getBaseContext(), "ClientProtocolException",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				} catch (IOException e) {
					Toast.makeText(getBaseContext(), "IOException",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				} catch (JSONException e) {
					Toast.makeText(getBaseContext(), "JSONException",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
			break;
		}
	}

	private String registerUser(String username, String nickname, String email,
			String password) throws ClientProtocolException, IOException, JSONException {
		
		// Setup out HTTP client and POST url
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(Utility.registerURL);

		// set the header of our POST
		post.setHeader("Content-type", "application/json");

		// create object for json object
		JSONObject registerJSON = new JSONObject();

		// populate our JSON object
		registerJSON.accumulate("email", email);
		registerJSON.accumulate("username", username);
		registerJSON.accumulate("password", password);

		// create object for name value pairs
		List<NameValuePair> regParams = new ArrayList<NameValuePair>();
		regParams.add(new BasicNameValuePair("body", registerJSON.toString()));

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
		// input stream object instance
		InputStream inputStream = response.getEntity().getContent();
		String result = "";

		// Convert InputStream to String
		if (inputStream != null)
			result = convertInputStreamToString(inputStream);
		else
			result = "No result found.";

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

	private boolean validateUsername(String txtUserName) {
		if (txtUserName != null && txtUserName.length() > 3) {
			return true;
		} else {
			this.txtUserName
					.setError("Username has to be longer than 3 characters!");
			return false;
		}
	}

	private boolean validateNickname(String txtNickName) {
		if (txtNickName != null && txtNickName.length() > 3) {
			return true;
		} else {
			this.txtNickName
					.setError("Nickname has to be longer than 3 characters!");
			return false;
		}
	}

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

	private boolean validatePassword(String txtPassword) {
		if (txtPassword != null && txtPassword.length() > 6) {
			return true;
		} else {
			this.txtPassword
					.setError("Password has to be longer than 6 characters!");
			return false;
		}
	}

	private boolean validateConfirmPassword(String txtConfirmPassword) {
		if (txtConfirmPassword != null && txtPassword.length() > 6) {
			return true;
		} else {
			this.txtConfirmPassword
					.setError("Confirmed Password has to be longer than 6 characters!");
			return false;
		}
	}

}
