package com.weiyun.newhardware.parser;

import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.weiyun.domain.DocUnit;


public class DocBodyParser extends BaseParser<DocUnit> {
	private DataCallback<DocUnit> callbackVerifyResult;
	public DocBodyParser(DataCallback<DocUnit> dataCallback) {
		super();
		this.callbackVerifyResult = dataCallback;
	}
	
	@Override
	public DocUnit parseData(String bodyFromServer)
			throws JSONException {
		DocUnit commentsData;
		commentsData =  JSON.parseObject(bodyFromServer, DocUnit.class);
		return commentsData;
	}

	@Override
	public DataCallback<DocUnit> getCallback() {
		return callbackVerifyResult;
	}

}