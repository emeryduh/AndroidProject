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
	
	public ChatMessage(boolean left, String message) {
		super();		
		this.left = left;
		this.message = message;
	}
}
