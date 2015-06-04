package com.cheesemobile.util;

import java.io.InputStream;
import java.util.HashMap;

import com.weiyun.newhardware.parser.BaseParser;

import android.content.Context;

public class RequestVo {

	public RequestVo(String requestUrl, int requestUrlid, Context context,
			String requestDataString, BaseParser<?> xmlParser) {
		super();
		this.requestUrl = requestUrl;
		this.requestUrlid = requestUrlid;
		this.context = context;
		this.requestDataString = requestDataString;
		this.xmlParser = xmlParser;
	}

	public String requestUrl;
	public int requestUrlid;
	public Context context;
	public String requestDataString;
	// public InputStream requestDataStream;
	// public HashMap<String,String> requestDataMap;
	public BaseParser<?> xmlParser;

	public BaseParser<?> getXmlParser() {
		return xmlParser;
	}

}
