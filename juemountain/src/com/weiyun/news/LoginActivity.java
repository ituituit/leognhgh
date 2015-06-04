package com.weiyun.news;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.cheesemobile.util.ImageUtil;
import com.cheesemobile.util.ImageUtil.ImageCallback;
import com.juetc.OfflineManager;
import com.juetc.OfflineManager.LocalBinder;
import com.juetc.OfflineManager.ProgressChangedListener;
import com.juetc.TencentService;
import com.juetc.TencentService.TencentListener;
import com.juetc.news.R;
import com.weiyun.domain.HomeBean;
import com.weiyun.listener.LoginListener;
import com.weiyun.listener.RegisterListener;
import com.weiyun.util.SharaedPreferences;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private boolean mBack = false;
	int MSG_LOGIN = 0;
	public static int RESULT_CODE_CONFIG_CHANGED = 0;
	public static int MSG_HOME_RETURN = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_LOGIN) {
				flushView();
				didLogin();
			}
		}
	};

	private void didLogin() {
		if (mBack) {
			finish();
		}
	}

	public static void getImage(final ImageView imageView, String imageUrl) {
		String imagePath = ImageUtil.getCacheImgPath().concat(
				ImageUtil.md5(imageUrl));
		Bitmap bitmap = ImageUtil.loadImage(imagePath, imageUrl,
				new ImageCallback() {
					@Override
					public void loadImage(Bitmap bitmap, String imagePath) {
						if (imageView != null) {
							imageView.setImageBitmap(bitmap);
						}
					}
				});
		imageView.setTag(imagePath);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(R.drawable.icon_app);
		}
	}

	private TextView accountNameText;
	private LinearLayout login_status;
	private RelativeLayout login_switch;
	private TextView loginTitle;

	public void flushView() {
		if (!TencentService.getInstance(this).isLogin()) {
			login_status.setVisibility(View.GONE);
			login_switch.setVisibility(View.VISIBLE);
			loginTitle.setText(R.string.login_title);
		} else {
			login_status.setVisibility(View.VISIBLE);
			login_switch.setVisibility(View.GONE);
			loginTitle.setText(R.string.login_ready);
		}

		SharedPreferences sharedPreferences = getSharedPreferences("account",
				Context.MODE_PRIVATE);
		String openId = sharedPreferences.getString("uid", "");
		String token = sharedPreferences.getString("token", "");
		String imgUrl = sharedPreferences.getString("thumbnials", "");
		String nickname = sharedPreferences.getString("nickname", "默认用户");
		String userName = sharedPreferences.getString("username", "默认用户");
		accountNameText.setText(nickname);
		getImage(account_img, imgUrl);
		detailSettingView.flush();
	}

	public void sendLoginMSG() {
		Message message = new Message();
		message.what = MSG_LOGIN;
		handler.sendMessage(message);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_setting);
		mBack = getIntent().getBooleanExtra("mBack", false);
		ImageButton login = (ImageButton) findViewById(R.id.qq_login_button);
		ImageButton weixin = (ImageButton) findViewById(R.id.weixin_login_button);
		ImageView nightmode_open = (ImageView) findViewById(R.id.setting_nightmode_open);
		TextView nightmode = (TextView) findViewById(R.id.setting_nightmode_text);
		ImageView offline_open = (ImageView) findViewById(R.id.setting_offline_open);
		TextView offline = (TextView) findViewById(R.id.setting_offline_text);

		account_img = (ImageView) findViewById(R.id.account_avatar);
		accountNameText = (TextView) findViewById(R.id.account_name);
		login_status = (LinearLayout) findViewById(R.id.login_status);
		login_switch = (RelativeLayout) findViewById(R.id.login_switch);
		loginTitle = (TextView) findViewById(R.id.login_title);
		detailSettingView = (DetailSettingView) findViewById(R.id.detail_setting_view);
		detailSettingView.setRegisterListener(new RegisterListener(this));
		detailSettingView.setLoginListener(new LoginListener(this));

		login.setOnClickListener(this);
		weixin.setOnClickListener(this);
		nightmode_open.setOnClickListener(this);
		nightmode.setOnClickListener(this);
		offline_open.setOnClickListener(this);
		offline.setOnClickListener(this);
		account_img.setOnClickListener(this);
		accountNameText.setOnClickListener(this);
		ArrayList<String> show = SharaedPreferences.getChannel(this, true);
		ArrayList<String> defaultOrderMenuItems = HomeBean.subscriptions
				.getDefaultOrderMenuItems();
		if (show != null) {
			detailSettingView.setChannelList(true, show);
			ArrayList<String> hideChannel = SharaedPreferences.getChannel(this,
					false);
			ArrayList<String> clone = (ArrayList<String>) defaultOrderMenuItems
					.clone();
			clone.removeAll(show);
			clone.removeAll(hideChannel);
			hideChannel.addAll(clone);
			detailSettingView.setChannelList(false, hideChannel);
		} else {
			detailSettingView.setChannelList(true, defaultOrderMenuItems);
		}
		flushView();
	}

	@Override
	protected void onPause() {
		ArrayList<String> channelShow = detailSettingView.getChannelShow(true);
		ArrayList<String> channelHide = detailSettingView.getChannelShow(false);
		SharaedPreferences.setChannel(getApplicationContext(), channelShow,
				channelHide);
		super.onPause();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	TencentListener tencentListener = new TencentListener() {
		@Override
		public void doComplete(JSONObject values, int token) {
			if (token == TencentService.LOGIN) {
			}
			if (token == TencentService.GET_INFO) {
				sendLoginMSG();
			}
		}
	};
	private ImageView account_img;
	private DetailSettingView detailSettingView;

	@Override
	public void onBackPressed() {
		boolean customed = detailSettingView.onBackPressed();
		if (!customed) {
			super.onBackPressed();
		}
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.qq_login_button:
			TencentService instance = TencentService.getInstance(this);
			instance.addListener(tencentListener);
			if (!instance.isLogin()) {
				instance.init(this);
			}
			break;
		case R.id.weixin_login_button:
			break;
		case R.id.setting_offline_open:
		case R.id.setting_offline_text:
			if (!OfflineManager.isServiceRunning(this,
					OfflineManager.ACTION_SERVICE)) {
				doBindService();
			}
			String str = getResources().getString(R.string.loading);
			showNotification(R.drawable.icon_app, str, str, "开始下载离线数据");
			break;
		case R.id.setting_nightmode_open:
		case R.id.setting_nightmode_text:
			if (SharaedPreferences.getTheme(getApplicationContext()) == R.style.AppTheme) {
				SharaedPreferences.setTheme(getApplicationContext(),
						R.style.DarkTheme);
			} else {
				SharaedPreferences.setTheme(getApplicationContext(),
						R.style.AppTheme);
			}
			intent = new Intent(this, LoginActivity.class);
			startActivityForResult(intent, HomeActivity.REQUEST_CODE_LOGIN);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		case R.id.account_avatar:
		case R.id.account_name:
			intent = new Intent(this, LogOutActivity.class);
			startActivityForResult(intent, 0);
			break;
		}
	}

	private OfflineManager mService;
	private boolean mBound = false;
	private ServiceConnection mConnection = new ServiceConnection() {
		// Called when the connection with the service is established
		public void onServiceConnected(ComponentName className, IBinder service) {
			// Because we have bound to an explicit
			// service that is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			LocalBinder binder = (OfflineManager.LocalBinder) service;
			mService = binder.getService();
			mBound = true;
			mService.setProgressChangedListener(new ProgressChangedListener() {
				@Override
				public void inProgress(float progress) {
				}

				@Override
				public void finished() {
					String str = getResources().getString(
							R.string.offline_complete);
					showNotificationComplete(R.drawable.icon_app, str, str,
							"离线数据已保存");
					doUnbindService();
				}
			});
			mService.allList(LoginActivity.this);
		}

		// Called when the connection with the service disconnects unexpectedly
		public void onServiceDisconnected(ComponentName className) {
			mBound = false;
		}
	};

	void doBindService() {
		// Establish a connection with the service. We use an explicit
		// class name because we want a specific service implementation that
		// we know will be running in our own process (and thus won't be
		// supporting component replacement by other applications).
		Intent intent = new Intent(LoginActivity.this, OfflineManager.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		mBound = true;
	}

	void doUnbindService() {
		if (mBound) {
			// LocalService service = mService.getService();
			// if (service != null) Log.d("doUnbindService", "service: " +
			// service);
			// int statusCode = mService.getStatusCode();
			// if (statusCode != 0) Log.d("doUnbindService", "statusCode: " +
			// statusCode);
			// Detach our existing connection.
			unbindService(mConnection);
			mBound = false;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}

	int notification_id = 19172439;

	public void showNotification(int icon, String tickertext, String title,
			String content) {
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Notification管理器
		Notification notification = new Notification(icon, tickertext,
				System.currentTimeMillis());
		// 后面的参数分别是显示在顶部通知栏的小图标，小图标旁的文字（短暂显示，自动消失）系统当前时间（不明白这个有什么用）
		notification.defaults = Notification.DEFAULT_LIGHTS;
		// 这是设置通知是否同时播放声音或振动，声音为Notification.DEFAULT_SOUND
		// 振动为Notification.DEFAULT_VIBRATE;
		// Light为Notification.DEFAULT_LIGHTS，在我的Milestone上好像没什么反应
		// 全部为Notification.DEFAULT_ALL
		// 如果是振动或者全部，必须在AndroidManifest.xml加入振动权限
		PendingIntent pt = PendingIntent.getActivity(this, 0, new Intent(this,
				LoginActivity.class), 0);
		// 点击通知后的动作，这里是转回main 这个Acticity
		// notification.setLatestEventInfo(this, title, content, pt);
		RemoteViews localRemoteViews = new RemoteViews(getPackageName(),
				R.layout.notifi_downloading);
		notification.contentIntent = pt;
		notification.contentView = localRemoteViews;
		nm.notify(notification_id, notification);
	}

	public void showNotificationComplete(int icon, String tickertext,
			String title, String content) {
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Notification管理器
		Notification notification = new Notification(icon, tickertext,
				System.currentTimeMillis());
		// 后面的参数分别是显示在顶部通知栏的小图标，小图标旁的文字（短暂显示，自动消失）系统当前时间（不明白这个有什么用）
		notification.defaults = Notification.DEFAULT_LIGHTS;
		// 这是设置通知是否同时播放声音或振动，声音为Notification.DEFAULT_SOUND
		// 振动为Notification.DEFAULT_VIBRATE;
		// Light为Notification.DEFAULT_LIGHTS，在我的Milestone上好像没什么反应
		// 全部为Notification.DEFAULT_ALL
		// 如果是振动或者全部，必须在AndroidManifest.xml加入振动权限
		PendingIntent pt = PendingIntent.getActivity(this, 0, new Intent(this,
				LoginActivity.class), 0);
		// 点击通知后的动作，这里是转回main 这个Acticity
		notification.setLatestEventInfo(this, title, content, pt);
		// RemoteViews localRemoteViews = new RemoteViews(getPackageName(),
		// R.layout.notifi_downloading);
		// notification.contentIntent = pt;
		// notification.contentView = localRemoteViews;
		nm.notify(notification_id, notification);
	}

	public final void notifyPush() {
		// this.A.setProgress(0);
		// this.x.setVisibility(View.INVISIBLE);
		// this.s.setImageResource(2130838240);
		NotificationManager localNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Intent localIntent = new Intent();
		localIntent.setAction("android.intent.action.MAIN");
		localIntent.setComponent(new ComponentName(getPackageName(),
				BaseActivity.class.getName()));
		localIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		localIntent.addCategory("android.intent.category.LAUNCHER");
		PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0,
				localIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		RemoteViews localRemoteViews = new RemoteViews(getPackageName(),
				R.layout.notifi_complete);
		// localRemoteViews.reapply(context, v);
		Notification localNotification = new Notification();
		localNotification.contentView = localRemoteViews;
		localNotification.contentIntent = localPendingIntent;
		localNotificationManager.notify(0, localNotification);
	}

	// public final void setProgress(int paramInt)
	// {
	// if (paramInt == 0)
	// this.r.setText("0%");
	// if (this.x.getVisibility() != 0)
	// {
	// this.x.setVisibility(0);
	// this.s.setImageResource(2130838221);
	// }
	// if (paramInt == 100)
	// {
	// this.x.setVisibility(4);
	// this.s.setImageResource(2130838240);
	// }
	// this.r.setText(paramInt + "%");
	// this.A.setProgress(paramInt);
	// }

	public static final int resultCodeChanged = 1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == resultCodeChanged) {
			flushView();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
