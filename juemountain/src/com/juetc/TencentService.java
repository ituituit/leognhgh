package com.juetc;

import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cheesemobile.util.Constants;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weiyun.domain.QQLoginInfo;
import com.weiyun.domain.QQLoginStatus;
import com.weiyun.util.SharaedPreferences;
import com.weiyun.util.TecentLoginUtil;

public class TencentService {
	public Tencent mTencent;
	private static TencentService instance;
	private Context context;
	private QQLoginStatus qqLoginStatus;
	private QQLoginInfo qqLoginInfo;

	private TencentService(Context context) {
		super();
		this.context = context;
	}

	public static TencentService getInstance(Context context) {
		if (instance == null) {
			instance = new TencentService(context);
		}
		return instance;
	}

	public void writeUserInfo() {
		// Editor editor = preferences.edit();
		// editor.putString("uid", qqLoginStatus.getOpenid());
		// editor.putString("token", qqLoginStatus.getAccess_token());
		// editor.putString("thumbnials", qqLoginInfo.getFigureurl());
		// editor.putString("nickname", qqLoginInfo.getNickname());
		// editor.putString("username", "qzone" + qqLoginStatus.getOpenid());
		// editor.commit();
		com.weiyun.domain.UserInfo userInfo = new com.weiyun.domain.UserInfo();
		userInfo.setOpenId(qqLoginStatus.getOpenid());
		userInfo.setToken(qqLoginStatus.getAccess_token());
		userInfo.setImgUrl(qqLoginInfo.getFigureurl());
		userInfo.setNickname(qqLoginInfo.getNickname());
		userInfo.setUserName("qzone" + qqLoginStatus.getOpenid());
		writeUserInfo(userInfo);
	}

	public void writeUserInfo(com.weiyun.domain.UserInfo info) {
		SharaedPreferences.writeUserInfo(context, info);
	}

	public static String register(String email, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.register);
		sb.append("user.email=");
		sb.append(email);
		sb.append("&user.nickname=");
		sb.append(email);
		sb.append("&user.password=");
		sb.append(password);
		return sb.toString();
	}

	public static String login(String email, String password) {
		// http://localhost:8080/javaweb/user/checkUser?user.email=asdf&user.password=asfd&uri=
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.login);
		sb.append("user.email=");
		sb.append(email);
		sb.append("&user.password=");
		sb.append(password);
		return sb.toString();
	}

	public void uploadToServer() {
		// http://localhost:8080/javaweb/user/register?user.email=3155654543%40qq.com&user.nickname=itu&user.password=summerhost&password1=summerhost&number=%E8%A5%BFadg%E5%8C%97&submit=%E6%B3%A8+%E5%86%8C
		String url = com.cheesemobile.util.Constants.uploadUserInfo;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"account", Context.MODE_PRIVATE);
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

	}

	public boolean isLogin() {
		if (mTencent == null) {
			Map<String, String> loginInfo = SharaedPreferences
					.getLoginInfo(context);
			if (loginInfo == null) {
				return false;
			}
			String string = loginInfo.get("userName");
			if (string.length() != 0 || (!string.equals("") && string != null)) {
				return true;
			} else {
				return false;
			}
		} else {
			return mTencent.isSessionValid();
		}
		// if (!mTencent.isSessionValid()) {
		// mTencent.login(this, "all", loginListener);
		// Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" +
		// SystemClock.elapsedRealtime());
		// } else {
		// mTencent.logout(this);
		// updateUserInfo();
		// updateLoginButton();
		// }
		// if(mTencent.getAccessToken()){
		//
		// }
		// return false;
	}

	public void logout(Activity activity) {
		SharaedPreferences.clearLoginInfo(activity);
		if (mTencent != null) {
			mTencent.logout(activity);
		}
	}

	public void init(com.weiyun.domain.UserInfo info) {
		writeUserInfo(info);
	}

	public void init(final Activity activity) {
		if (mTencent == null) {
			mTencent = Tencent.createInstance(Constants.APPID, context);
		}
		IUiListener loginListener = new BaseUiListener(activity) {
			@Override
			public void doComplete(JSONObject values) {
				Log.d("SDKQQAgentPref",
						"AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
				qqLoginStatus = JSON.parseObject(values.toString(),
						QQLoginStatus.class);
				getLoginInfo(activity);
				if (listener != null) {
					listener.doComplete(values, TencentService.LOGIN);
				}
			}
		};
		mTencent.login(activity, "all", loginListener);
	}

	public void getLoginInfo(Activity activity) {
		String str = "";
		UserInfo mInfo = new UserInfo(context, mTencent.getQQToken());
		IUiListener loginListener = new BaseUiListener(activity) {

			@Override
			public void doComplete(JSONObject values) {
				Log.d("SDKQQAgentPref",
						"AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
				qqLoginInfo = JSON.parseObject(values.toString(),
						QQLoginInfo.class);
				writeUserInfo();
				if (listener != null) {
					listener.doComplete(values, TencentService.GET_INFO);
				}
			}
		};
		mInfo.getUserInfo(loginListener);
	}

	private TencentListener listener;

	public void addListener(TencentListener listener) {
		this.listener = listener;
	}

	public final static int LOGIN = 1;
	public final static int GET_INFO = 2;

	public interface TencentListener {
		public void doComplete(JSONObject values, int token);
	}

	public class BaseUiListener implements IUiListener {

		private Activity context;

		public BaseUiListener(Activity context) {
			super();
			this.context = context;
		}

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				// TecentLoginUtil.showResultDialog(context, "返回为空", "登录失败");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				// TecentLoginUtil.showResultDialog(context, "返回为空", "登录失败");
				return;
			}
			// TecentLoginUtil.showResultDialog(context,
			// response.toString(),"登录成功");
			doComplete((JSONObject) response);
		}

		public void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			TecentLoginUtil.toastMessage(context, "onError: " + e.errorDetail);
			TecentLoginUtil.dismissDialog();
		}

		@Override
		public void onCancel() {
			TecentLoginUtil.toastMessage(context, "onCancel: ");
			TecentLoginUtil.dismissDialog();
		}
	}

}