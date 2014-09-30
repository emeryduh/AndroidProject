/**
 * 
 */
package ca.cencol.geochat;

/**
 * @author sveta
 *
 */
public class RegistrationInfo {

  private String userId;
  private String nickname;
  private String emailAddr;

  // TODO password

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @return the nickName
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * @return the emailAddr
   */
  public String getEmailAddr() {
    return emailAddr;
  }

  /**
   * @param userId
   * @param nickName
   * @param emailAddr
   */
  public RegistrationInfo(String userId, String nickname, String emailAddr) {
    // TODO add password
    this.userId = userId;
    this.nickname = nickname;
    this.emailAddr = emailAddr;
  }

}
