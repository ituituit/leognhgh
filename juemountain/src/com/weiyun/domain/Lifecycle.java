package com.weiyun.domain;

import java.io.Serializable;

public class Lifecycle implements Serializable {
	private static final long serialVersionUID = 2774874286271967590L;
	private long end;
	private long start;

	public long getEnd() {
		return this.end;
	}

	public long getStart() {
		return this.start;
	}

	public void setEnd(long paramLong) {
		this.end = paramLong;
	}

	public void setStart(long paramLong) {
		this.start = paramLong;
	}
}

/*
 * Location: /Users/pwl/Desktop/classes-dex2jar.jar Qualified Name:
 * com.ifeng.news2.bean.Lifecycle JD-Core Version: 0.6.2
 */