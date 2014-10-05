package com.comp313;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class LaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// hide the title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_launch);
		
		// gets the font style to asset resource 
		Typeface type = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Regular.ttf");
		
		// gets the text view instance using id
		TextView tvHeader = (TextView) findViewById(R.id.tvLogoText);
		
		// sets the font style to text view
		tvHeader.setTypeface(type);
	}
	
	// occurs when user clicks button 
	public void callIntent(android.view.View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.btnLogin:
			intent = new Intent(this, LoginActivity.class);			
			startActivity(intent);			
			break;
		case R.id.btnSignUp:
			intent = new Intent(this, RegistrationActivity.class);			
			startActivity(intent);			
			break;
		}
	}
}
