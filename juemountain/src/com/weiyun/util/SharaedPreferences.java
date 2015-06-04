package com.weiyun.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cheesemobile.util.Constants;
import com.juetc.news.R;
import com.weiyun.domain.QRCodeBean;

public class SharaedPreferences {
	public static final String KEY_CHANNEL_SHOW = "key_channle_show";
	public static final String KEY_CHANNEL_HIDE = "key_channle_hide";
	public static final String KEY_QR_CODE_OBJ = "qr_code";
	public static final String KEY_THEME_RESOURCE_ID = "theme_id";

	public static ArrayList<String> getChannel(Context context,
			boolean returnShow) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Constants.sharePreferenceChannelSetting, Context.MODE_PRIVATE);
		ArrayList<String> show = (ArrayList<String>) ObjectSerializer
				.deserialize(sharedPreferences
						.getString(KEY_CHANNEL_SHOW, null));
		ArrayList<String> hide = (ArrayList<String>) ObjectSerializer
				.deserialize(sharedPreferences
						.getString(KEY_CHANNEL_HIDE, null));
		if (returnShow) {
			return show;
		} else {
			return hide;
		}
	}

	public static void setTheme(Context context, int themeId) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Constants.sharePreferenceQRcode, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_THEME_RESOURCE_ID, themeId);
		editor.commit();
	}

	public static int getTheme(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Constants.sharePreferenceQRcode, Context.MODE_PRIVATE);
		return sharedPreferences
				.getInt(KEY_THEME_RESOURCE_ID, R.style.AppTheme);
	}

	public static void writeUserInfo(Context context,
			com.weiyun.domain.UserInfo info) {
		SharedPreferences preferences = context.getSharedPreferences("account",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("uid", info.getOpenId());
		editor.putString("token", info.getToken());
		editor.putString("thumbnials", info.getImgUrl());
		editor.putString("nickname", info.getNickname());
		editor.putString("username", info.getUserName());
		editor.commit();
	}

	public static Map<String, String> getLoginInfo(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"account", Context.MODE_PRIVATE);
		if (sharedPreferences == null) {
			return null;
		}
		String openId = sharedPreferences.getString("uid", "");
		String token = sharedPreferences.getString("token", "");
		String imgUrl = sharedPreferences.getString("thumbnials", "");
		String nickname = sharedPreferences.getString("nickname",
				context.getString(R.string.default_user));
		String userName = sharedPreferences.getString("username", "");
		Map<String, String> map = new HashMap<>();
		map.put("openId", openId);
		map.put("token", token);
		map.put("imgUrl", imgUrl);
		map.put("nickname", nickname);
		map.put("userName", userName);
		return map;
	}

	public static void clearLoginInfo(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"account", Context.MODE_PRIVATE);
		if (sharedPreferences == null) {
			return;
		}
		sharedPreferences.edit().clear().commit();
	}

	public static void setChannel(Context context,
			ArrayList<String> channelShow, ArrayList<String> channelhide) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Constants.sharePreferenceChannelSetting, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(SharaedPreferences.KEY_CHANNEL_SHOW,
				com.weiyun.util.ObjectSerializer.serialize(channelShow));
		editor.putString(SharaedPreferences.KEY_CHANNEL_HIDE,
				com.weiyun.util.ObjectSerializer.serialize(channelhide));
		editor.commit();
	}

	public static QRCodeBean getQRCode(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Constants.sharePreferenceQRcode, Context.MODE_PRIVATE);
		QRCodeBean b = (QRCodeBean) ObjectSerializer
				.deserialize(sharedPreferences.getString(KEY_QR_CODE_OBJ, null));
		if (b == null || b.getCodenum() == null) {
			return null;
		}
		return b;
	}

	public static void setQRCode(Context context, QRCodeBean bean) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Constants.sharePreferenceQRcode, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_QR_CODE_OBJ,
				com.weiyun.util.ObjectSerializer.serialize(bean));
		editor.commit();
	}
}
