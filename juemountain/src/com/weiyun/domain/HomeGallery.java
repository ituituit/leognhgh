package com.weiyun.domain;

import java.io.Serializable;

public class HomeGallery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1513477780926133935L;

	public String id;
	public String title;
	public String pic;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
}
