package com.comp313;

import android.R;

public class MessageCheck {
	
	private static MessageInformation[] messageInfo = null;
	
	
	//Checks if message is null
	public static MessageInformation checkMessage(String username)
	{
		MessageInformation messageContent = null;
		if (messageInfo != null) 
		{
			for (int i = 0; i < messageInfo.length;) 
			{
				
					messageContent = messageInfo[i];
					break;
								
			}			
		}		
		return messageContent;
	}
	
	/*public static MessageInformation getMessageInfo(MessageInfo[] messageInfo)
	{
		MessageInformation messageContent = null;
		if (messageInfo != null) 
		{
			for (int i = 0; i < messageInfo.length;) 
			{
				
					messageContent = messageInfo[i];
					break;
								
			}			
		}		
		return messageContent;
	}
	
	public static MessageInformation setMessageInfo(MessageInfo[] messageInfo)
	{
		MessageCheck.messageInfo = messageInfo;
	}*/

}
