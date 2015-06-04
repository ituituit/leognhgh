package com.weiyun.newhardware.parser;

import java.util.List;

import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.weiyun.domain.channel.entity.ChannelList;
import com.weiyun.domain.channel.entity.ChannelListUnit;
import com.weiyun.domain.comment.entity.CommentsData;

public class CommentsParser extends BaseParser<CommentsData> {
	private DataCallback<CommentsData> callbackVerifyResult;

	public CommentsParser(DataCallback<CommentsData> dataCallback) {
		super();
		this.callbackVerifyResult = dataCallback;
	}

	@Override
	public CommentsData parseData(String bodyFromServer) throws JSONException {
		CommentsData commentsData;
		commentsData = JSON.parseObject(bodyFromServer, CommentsData.class);
		return commentsData;
	}

	@Override
	public DataCallback<CommentsData> getCallback() {
		return callbackVerifyResult;
	}

}