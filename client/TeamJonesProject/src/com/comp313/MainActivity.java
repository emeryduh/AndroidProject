package com.comp313;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
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
				"fonts/DEVROYE.ttf");

		// gets the text view instance using id
		TextView tvHeader = (TextView) findViewById(R.id.tvLogoText);

		// sets the font style to text view
		tvHeader.setTypeface(type);

		// gets the welcome text instance using id
		TextView tvWelcomeText = (TextView) findViewById(R.id.tvUserName);

		// sets the font style to text view
		tvWelcomeText.setTypeface(type);

		// set the text
		// TODO: This will need to be modified to later acquire the username
		tvWelcomeText.setText("Welcome!!");

		// setupComponents();
	}

	// occurs when user clicks button
	public void callIntent(android.view.View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.btnEnter:
			// get the tower location
			String[] value = getCellTowerInfo();
			// to store the userId locally
			SharedPreferences pref = getApplicationContext()
					.getSharedPreferences("AppPref", 0);
			// to store and retrieve value using editor
			Editor editor = pref.edit();
			String userId = pref.getString("userId", null);
			if (userId == null || userId.isEmpty()) {
				intent = new Intent(this, RegistrationActivity.class);
				startActivity(intent);
			} else {
				intent = new Intent(this, ChatActivity.class);
				intent.putExtra("userId", userId);
				intent.putExtra("roomid", value[1]);
				// getCellTowerInfo();
				startActivity(intent);
			}
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
		// returns cell id
		value[0] = String.valueOf(cellLocation.getCid() % 0xffff);

		// returns cell location
		value[1] = String.valueOf(cellLocation.getLac() % 0xffff);
		return value;
	}
}
