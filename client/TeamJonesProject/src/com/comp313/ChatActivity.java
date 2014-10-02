/**
 * 
 */
package com.comp313;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
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
	private static final String TAG = "ChatActivity";

	private ChatArrayAdapter chatArrayAdapter;
	private ListView lstChatMessages;
	private EditText txtChatText;
	private Button btnSend;

	Intent intent;
	private boolean side = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		Intent i = getIntent();

		btnSend = (Button) findViewById(R.id.btnSend);

		lstChatMessages = (ListView) findViewById(R.id.lstChatMessages);

		chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(),
				R.layout.chat_list_item);
		lstChatMessages.setAdapter(chatArrayAdapter);

		txtChatText = (EditText) findViewById(R.id.txtChatText);
		txtChatText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					return sendChatMessage();
				}
				return false;
			}
		});
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				sendChatMessage();
			}
		});

		lstChatMessages
				.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		lstChatMessages.setAdapter(chatArrayAdapter);

		// to scroll the list view to bottom on data change
		chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				lstChatMessages.setSelection(chatArrayAdapter.getCount() - 1);
			}
		});
	}

	private boolean sendChatMessage() {
		chatArrayAdapter.add(new ChatMessage(side, txtChatText.getText()
				.toString()));
		txtChatText.setText("");
		side = !side;
		return true;
	}

}
