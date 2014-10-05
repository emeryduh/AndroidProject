package com.comp313;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;

import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// hide the title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// gets the font style to asset resource
		Typeface type = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Regular.ttf");

		// gets the text view instance using id
		TextView headerText = (TextView) findViewById(R.id.tvLogoText);

		// sets the font style to text view
		headerText.setTypeface(type);
		
		// gets the welcome text instance using id
		TextView tvWelcomeText = (TextView) findViewById(R.id.tvUserName);

		// sets the font style to text view
		tvWelcomeText.setTypeface(type);
		
		// set the text
		tvWelcomeText.setText("Welcome back Vivek!!");
		
		// setupComponents();
	}

	// occurs when user clicks button
	public void callIntent(android.view.View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.btnEnter:
			intent = new Intent(this, ChatActivity.class);
			startActivity(intent);
			break;
		}
	}
}
