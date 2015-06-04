package com.weiyun.news;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.cheesemobile.util.AccessUtil;
import com.cheesemobile.util.Constants;
import com.cheesemobile.util.NetApis;
import com.cheesemobile.util.RequestVoDownLoad;
import com.cheesemobile.util.RequestVoNews;
import com.cheesemobile.util.ThreadPoolManager;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;

public class BaseService extends Service {
	private ThreadPoolManager threadPoolManager;

	public void getDataFromServer(RequestVoNews reqVo) {
		BaseHandler handler = new BaseHandler(this, reqVo.getXmlParser()
				.getCallback(), reqVo);
		BaseTask taskThread = new BaseTask(reqVo.context, reqVo, handler);
		threadPoolManager = ThreadPoolManager.getInstance();
		this.threadPoolManager.addTask(taskThread);
	}

	public void downloadFromServer(RequestVoDownLoad reqVo) {
		BaseHandler handler = new BaseHandler(this, reqVo.getCallback(), null);
		DownLoadTask taskThread = new DownLoadTask(this, reqVo, handler);
		threadPoolManager = ThreadPoolManager.getInstance();
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
		protected Context context;
		private RequestVoNews reqVo;
		protected Handler handler;

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

	class DownLoadTask extends BaseTask {
		RequestVoDownLoad reqVo;

		public DownLoadTask(Context context, RequestVoDownLoad reqVo,
				Handler handler) {
			super(context, null, handler);
			this.reqVo = reqVo;
		}

		@Override
		public void run() {
			Object obj = null;
			Message msg = new Message();
			boolean network = true;
			network = AccessUtil.hasNetwork(context);
			Log.i("", "hasnetwork:" + network);
			if (network) {
				NetApis.download(reqVo.requestUrl, reqVo.destPath);
				msg.what = Constants.NET_CONNECTED;
				msg.obj = "success";
				handler.sendMessage(msg);
			} else {
				msg.what = Constants.NET_FAILED;
				msg.obj = obj;
				handler.sendMessage(msg);
			}
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
