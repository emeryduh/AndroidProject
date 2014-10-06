package com.comp313;

import java.io.BufferedReader;
import java.io.IOException;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends Activity {

	// holds the return user id
	private String strUserId;

	// holds the error response
	private String strResponse;

	// user agent
	private final String USER_AGENT = "Mozilla/5.0";

	// instance variable for input fields
	EditText txtUserName, txtNickName, txtEmail, txtPassword,
			txtConfirmPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// hide the title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		
	/*	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);*/

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
		Intent intent = null;
		switch (view.getId()) {
		case R.id.btnSubmit:
			intent = new Intent(this, ChatActivity.class);
			
		    // String strUserId = registerUser();
		
			// startActivity(intent);
			break;
		}
	}

	private String registerUser() throws ClientProtocolException, IOException {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(Utility.registerURL);

		// add header
		post.setHeader("User-Agent", USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("username", txtUserName
				.getText().toString()));
		urlParameters.add(new BasicNameValuePair("nickname", txtNickName
				.getText().toString()));
		urlParameters.add(new BasicNameValuePair("email", txtEmail.getText()
				.toString()));
		urlParameters.add(new BasicNameValuePair("password", txtPassword
				.getText().toString()));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : "
				+ Utility.registerURL);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : "
				+ response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return strUserId;
	}
}
