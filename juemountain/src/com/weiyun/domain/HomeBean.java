package com.weiyun.domain;

import java.util.List;

public class HomeBean {

	public static SubscriptionBean subscriptions;
	public static String getComment;
	public static String sendComment;
	public static String supportComment;
	public static String detail;
	public static String newGallery;

	public SubscriptionBean getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(SubscriptionBean subscriptions) {
		this.subscriptions = subscriptions;
	}

	public String getGetComment() {
		return getComment;
	}

	public void setGetComment(String getComment) {
		this.getComment = getComment;
	}

	public String getSendComment() {
		return sendComment;
	}

	public void setSendComment(String sendComment) {
		this.sendComment = sendComment;
	}

	public String getSupportComment() {
		return supportComment;
	}

	public void setSupportComment(String supportComment) {
		this.supportComment = supportComment;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public static String getNewGallery() {
		return newGallery;
	}

	public void setNewGallery(String newGallery) {
		this.newGallery = newGallery;
	}

}
