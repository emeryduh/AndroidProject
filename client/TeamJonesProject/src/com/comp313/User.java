/**
 * 
 */
package com.comp313;

/**
 * @author Vivekanandhan
 * 
 */
public class User {

	private String UserId;
	private String NickName;
	private String Email;

	/**
	 * @param userId
	 * @param nickName
	 * @param emailAddr
	 */
	public User(String userId, String nickName, String emailAddr) {
		this.UserId = userId;
		this.NickName = nickName;
		this.Email = emailAddr;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return UserId;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return NickName;
	}

	/**
	 * @return the emailAddr
	 */
	public String getEmailAddr() {
		return Email;
	}

}
