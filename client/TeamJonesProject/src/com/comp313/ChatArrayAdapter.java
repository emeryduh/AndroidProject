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
import android.widget.TextView;

public class ChatArrayAdapter extends ArrayAdapter<Object>{
	
	// holds the text view control 
	private TextView chatText;
	
	// holds the text view name control 
    private TextView tvName;
	
	// this list will hold chat messages 
	private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
	
	// holds the container object from list item template
	private LinearLayout singleMessageContainer;

	// add new chat message to the list
	public void add(ChatMessage object) {
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
	public ChatMessage getItem(int index) {
		return (ChatMessage) this.chatMessageList.get(index);
	}

	// this method will manipulate the left or right message template based on boolean value
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// assign the custom list item template for list view item
			row = inflater.inflate(R.layout.chat_list_item, parent, false);
		}
		
		// get the container from list item template
		singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
		
		// get the position of the item
		ChatMessage chatMessageObj = getItem(position);
		
		// get the text view instance from list item template 
		chatText = (TextView) row.findViewById(R.id.singleMessage);
		// set the text 
		chatText.setText(chatMessageObj.message);	
		
		/*// get the text view instance from list item template 
		tvName = (TextView) row.findViewById(R.id.tvName);
				// set the text 
		tvName.setText("Vivek");*/
		
		// set the background bubble image based on boolean value
		chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.bubble_a : R.drawable.bubble_b);
		
		// set the right or left position based on boolean value 
		singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
		return row;
	}

	// decode the chat bubble image to stretch or shrink
	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}
