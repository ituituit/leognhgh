package com.weiyun.domain;

import java.io.Serializable;

public class QRCodeBean  implements Serializable{
	private int ret;
	private String codenum;
	private String qrurl;
	private String validbegintime;
	private String validendtime;
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getCodenum() {
		return codenum;
	}
	public void setCodenum(String codenum) {
		this.codenum = codenum;
	}
	public String getQrurl() {
		return qrurl;
	}
	public void setQrurl(String qrurl) {
		this.qrurl = qrurl;
	}
	public String getValidbegintime() {
		return validbegintime;
	}
	public void setValidbegintime(String validbegintime) {
		this.validbegintime = validbegintime;
	}
	public String getValidendtime() {
		return validendtime;
	}
	public void setValidendtime(String validendtime) {
		this.validendtime = validendtime;
	}
	
}
