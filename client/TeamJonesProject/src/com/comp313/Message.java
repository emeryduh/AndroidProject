/**
 * 
 */
package com.comp313;

/**
 * @author Vivekanandhan
 * 
 */
public class Message {

	// holds left or right boolean value
	private boolean left;

	// holds string message
	private String messageText;

	// holds name
	private String userId;

	// holds name
	private String date;

	public Message(boolean left, String message, String name, String dateTime) {
		super();
		this.left = left;
		this.messageText = message;
		this.userId = name;
		this.date = dateTime;

	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return messageText
	 */
	public String getMessageText() {
		return messageText;
	}
	
	/**
	 * @return left
	 */
	 public boolean isLeft() {
		 return left;
	 }

}
