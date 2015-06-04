package com.weiyun.domain.gallery.entity;

import java.io.Serializable;
import java.util.List;

public class NewsGalleryUnit implements Serializable {
	private static final long serialVersionUID = 7232769716474524969L;
	private SlideItemList body;
	private Meta meta = new Meta();

	public Meta getMeta() {
		return this.meta;
	}

	public void setMeta(Meta paramMeta) {
		this.meta = paramMeta;
	}

	public SlideItemList getBody() {
		return body;
	}

	public List<SlideItem> getBodyList() {
		return body.getSlides();
	}

	public void setBody(SlideItemList body) {
		this.body = body;
	}

}

