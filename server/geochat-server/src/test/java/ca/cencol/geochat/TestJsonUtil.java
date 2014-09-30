/**
 * 
 */
package ca.cencol.geochat;

import java.util.Date;

import javax.json.JsonObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ca.cencol.geochat.util.JsonUtil;

/**
 * @author sveta
 *
 */
public class TestJsonUtil extends TestCase
{

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public TestJsonUtil(String testName)
  {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite()
  {
    return new TestSuite(TestJsonUtil.class);
  }

  public void testGetMessageJson()
  {
    String userId = "id";
    Date date = new Date();
    String messageText = "hello";
    Message msg = new Message(userId, date, messageText);
    JsonObject json = JsonUtil.getMessageJson(msg);
    assertEquals(userId, json.getString(JsonUtil.STR_MESSAGE_USER_ID));
    assertEquals(date.toString(), json.getString(JsonUtil.STR_MESSAGE_TIME).toString());
    assertEquals(messageText, json.getString(JsonUtil.STR_MESSAGE_TXT));
  }

  public void testGetErrorJson()
  {
    int errorCode = 123;
    String errorMsg = "Some error";
    JsonObject json = JsonUtil.getErrorJson(errorCode, errorMsg);
    assertEquals(errorCode, json.getInt(JsonUtil.STR_ERROR_CODE));
    assertEquals(errorMsg, json.getString(JsonUtil.STR_ERROR_MSG));
  }

  public void testGetRoomMessageJson()
  {
    // TODO
  }

  public void testParseRegistrationInfo()
  {
    // TODO
  }
}
