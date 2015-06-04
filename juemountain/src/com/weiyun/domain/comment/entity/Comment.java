package com.weiyun.domain.comment.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.os.Parcel;
import android.text.TextUtils;

public class Comment {
	private static final SimpleDateFormat commentDateParser = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private int add_time;
	private int application;
	private int article_id;
	private String briefDate;
	private int channel_id;
	private String client_ip;
	private String comment_contents;
	private String comment_date;
	private String comment_id;
	private long currentTime;
	private String docId;
	private String doc_name;
	private String doc_url;
	private int downtimes;
	private Ext ext = new Ext();
	private String ext1;
	private String ext2;
	private String ext3;
	private int ext4;
	private int ext5;
	private int ext6;
	private String guid;
	private String id;
	private String ip_from;
	private boolean isClicked = false;
	private boolean isExpansion;
	private boolean isSendComment;
	private transient boolean isUpped = false;
	private int is_hiddenip;
	private int last_update_time;
	private int level;
	private ArrayList<ParentComment> parent = new ArrayList();
	private String quote_id;
	private String replyNickName;
	private int report;
	private String uname;
	private String uptime;
	private String uptimes;
	private String userFace;
	private String uuid;

//	public int compareTo(Comment paramComment) {
//		if (this.comment_contents.equals(paramComment.getComment_contents()))
//			return 0;
//		return -1;
//	}

	public int describeContents() {
		return 0;
	}

	public boolean equals(Object paramObject) {
		if ((this.comment_id != null) && (paramObject != null)
				&& ((paramObject instanceof Comment))) {
			Comment localComment = (Comment) paramObject;
			if (this.comment_id.equals(localComment.getComment_id()))
				return true;
		}
		return false;
	}

	public int getAdd_time() {
		return this.add_time;
	}

	public int getApplication() {
		return this.application;
	}

	public int getArticle_id() {
		return this.article_id;
	}

	public int getChannel_id() {
		return this.channel_id;
	}

	public String getClient_ip() {
		return this.client_ip;
	}

	public String getComment_contents() {
		return this.comment_contents;
	}

	// public String getComment_date()
	// {
	// if (this.briefDate == null)
	// this.briefDate = are.a(are.a(this.comment_date, commentDateParser));
	// return this.briefDate;
	// }

	public String getComment_id() {
		return this.comment_id;
	}

	public long getCurrentTime() {
		return this.currentTime;
	}

	public String getDocId() {
		return this.docId;
	}

	public String getDoc_name() {
		return this.doc_name;
	}

	public String getDoc_url() {
		return this.doc_url;
	}

	public int getDowntimes() {
		return this.downtimes;
	}

	public Ext getExt() {
		return this.ext;
	}

	public String getExt1() {
		return this.ext1;
	}

	public String getExt2() {
		return this.ext2;
	}

	public String getExt3() {
		return this.ext3;
	}

	public int getExt4() {
		return this.ext4;
	}

	public int getExt5() {
		return this.ext5;
	}

	public int getExt6() {
		return this.ext6;
	}

	public String getGuid() {
		return this.guid;
	}

	public String getId() {
		return this.id;
	}

	public String getIp_from() {
		return this.ip_from;
	}

	public int getIs_hiddenip() {
		return this.is_hiddenip;
	}

	public int getLast_update_time() {
		return this.last_update_time;
	}

	public int getLevel() {
		return this.level;
	}

	public ArrayList<ParentComment> getParent() {
		return this.parent;
	}

	public String getQuote_id() {
		return this.quote_id;
	}

	public String getReplyNickName() {
		return this.replyNickName;
	}

	public int getReport() {
		return this.report;
	}

	public String getUname() {
		return this.uname;
	}

	public String getUptime() {
		return this.uptime;
	}

	public String getUptimes() {
		return this.uptimes;
	}

	public String getUserFace() {
		if (TextUtils.isEmpty(this.userFace))
			this.userFace = "";
		return this.userFace;
	}

	public String getUuid() {
		return this.uuid;
	}

	public boolean isClicked() {
		return this.isClicked;
	}

	public boolean isExpansion() {
		return this.isExpansion;
	}

	public boolean isSendComment() {
		return this.isSendComment;
	}

	public boolean isUped() {
		return this.isUpped;
	}

	public void setAdd_time(int paramInt) {
		this.add_time = paramInt;
	}

	public void setApplication(int paramInt) {
		this.application = paramInt;
	}

	public void setArticle_id(int paramInt) {
		this.article_id = paramInt;
	}

	public void setChannel_id(int paramInt) {
		this.channel_id = paramInt;
	}

	public void setClicked(boolean paramBoolean) {
		this.isClicked = paramBoolean;
	}

	public void setClient_ip(String paramString) {
		this.client_ip = paramString;
	}

	public void setComment_contents(String paramString) {
		this.comment_contents = paramString;
	}

	public void setComment_date(String paramString) {
		this.comment_date = paramString;
	}

	public void setComment_id(String paramString) {
		this.comment_id = paramString;
	}

	public void setCurrentTime(long paramLong) {
		this.currentTime = paramLong;
	}

	public void setDocId(String paramString) {
		this.docId = paramString;
	}

	public void setDoc_name(String paramString) {
		this.doc_name = paramString;
	}

	public void setDoc_url(String paramString) {
		this.doc_url = paramString;
	}

	public void setDowntimes(int paramInt) {
		this.downtimes = paramInt;
	}

	public void setExpansion(boolean paramBoolean) {
		this.isExpansion = paramBoolean;
	}

	public void setExt(Ext paramExt) {
		this.ext = paramExt;
	}

	public void setExt1(String paramString) {
		this.ext1 = paramString;
	}

	public void setExt2(String paramString) {
		this.ext2 = paramString;
	}

	public void setExt3(String paramString) {
		this.ext3 = paramString;
	}

	public void setExt4(int paramInt) {
		this.ext4 = paramInt;
	}

	public void setExt5(int paramInt) {
		this.ext5 = paramInt;
	}

	public void setExt6(int paramInt) {
		this.ext6 = paramInt;
	}

	public void setGuid(String paramString) {
		this.guid = paramString;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setIp_from(String paramString) {
		if (TextUtils.isEmpty(paramString))
			paramString = "";
		this.ip_from = paramString;
	}

	public void setIs_hiddenip(int paramInt) {
		this.is_hiddenip = paramInt;
	}

	public void setLast_update_time(int paramInt) {
		this.last_update_time = paramInt;
	}

	public void setLevel(int paramInt) {
		this.level = paramInt;
	}

	public void setParent(ArrayList<ParentComment> paramArrayList) {
		this.parent = paramArrayList;
	}

	public void setQuote_id(String paramString) {
		this.quote_id = paramString;
	}

	public void setReplyNickName(String paramString) {
		this.replyNickName = paramString;
	}

	public void setReport(int paramInt) {
		this.report = paramInt;
	}

	public void setSendComment(boolean paramBoolean) {
		this.isSendComment = paramBoolean;
	}

	public void setUname(String paramString) {
		this.uname = paramString;
	}

	public void setUped(boolean paramBoolean) {
		this.isUpped = paramBoolean;
	}

	public void setUptime(String paramString) {
		this.uptime = paramString;
	}

	public void setUptimes(String paramString) {
		this.uptimes = paramString;
	}

	public void setUserFace(String paramString) {
		this.userFace = paramString;
	}

	public void setUuid(String paramString) {
		this.uuid = paramString;
	}

	public void writeToParcel(Parcel paramParcel, int paramInt) {
	}
}