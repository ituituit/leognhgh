package com.weiyun.domain;

public class UserInfo {
	private String openId;
	private String token;
	private String imgUrl;
	private String nickname;
	private String userName;
	private String errorMsg;
	private VerifyResult vr;

	public UserInfo() {
		super();
	}

	public UserInfo(String token, String imgUrl, String nickname,
			String userName) {
		super();
		this.openId = "";
		this.token = token;
		this.imgUrl = imgUrl;
		this.nickname = nickname;
		this.userName = userName;
		this.errorMsg = "1";
	}

	public VerifyResult getVr() {
		return vr;
	}

	public void setVr(VerifyResult vr) {
		this.vr = vr;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
