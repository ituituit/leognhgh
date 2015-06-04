package com.weiyun.newhardware.parser;

import java.util.List;

import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.weiyun.domain.channel.entity.ChannelList;
import com.weiyun.domain.channel.entity.ChannelListUnit;

public class HomeListParser extends BaseParser<ChannelList> {
	private DataCallback<ChannelList> callbackVerifyResult;
	public HomeListParser(DataCallback<ChannelList> dataCallback) {
		super();
		this.callbackVerifyResult = dataCallback;
	}
	
	@Override
	public ChannelList parseData(String bodyFromServer)
			throws JSONException {
		List<ChannelListUnit> obj;
		obj = JSON.parseArray(bodyFromServer, ChannelListUnit.class);
		ChannelList cl = new ChannelList();
		cl.setClu(obj);
		return cl;
	}

	@Override
	public DataCallback<ChannelList> getCallback() {
		return callbackVerifyResult;
	}

}
