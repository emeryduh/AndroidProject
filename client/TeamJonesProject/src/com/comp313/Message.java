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
	public boolean Left;

	// holds string message
	public String MessageText;

	// holds name
	public String User;

	// holds name
	public String Date;

	public Message(boolean left, String message, String name, String dateTime) {
		super();
		this.Left = left;
		this.MessageText = message;
		this.User = name;
		this.Date = dateTime;

	}
	
	/**
	   * @return the userId
	   */
	  public String getUserId() {
	    return User;
	  }

	  /**
	   * @return the date
	   */
	  public String getDate() {
	    return Date;
	  }

	  /**
	   * @return the messageText
	   */
	  public String getMessageText() {
	    return MessageText;
	  }

}
