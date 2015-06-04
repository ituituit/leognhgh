package com.weiyun.newhardware.parser;

import java.util.List;

import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.weiyun.domain.QRCodeBean;
import com.weiyun.domain.channel.entity.ChannelList;
import com.weiyun.domain.channel.entity.ChannelListUnit;
import com.weiyun.domain.comment.entity.CommentsData;

public class QRCodeParser extends BaseParser<QRCodeBean> {
	private DataCallback<QRCodeBean> callbackVerifyResult;

	public QRCodeParser(DataCallback<QRCodeBean> dataCallback) {
		super();
		this.callbackVerifyResult = dataCallback;
	}

	@Override
	public QRCodeBean parseData(String bodyFromServer) throws JSONException {
		QRCodeBean commentsData;
		commentsData = JSON.parseObject(bodyFromServer, QRCodeBean.class);
		return commentsData;
	}

	@Override
	public DataCallback<QRCodeBean> getCallback() {
		return callbackVerifyResult;
	}

}