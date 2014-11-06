package com.comp313;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	
	//create variables for validation
	private EditText txtDisplayName, txtStatus, txtAboutMe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		//get the values for each variable
		txtDisplayName = (EditText) findViewById(R.id.displayName);
		txtStatus = (EditText) findViewById(R.id.statusProfile);
		txtAboutMe = (EditText) findViewById(R.id.aboutMe);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
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

	//Fired when Edit button is pressed
	public void validateEdit(android.view.View view){
		//Parse the values to string
		final String displayName = this.txtDisplayName.getText().toString();
		final String status = this.txtStatus.getText().toString();
		final String aboutMe = this.txtAboutMe.getText().toString();
		
		//TODO: Add functionality to send the valid data to server
		if(isValidDisplayName(displayName))
			Toast.makeText(getBaseContext(), "Display name set!",Toast.LENGTH_SHORT).show();
		if(isValidStatus(status))
			Toast.makeText(getBaseContext(), "Status set!",Toast.LENGTH_SHORT).show();
		if(isValidAboutMe(aboutMe))
			Toast.makeText(getBaseContext(), "About Me set!",Toast.LENGTH_SHORT).show();
		
	}
	//Validation for display name
	private boolean isValidDisplayName(String txtDisplayName){
		if (txtDisplayName != null && txtDisplayName.length() > 3) 
			return true;
		 else 
			this.txtDisplayName.setError("Display Name has to be longer than 3 characters!");
			return false;
	}
	//Validation for status
	private boolean isValidStatus(String txtStatus){
		if (txtStatus != null && txtStatus.length() > 3) 
			return true;
		 else 
			this.txtStatus.setError("Status has to be longer than 3 characters!");
			return false;
	}
	//Validation for about me
	private boolean isValidAboutMe(String txtAboutMe){
		if (txtAboutMe != null && txtAboutMe.length() > 3) 
			return true;
		 else 
			 this.txtAboutMe.setError("About Me has to be longer than 3 characters!");
			return false;
	}
}
