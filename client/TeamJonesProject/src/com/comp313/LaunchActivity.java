package com.comp313;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class LaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_launch);
	}
	
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
