package com.weiyun.newhardware.parser;

import java.util.List;

import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.weiyun.domain.QRCodeBean;
import com.weiyun.domain.UserInfo;
import com.weiyun.domain.VerifyResult;
import com.weiyun.domain.channel.entity.ChannelList;
import com.weiyun.domain.channel.entity.ChannelListUnit;
import com.weiyun.domain.comment.entity.CommentsData;

public class UserInfoParser extends BaseParser<UserInfo> {
	private DataCallback<UserInfo> callbackVerifyResult;

	public UserInfoParser(DataCallback<UserInfo> dataCallback) {
		super();
		this.callbackVerifyResult = dataCallback;
	}

	@Override
	public UserInfo parseData(String bodyFromServer) throws JSONException {
		UserInfo commentsData;
		commentsData = JSON.parseObject(bodyFromServer, UserInfo.class);
		VerifyResultParser vrp = new VerifyResultParser(null);
		VerifyResult parseData = vrp.parseData(commentsData.getErrorMsg());
		commentsData.setVr(parseData);
		return commentsData;
	}

	@Override
	public DataCallback<UserInfo> getCallback() {
		return callbackVerifyResult;
	}

}