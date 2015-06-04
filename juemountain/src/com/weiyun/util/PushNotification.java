package com.weiyun.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;

import com.juetc.news.R;
import com.weiyun.news.BaseActivity;

public class PushNotification extends Activity{
	public final void complete() {
//		this.A.setProgress(0);
//		this.x.setVisibility(View.INVISIBLE);
//		this.s.setImageResource(2130838240);
		NotificationManager localNotificationManager = (NotificationManager) getSystemService("notification");
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
//		localRemoteViews
//				.setOnClickPendingIntent(2131296537, localPendingIntent);
		Notification localNotification = new Notification();
		localNotification.flags = (0x10 | localNotification.flags);
		localNotificationManager.notify(100, localNotification);
	}

	public final void failed() {
//		this.x.setVisibility(View.INVISIBLE);
//		this.s.setImageResource(2130838240);
	}

	public final void cancel() {
//		this.x.setVisibility(View.INVISIBLE);
//		this.s.setImageResource(2130838240);
	}
}
