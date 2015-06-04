package com.weiyun.newhardware.parser;

import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.weiyun.domain.gallery.entity.NewsGalleryUnit;

public class GalleryParser extends BaseParser<NewsGalleryUnit> {
	private DataCallback<NewsGalleryUnit> callbackVerifyResult;

	public GalleryParser(DataCallback<NewsGalleryUnit> dataCallback) {
		super();
		this.callbackVerifyResult = dataCallback;
	}

	@Override
	public NewsGalleryUnit parseData(String bodyFromServer)
			throws JSONException {
		NewsGalleryUnit obj = JSON.parseObject(bodyFromServer,
				NewsGalleryUnit.class);
		return obj;
	}

	@Override
	public DataCallback<NewsGalleryUnit> getCallback() {
		return callbackVerifyResult;
	}

}
