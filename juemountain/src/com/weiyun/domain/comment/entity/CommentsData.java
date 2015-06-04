package com.weiyun.domain.comment.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import android.webkit.JavascriptInterface;

public class CommentsData implements Serializable {
	private static final long serialVersionUID = 1L;
	private AllComments comments;
	private int count;
	private int followcount;
	private int join_count;
	private int nopass;
	private int subjectid;

	public AllComments getComments() {
		return this.comments;
	}

	public int getCount() {
		return this.count;
	}

	public int getFollowcount() {
		return this.followcount;
	}

	public int getJoin_count() {
		return this.join_count;
	}

	public int getNopass() {
		return this.nopass;
	}

	public int getSubjectid() {
		return this.subjectid;
	}

	public void setComments(AllComments paramAllComments) {
		this.comments = paramAllComments;
	}

	public void setCount(int paramInt) {
		this.count = paramInt;
	}

	public void setFollowcount(int paramInt) {
		this.followcount = paramInt;
	}

	public void setJoin_count(int paramInt) {
		this.join_count = paramInt;
	}

	public void setNopass(int paramInt) {
		this.nopass = paramInt;
	}

	public void setSubjectid(int paramInt) {
		this.subjectid = paramInt;
	}
	
	@JavascriptInterface
	public String toString(){
		return JSON.toJSONString(this);
	}
}