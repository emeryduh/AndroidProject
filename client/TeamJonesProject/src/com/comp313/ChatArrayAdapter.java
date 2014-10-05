package com.comp313;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ChatArrayAdapter extends ArrayAdapter<Object> {

	// holds the text view control
	private TextView chatText;

	// holds the text view name control
	private TextView tvName;

	// holds the text view date control
	private TextView tvDateTime;

	// this list will hold chat messages
	private List<Message> chatMessageList = new ArrayList<Message>();

	// holds the container object from list item template
	private LinearLayout parentContainer;

	// holds the container object from list item template
	private LinearLayout childContainer;

	// add new chat message to the list
	public void add(Message object) {
		chatMessageList.add(object);
		super.add(object);
	}

	// get the context for current message
	public ChatArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	// gets the messages count
	public int getCount() {
		return this.chatMessageList.size();
	}

	// get list item based on index
	public Message getItem(int index) {
		return (Message) this.chatMessageList.get(index);
	}

	// this method will manipulate the left or right message template based on
	// boolean value
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// assign the custom list item template for list view item
			row = inflater.inflate(R.layout.chat_list_item, parent, false);
		}

		// get the parent container from list item template
		parentContainer = (LinearLayout) row.findViewById(R.id.parentContainer);

		// get the child container from list item template
		childContainer = (LinearLayout) row.findViewById(R.id.childContainer);

		// get the position of the item
		Message chatMessageObj = getItem(position);

		// get the chat text control instance from list item template
		chatText = (TextView) row.findViewById(R.id.tvMessage);
		// set the text
		chatText.setText(chatMessageObj.MessageText);

		// get the name text control instance from list item template
		tvName = (TextView) row.findViewById(R.id.tvName);
		// set the text
		tvName.setText(chatMessageObj.User);

		// get the date text control instance from list item template
		tvDateTime = (TextView) row.findViewById(R.id.tvDateTime);
		// set the text
		tvDateTime.setText(chatMessageObj.Date);

		// set the background bubble image based on boolean value
		childContainer
				.setBackgroundResource(chatMessageObj.Left ? R.drawable.bubble_a
						: R.drawable.bubble_b);

		// set the right or left position based on boolean value
		parentContainer.setGravity(chatMessageObj.Left ? Gravity.LEFT
				: Gravity.RIGHT);
		return row;
	}

	// decode the chat bubble image to stretch or shrink
	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}
