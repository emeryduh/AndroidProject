/**
 * 
 */
package ca.cencol.geochat.util;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import ca.cencol.geochat.Message;
import ca.cencol.geochat.RegistrationInfo;

/**
 * @author sveta
 *
 * JsonUtil - utility for converting to and parsing JSON objects
 */
public class JsonUtil {

  public static String STR_ROOM_ID = "roomId";
  public static String STR_ERROR_CODE = "error_code";
  public static String STR_ERROR_MSG = "error_message";
  public static String STR_MESSAGES = "messages";
  public static String STR_MESSAGE_USER_ID = "userId";
  public static String STR_MESSAGE_TIME = "time";
  public static String STR_MESSAGE_TXT = "message";

  public static String STR_REGIST_USER_ID = "userId";
  public static String STR_REGIST_NICKNAME = "nickname";
  public static String STR_REGIST_EMAIL = "password";

  public static JsonObject getRoomMessageJson(String roomId, ArrayList<Message> messageList)
  {
    JsonObjectBuilder roomBuilder = Json.createObjectBuilder();
    roomBuilder.add(STR_ROOM_ID, roomId);

    JsonArrayBuilder msgArrBuilder = Json.createArrayBuilder();
    for (Message msg : messageList)
    {
      msgArrBuilder.add(getMessageJson(msg));
    }
    roomBuilder.add(STR_MESSAGES, msgArrBuilder);
    return roomBuilder.build();
  }

  public static JsonObject getMessageJson(Message message)
  {
    JsonObjectBuilder messageBuilder = Json.createObjectBuilder();
    messageBuilder.add(STR_MESSAGE_USER_ID, message.getUserId());
    // TODO: Date format?
    messageBuilder.add(STR_MESSAGE_TIME, message.getDate().toString());
    messageBuilder.add(STR_MESSAGE_TXT, message.getMessageText());
    return messageBuilder.build();
  }

  public static JsonObject getErrorJson(int errorCode, String errorMessage)
  {
    JsonObjectBuilder errorBuilder = Json.createObjectBuilder();
    errorBuilder.add(STR_ERROR_CODE, errorCode);
    errorBuilder.add(STR_ERROR_MSG, errorMessage);
    return errorBuilder.build();
  }

  public static RegistrationInfo parseRegistrationInfo(JsonObject json)
  {
    try {
      String userId = json.getString(STR_REGIST_USER_ID);
      String nickname = json.getString(STR_REGIST_NICKNAME);
      String email = json.getString(STR_REGIST_EMAIL);
      RegistrationInfo info = new RegistrationInfo(userId, nickname, email);
      return info;
    } catch (Exception ex) {
      // TODO throw exception?
      System.out.println("Error: " + ex.getMessage());
      return null;
    }
  }
}
