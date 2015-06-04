package com.weiyun.news;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;

import com.cheesemobile.util.AccessUtil;
import com.cheesemobile.util.Constants;
import com.cheesemobile.util.NetApis;
import com.cheesemobile.util.RequestVoNews;
import com.cheesemobile.util.ThreadPoolManager;
import com.juetc.news.R;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.util.SharaedPreferences;

public class BaseActivity extends Activity {
	private ThreadPoolManager threadPoolManager;

	public BaseActivity() {
		threadPoolManager = ThreadPoolManager.getInstance();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(SharaedPreferences.getTheme(getApplicationContext()));
		// refreshTheme(getApplicationContext());
	}

	public void getDataFromServer(RequestVoNews reqVo) {
		BaseHandler handler = new BaseHandler(this, reqVo.getXmlParser()
				.getCallback(), reqVo);
		BaseTask taskThread = new BaseTask(this, reqVo, handler);
		this.threadPoolManager.addTask(taskThread);
	}

	@SuppressWarnings("unchecked")
	class BaseHandler extends Handler {
		private Context context;
		private DataCallback callBack;
		private RequestVoNews reqVo;

		public BaseHandler(Context context, DataCallback callBack,
				RequestVoNews reqVo) {
			this.context = context;
			this.callBack = callBack;
			this.reqVo = reqVo;
		}

		public void handleMessage(Message msg) {
			if (msg.what == Constants.NET_CONNECTED) {
				if (msg.obj == null) {
					if (null != netErrorListener) {
						netErrorListener.onNullObjectReturned();
					}
				} else if (msg.obj.getClass().isArray()) {
					if (((List<?>) msg.obj).size() == 0) {
						netErrorListener.onNullObjectReturned();
					}
				} else {
					callBack.processData(msg.obj, true);
				}
			} else if (msg.what == Constants.NET_FAILED) {
				if (null != netErrorListener) {
					netErrorListener.onNetFailedReceived(false);
				}
			}
		}
	}

	private NetErrorListener netErrorListener;

	interface NetErrorListener {
		void onNetFailedReceived(boolean hasNetWork);

		void onNullObjectReturned();
	}

	public void addNetErrorListener(NetErrorListener listener) {
		netErrorListener = listener;
	}

	class BaseTask implements Runnable {
		private Context context;
		private RequestVoNews reqVo;
		private Handler handler;

		public BaseTask(Context context, RequestVoNews reqVo, Handler handler) {
			this.context = context;
			this.reqVo = reqVo;
			this.handler = handler;
		}

		@Override
		public void run() {
			Object obj = null;
			Message msg = new Message();
			boolean network = true;
			network = AccessUtil.hasNetwork(context);
			Log.i("", "hasnetwork:" + network);
			if (network) {
				obj = NetApis.getByApache(reqVo);
				msg.what = Constants.NET_CONNECTED;
				msg.obj = obj;
				handler.sendMessage(msg);
			} else {
				msg.what = Constants.NET_FAILED;
				msg.obj = obj;
				handler.sendMessage(msg);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		BaseActivity.themeResetActivity(this, this.getClass());
	}

	//
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (requestCode == HomeActivity.REQUEST_CODE_LOGIN) {
	//
	// }
	// }

	public static void themeResetActivity(Activity context, Class c) {
		boolean themeChanged = SharaedPreferences.getTheme(context) != BaseActivity
				.getThemeId(context);
		if (themeChanged) {
			resetActivity(context, c);
		}
	}

	public static void resetActivity(Activity context, Class c) {
		Intent intent = new Intent(context, c);
		if (context.getIntent().getExtras() != null) {
			intent.putExtras(context.getIntent().getExtras());
		}
		if(context.getClass().equals(BaseActivity.class))
		((BaseActivity)context).onResetActivity();
		context.startActivity(intent);
		context.finish();
		context.overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public void onResetActivity(){
		
	}
	
	public static int getThemeId(Activity context) {
		TypedValue outValue = new TypedValue();
		context.getTheme().resolveAttribute(R.attr.themeName, outValue, true);
		if ("DarkTheme".equals(outValue.string)) {
			return R.style.DarkTheme;
		} else {
			return R.style.AppTheme;
		}
	}
}
