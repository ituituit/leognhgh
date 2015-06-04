package com.cheesemobile.util;

import java.io.InputStream;
import java.util.HashMap;

import com.weiyun.newhardware.parser.BaseParser;

import android.content.Context;

public class RequestVoNews {

	public RequestVoNews(String requestUrl, Context context,
			BaseParser<?> xmlParser) {
		super();
		this.requestUrl = requestUrl;
		this.context = context;
		this.xmlParser = xmlParser;
	}

	public String requestUrl;
	public Context context;
	public BaseParser<?> xmlParser;

	public BaseParser<?> getXmlParser() {
		return xmlParser;
	}

}
