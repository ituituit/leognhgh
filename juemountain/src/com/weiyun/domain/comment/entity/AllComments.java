package com.weiyun.domain.comment.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AllComments implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Comment> hottest;// = new ArrayList<Comment>();
	private List<Comment> newest;// = new ArrayList<Comment>();

	public List<Comment> getHottest() {
		return this.hottest;
	}

	public List<Comment> getNewest() {
		return this.newest;
	}

	public void setHottest(List<Comment> paramArrayList) {
		this.hottest = paramArrayList;
	}

	public void setNewest(List<Comment> paramArrayList) {
		this.newest = paramArrayList;
	}
}