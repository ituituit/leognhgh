package com.weiyun.domain;

import java.io.Serializable;
import java.util.ArrayList;

import android.text.TextUtils;

public class Extension implements Serializable {
	private static final long serialVersionUID = -2118522289106395720L;
	private String category = "";
	private String documentId = "";
	private ArrayList<String> images = new ArrayList();
	private String introduction = "";
	private String isDefault = "";
	private boolean isDirectToComment;
	private String isLinkable = "";
	private String isShow = "";
	private ArrayList<Lifecycle> lifecycle = new ArrayList();
	private String m3u8 = "";
	private String origin = "";
	private int priority;
	private String rtsp = "";
	private String storyId = "";
	private String storyImage = "";
	private String style = "";
	private String subtype = "";
	private String thumbnail = "";
	private ThumbnailSize thumbnailSize;
	private String thumbnailpic = "";
	private String title = "";
	private String topicSportsAd;
	private String topicUrl;
	private String type = "";
	private String url = "";
	private float validSeconds;
	private String validTime = "";

	public String getCategory() {
		return this.category;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public ArrayList<String> getImages() {
		return this.images;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public String getIsDefault() {
		return this.isDefault;
	}

	public String getIsLinkable() {
		return this.isLinkable;
	}

	public String getIsShow() {
		return this.isShow;
	}

	public ArrayList<Lifecycle> getLifecycles() {
		return this.lifecycle;
	}

	public String getM3u8() {
		return this.m3u8;
	}

	public String getOrigin() {
		return this.origin;
	}

	public int getPriority() {
		return this.priority;
	}

	public String getRtsp() {
		return this.rtsp;
	}

	public String getStoryId() {
		return this.storyId;
	}

	public String getStoryImage() {
		return this.storyImage;
	}

	public String getStyle() {
		return this.style;
	}

	public String getSubtype() {
		return this.subtype;
	}

	public String getThumbnail() {
		if (TextUtils.isEmpty(this.thumbnail))
			this.thumbnail = "";
		return this.thumbnail;
	}

	public ThumbnailSize getThumbnailSize() {
		return this.thumbnailSize;
	}

	public String getThumbnailpic() {
		return this.thumbnailpic;
	}

	public String getTitle() {
		return this.title;
	}

	public String getTopicSportsAd() {
		return this.topicSportsAd;
	}

	public String getTopicUrl() {
		return this.topicUrl;
	}

	public String getType() {
		return this.type;
	}

	public String getUrl() {
		return this.url;
	}

	public float getValidSeconds() {
		return this.validSeconds;
	}

	public String getValidTime() {
		return this.validTime;
	}

	public boolean isDirectToComment() {
		return this.isDirectToComment;
	}

	public void setCategory(String paramString) {
		this.category = paramString;
	}

	public void setDirectToComment(boolean paramBoolean) {
		this.isDirectToComment = paramBoolean;
	}

	public void setDocumentId(String paramString) {
		this.documentId = paramString;
	}

	public void setImages(ArrayList<String> paramArrayList) {
		this.images = paramArrayList;
	}

	public void setIntroduction(String paramString) {
		this.introduction = paramString;
	}

	public void setIsDefault(String paramString) {
		this.isDefault = paramString;
	}

	public void setIsLinkable(String paramString) {
		this.isLinkable = paramString;
	}

	public void setIsShow(String paramString) {
		this.isShow = paramString;
	}

	public void setLifecycles(ArrayList<Lifecycle> paramArrayList) {
		this.lifecycle = paramArrayList;
	}

	public void setM3u8(String paramString) {
		this.m3u8 = paramString;
	}

	public void setOrigin(String paramString) {
		this.origin = paramString;
	}

	public void setPriority(int paramInt) {
		this.priority = paramInt;
	}

	public void setRtsp(String paramString) {
		this.rtsp = paramString;
	}

	public void setStoryId(String paramString) {
		this.storyId = paramString;
	}

	public void setStoryImage(String paramString) {
		this.storyImage = paramString;
	}

	public void setStyle(String paramString) {
		this.style = paramString;
	}

	public void setSubtype(String paramString) {
		this.subtype = paramString;
	}

	public void setThumbnail(String paramString) {
		this.thumbnail = paramString;
	}

	public void setThumbnailSize(ThumbnailSize paramThumbnailSize) {
		this.thumbnailSize = paramThumbnailSize;
	}

	public void setThumbnailpic(String paramString) {
		this.thumbnailpic = paramString;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

	public void setTopicSportsAd(String paramString) {
		this.topicSportsAd = paramString;
	}

	public void setTopicUrl(String paramString) {
		this.topicUrl = paramString;
	}

	public void setType(String paramString) {
		this.type = paramString;
	}

	public void setUrl(String paramString) {
		this.url = paramString;
	}

	public void setValidSeconds(float paramFloat) {
		this.validSeconds = paramFloat;
	}

	public void setValidTime(String paramString) {
		this.validTime = paramString;
	}
}

/*
 * Location: /Users/pwl/Desktop/classes-dex2jar.jar Qualified Name:
 * com.ifeng.news2.channel.entity.Extension JD-Core Version: 0.6.2
 */