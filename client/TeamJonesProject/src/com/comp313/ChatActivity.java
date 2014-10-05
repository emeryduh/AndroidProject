/**
 * 
 */
package com.comp313;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.AbsListView;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar bar = getActionBar();
		// for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CF000F")));

		setContentView(R.layout.activity_chat);

		// get the intent
		Intent i = getIntent();

		// find and assign send button control instance
		btnSend = (Button) findViewById(R.id.btnSend);

		// find and assign list control control instance
		lstChatMessages = (ListView) findViewById(R.id.lstChatMessages);

		// get the list view item style layout and assign to array adapter
		chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(),
				R.layout.chat_list_item);

		// set the adapter to list view control
		lstChatMessages.setAdapter(chatArrayAdapter);

		// find and assign chat text control instance
		txtChatText = (EditText) findViewById(R.id.txtChatText);

		// hook the event to chat text control. this event will capture enter
		// button click
		txtChatText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					return sendChatMessage();
				}
				return false;
			}
		});

		// hook the event to chat send button control. this occurs when users
		// click send button
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				sendChatMessage();
			}
		});

		// puts the list or grid into transcript mode. In this mode the list or
		// grid will always scroll to the bottom to show new items.
		lstChatMessages
				.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

		// set the array adapter to listview
		// lstChatMessages.setAdapter(chatArrayAdapter);

		// to scroll the list view to bottom on data change
		chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				lstChatMessages.setSelection(chatArrayAdapter.getCount() - 1);
			}
		});
	}

	// add the new text to adapter which push the message to list view
	private boolean sendChatMessage() {
		// get the current date time
		String timeStamp = new SimpleDateFormat("hh:mm aa")
				.format(Calendar.getInstance().getTime());
				
		chatArrayAdapter.add(new Message(side,
				txtChatText.getText().toString(), "Vivek", timeStamp));
		txtChatText.setText("");

		// change the bool value after text entered. this will be modified
		side = !side;
		return true;
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
