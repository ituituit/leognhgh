package com.cheesemobile.util;

import java.io.InputStream;
import java.util.HashMap;

import com.weiyun.domain.Version;
import com.weiyun.newhardware.parser.BaseParser;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;

import android.content.Context;

public class RequestVoDownLoad {
	public RequestVoDownLoad(String requestUrl, Context context,DataCallback callback,String destPath) {
		super();
		this.requestUrl = requestUrl;
		this.context = context;
		this.destPath = destPath;
		callbackVerifyResult = callback;
	}

	public String requestUrl;
	public String destPath;
	public Context context;
	private DataCallback<String> callbackVerifyResult;

	public DataCallback<String> getCallback() {
		return callbackVerifyResult;
	}
}
