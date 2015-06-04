package com.weiyun.domain.channel.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChannelListUnit implements Serializable {
	private static final long serialVersionUID = -8399701415360593005L;
	private static int TYPE_HEADLINE = 0;
	private static int TYPE_RESIDENT = 1;
	private static int TYPE_COMMERCIALS = 2;
	private static int TYPE_BUILD = 3;
	private static int TYPE_CONVENIENT = 4;
	private static int TYPE_SOCIETY = 5;
	private static int TYPE_IMAGE = 6;
	private static int TYPE_TRAVEL = 7;
	private static int TYPE_FOOD = 8;
	private static int TYPE_SHOPPING = 9;
	private static int TYPE_EMOTION = 10;

	public String stringType(int type) {
		if (type == TYPE_HEADLINE) {
			return "头条";
		}
		if (type == TYPE_RESIDENT) {
			return "民生";
		}
		if (type == TYPE_COMMERCIALS) {
			return "公告";
		}
		if (type == TYPE_BUILD) {
			return "城建";
		}
		if (type == TYPE_CONVENIENT) {
			return "便民";
		}
		if (type == TYPE_SOCIETY) {
			return "社会";
		}
		if (type == TYPE_IMAGE) {
			return "美图";
		}
		if (type == TYPE_TRAVEL) {
			return "旅游";
		}
		if (type == TYPE_FOOD) {
			return "美食";
		}
		if (type == TYPE_SHOPPING) {
			return "购物";
		}
		if (type == TYPE_EMOTION) {
			return "情感";
		}
		return "";
	}

	public static int intTypeC(String type) {
		if (type.equals("头条")) {
			return TYPE_HEADLINE;
		}
		if (type.equals("民生")) {
			return TYPE_RESIDENT;
		}
		if (type.equals("公告")) {
			return TYPE_COMMERCIALS;
		}
		if (type.equals("城建")) {
			return TYPE_BUILD;
		}
		if (type.equals("便民")) {
			return TYPE_CONVENIENT;
		}
		if (type.equals("社会")) {
			return TYPE_SOCIETY;
		}
		if (type.equals("美图")) {
			return TYPE_IMAGE;
		}
		if (type.equals("旅游")) {
			return TYPE_TRAVEL;
		}
		if (type.equals("美食")) {
			return TYPE_FOOD;
		}
		if (type.equals("购物")) {
			return TYPE_SHOPPING;
		}
		if (type.equals("情感")) {
			return TYPE_EMOTION;
		}
		return -1;
	}
	
	public static int intType(String type) {
		if (type.equals("headline")) {
			return TYPE_HEADLINE;
		}
		if (type.equals("resident")) {
			return TYPE_RESIDENT;
		}
		if (type.equals("commercials")) {
			return TYPE_COMMERCIALS;
		}
		if (type.equals("build")) {
			return TYPE_BUILD;
		}
		if (type.equals("convenient")) {
			return TYPE_CONVENIENT;
		}
		if (type.equals("society")) {
			return TYPE_SOCIETY;
		}
		if (type.equals("image")) {
			return TYPE_IMAGE;
		}
		if (type.equals("travel")) {
			return TYPE_TRAVEL;
		}
		if (type.equals("food")) {
			return TYPE_FOOD;
		}
		if (type.equals("shopping")) {
			return TYPE_SHOPPING;
		}
		if (type.equals("emotion")) {
			return TYPE_EMOTION;
		}
		return -1;
	}

	private String listId;
	private String type;
	private int expiredTime;
	private int currentPage;
	private ArrayList<ChannelItemBean> item;
	private int totalPage;
	private int sum;

	// private boolean comeFromClickUpdate;

	public int getCurrentPage() {
		return this.currentPage;
	}

	// public List<?> getData()
	// {
	// return this.item;
	// }

	public int getExpiredTime() {
		return this.expiredTime;
	}

	public ArrayList<ChannelItemBean> getItem() {
		return this.item;
	}

	public String getListId() {
		return this.listId;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public String getType() {
		return this.type;
	}

	// public boolean isComeFromClickUpdate()
	// {
	// return this.comeFromClickUpdate;
	// }
	//
	// public void setComeFromClickUpdate(boolean paramBoolean)
	// {
	// this.comeFromClickUpdate = paramBoolean;
	// }

	public void setCurrentPage(int paramInt) {
		this.currentPage = paramInt;
	}

	public void setExpiredTime(int paramInt) {
		this.expiredTime = paramInt;
	}

	public void setItem(ArrayList<ChannelItemBean> paramArrayList) {
		this.item = paramArrayList;
	}

	public void setListId(String paramString) {
		this.listId = paramString;
	}

	public void setTotalPage(int paramInt) {
		this.totalPage = paramInt;
	}

	public void setType(String paramString) {
		this.type = paramString;
	}
}

/*
 * Location: /Users/pwl/Desktop/classes-dex2jar.jar Qualified Name:
 * com.ifeng.news2.channel.entity.ChannelListUnit JD-Core Version: 0.6.2
 */