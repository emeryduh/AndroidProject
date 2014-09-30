/**
 * 
 */
package ca.cencol.geochat;

import java.util.Date;

/**
 * @author sveta
 *
 */
public class Message {

  private String userId;
  private Date date;
  private String messageText;

  /**
   * @param userId
   * @param date
   * @param messageText
   */
  public Message(String userId, Date date, String messageText) {
    this.userId = userId;
    this.date = date;
    this.messageText = messageText;
  }

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * @return the messageText
   */
  public String getMessageText() {
    return messageText;
  }

}
