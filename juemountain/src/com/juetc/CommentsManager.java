package com.juetc;

import java.net.URLEncoder;

import android.content.Context;
import android.content.SharedPreferences;

import com.cheesemobile.util.Constants;
import com.weiyun.domain.Channel;
import com.weiyun.domain.HomeBean;
import com.weiyun.domain.SubscriptionBean;
import com.weiyun.domain.channel.entity.ChannelListUnit;

public class CommentsManager {

	public static int pageSize = 20;

	public static final void getComments() {

	}

	public static String getUrl(String docUrl, int page, String type) {
//		String encode = URLEncoder.encode(docUrl);
		String encode = docUrl;
		type = "all";

		String url = HomeBean.getComment + "?pagesize=" + pageSize + "&p="
				+ page + "&docurl=" + encode + "&type=" + type;
		return url;
	}

	public static String getHomeListUrl(String pageChannelName, int page) {
		SubscriptionBean subscriptions = HomeBean.subscriptions;
		Channel channel = subscriptions.getChannels().get(pageChannelName);
		String channelUrl = channel.getChannelUrl();
		return channelUrl + "&page=" + page;
	}

	public static String getDefaultListUrl(String pageChannelName) {
		String galleryUrl = HomeBean.getNewGallery();
		int channel = ChannelListUnit.intTypeC(pageChannelName);
		int page = 1;
		return galleryUrl  +"?id=" + channel+ "&page=" + page;
	}

	public static class extData {
		private String docId;
		private String from;
		private String guid;
		private String nickname;
		private String type;

		public extData(String docId, String guid, String nickname) {
			super();
			this.docId = docId;
			this.guid = guid;
			this.nickname = nickname;
		}

		@Override
		public String toString() {
			return "{\"docId\":\"" + docId + "\",\"from\":\"sj\",\"guid\":\""
					+ guid + "\",\"nickname\":\"" + nickname
					+ "\",\"type\":\"doc\"}";
		}
	}

	public static String uploadStr(String docName,String docUrl,String commentContent,String docId,Context context){
//		String docName = "杨幂素颜穿拖鞋娘家放烟花 豪宅内景疑曝光";
//		String docUrl = "http://ent.ifeng.com/a/20141025/40339766_0.shtml";
		SharedPreferences sharedPreferences = context.getSharedPreferences("account",
				Context.MODE_PRIVATE);
		String openId = sharedPreferences.getString("uid", "");
		String token = sharedPreferences.getString("token", "");
		String imgUrl = sharedPreferences.getString("thumbnials", "");
		String nickname = sharedPreferences.getString("nickname", "默认用户");
		String userName = sharedPreferences.getString("username", "默认用户");
		
		openId = URLEncoder.encode(openId);
		token = URLEncoder.encode(token);
		imgUrl = URLEncoder.encode(imgUrl);
		nickname = URLEncoder.encode(nickname);
		userName = URLEncoder.encode(userName);
		docName = URLEncoder.encode(docName);
		docUrl = URLEncoder.encode(docUrl);
		commentContent = URLEncoder.encode(commentContent);
		docId = URLEncoder.encode(docId);
//		String userName = "40115055-8cc8-41db-b233-";
//		String extUserInfo = new extData(docId, "51385387", "用户5162740554").toString();
		String extUserInfo = new extData(docId, token, userName).toString();
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.sendComment + "&docName=");
		sb.append(docName);
		sb.append("&docUrl=");
		sb.append(docUrl);
		sb.append("&client=1&content=");
		sb.append(commentContent);
		sb.append("&rt=sj&userName=");
		sb.append(nickname);
		sb.append("&ext2=");
		sb.append(extUserInfo);
		return sb.toString();
	}

}
