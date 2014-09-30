/**
 * 
 */
package ca.cencol.geochat;

/**
 * @author sveta
 *
 */
public class User {

  private String userId;
  private String nickName;
  private String emailAddr;

  /**
   * @param userId
   * @param nickName
   * @param emailAddr
   */
  public User(String userId, String nickName, String emailAddr) {
    this.userId = userId;
    this.nickName = nickName;
    this.emailAddr = emailAddr;
  }

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @return the nickName
   */
  public String getNickName() {
    return nickName;
  }

  /**
   * @return the emailAddr
   */
  public String getEmailAddr() {
    return emailAddr;
  }

}
