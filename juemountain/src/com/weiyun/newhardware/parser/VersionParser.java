package com.weiyun.newhardware.parser;

import com.alibaba.fastjson.JSON;
import com.weiyun.domain.Version;

public class VersionParser extends BaseParser<Version>  {
	private DataCallback<Version> callbackVerifyResult;

	public VersionParser(DataCallback<Version> callbackVerifyResult) {
		super();
		this.callbackVerifyResult = callbackVerifyResult;
	}

	public Version parseData(String bodyFromServer) {
		Version vr = JSON.parseObject(bodyFromServer, Version.class);
		return vr;
	}

	@Override
	public DataCallback<Version> getCallback() {
		return callbackVerifyResult;
	}
}
