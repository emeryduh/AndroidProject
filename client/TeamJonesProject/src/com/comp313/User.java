/**
 * 
 */
package com.comp313;

/**
 * @author Vivekanandhan
 * 
 */
public class User {

	private String userId;
	private String nickName;
	private String email;

	/**
	 * @param userId
	 * @param nickName
	 * @param emailAddr
	 */
	public User(String userId, String nickName, String emailAddr) {
		this.userId = userId;
		this.nickName = nickName;
		this.email = emailAddr;
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
		return email;
	}

}
