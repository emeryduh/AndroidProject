/**
 * 
 */
package com.comp313;

/**
 * @author Vivekanandhan
 * 
 */
public class ChatMessage {

	// holds left or right boolean value
	public boolean left;

	// holds string message
	public String message;

	// holds name
	public String name;

	// holds name
	public String dateTime;

	public ChatMessage(boolean left, String message, String name,
			String dateTime) {
		super();
		this.left = left;
		this.message = message;
		this.name = name;
		this.dateTime = dateTime;

	}
}
