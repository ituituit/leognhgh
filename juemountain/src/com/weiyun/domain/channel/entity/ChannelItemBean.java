package com.weiyun.domain.channel.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cheesemobile.util._Log;
import com.weiyun.domain.Extension;

public class ChannelItemBean implements Serializable {
	public static final int TYPE_AD = 3;
	public static final int TYPE_COUNT = 4;
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_SLIDE = 1;
	public static final int TYPE_SPORT = 2;
	public static final int TYPE_SUBSCRIBE = 5;
	private static final long serialVersionUID = 3261932987643937516L;
	private String thumbnail;
	private String title;
	private String source;
	private String channel;
	private String updateTime;
	private String id;
	private String documentId;
	private String type;
	private String commentUrl;
	private String comments;
	private String commentsall;
	private Extension link;
	private ChannelStyle style;
	private SportsLiveExt sportsLiveExt;

	public SportsLiveExt getSportsLiveExt() {
		return sportsLiveExt;
	}

	public void setSportsLiveExt(SportsLiveExt sportsLiveExt) {
		this.sportsLiveExt = sportsLiveExt;
	}

	public Extension getLink() {
		return link;
	}

	public void setLink(Extension link) {
		this.link = link;
	}

	public ChannelStyle getStyle() {
		return style;
	}

	public void setStyle(ChannelStyle style) {
		this.style = style;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public int intType(String type) {
		if (type.equals("doc") || type.equals("topic2")) {
			return TYPE_NORMAL;
		}
		if (type.equals("slide")) {
			return TYPE_SLIDE;
		}
		if (type.equals("sports_live")) {
			return TYPE_SPORT;
		}
		return -1;
	}

	public List<String> getImages() {
		List<String> list = new ArrayList<String>();
		switch (intType(this.type)) {
		case TYPE_SPORT:
			list.add(this.sportsLiveExt.getLeftLogo());
			list.add(this.sportsLiveExt.getRightLogo());
			break;
		case TYPE_SLIDE:
			if (style == null) {
				_Log.e(this.toString() + "style is null");
				break;
			}
			list.addAll(this.style.getImages());
			break;
		case TYPE_NORMAL:
			list.add(thumbnail);
			break;
		default:
			break;
		}
		return list;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommentUrl() {
		return commentUrl;
	}

	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCommentsall() {
		return commentsall;
	}

	public void setCommentsall(String commentsall) {
		this.commentsall = commentsall;
	}

}
