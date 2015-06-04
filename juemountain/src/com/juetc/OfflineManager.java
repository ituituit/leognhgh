package com.juetc;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.cheese.db.QueryDao;
import com.cheesemobile.util.RequestVoNews;
import com.weiyun.domain.DocUnit;
import com.weiyun.domain.channel.entity.ChannelItemBean;
import com.weiyun.domain.channel.entity.ChannelList;
import com.weiyun.domain.channel.entity.ChannelListUnit;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.newhardware.parser.DocBodyParser;
import com.weiyun.newhardware.parser.HomeListParser;
import com.weiyun.news.BaseService;
import com.weiyun.news.DetailActivity;
import com.weiyun.news.HomeActivity;

public class OfflineManager extends BaseService {
	public static final String ACTION_SERVICE = "com.weiyun.OfflineManager";
	private float progress = .0f;
	ProgressChangedListener mProgressListener;
	private NotificationManager notiManager;
	private int level = 0;
	private int currentLevel = 0;

	public void setProgressChangedListener(ProgressChangedListener listener) {
		this.mProgressListener = listener;
	}

	public interface ProgressChangedListener {
		public void inProgress(float progress);

		public void finished();
	}

	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public void setProgress() {
		currentLevel++;
		progress = currentLevel / (.0f + level);
		setProgress(progress);
	}

	public void setProgress(float progress) {
		this.progress = progress;
		if (null != mProgressListener) {
			mProgressListener.inProgress(progress);
			if (progress >= 1.0f) {
				mProgressListener.finished();
				resetProgress();
			}
		}
	}

	public void resetProgress() {
		progress = .0f;
		currentLevel = 0;
		level = 0;
	}

	public static boolean isServiceRunning(Context context, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public class LocalBinder extends Binder {
		public OfflineManager getService() {
			return OfflineManager.this;
		}
	}

	public static void saveChannelItem(Context context,
			ChannelItemBean currentNews) {
		QueryDao dao = new QueryDao(context);
		if (dao.query(currentNews.getDocumentId()) == null) {
			ChannelItemBean query = dao.query(currentNews.getDocumentId());
			dao.insert(currentNews,false);
		}
	}

	public static void saveChannelItem(Context context,
			ArrayList<ChannelItemBean> currentNews) {
		for (ChannelItemBean channelItemBean : currentNews) {
			saveChannelItem(context, channelItemBean);
		}
	}

	private void saveDetail(Context context,
			ArrayList<ChannelItemBean> currentNews) {
		for (ChannelItemBean channelItemBean : currentNews) {
			final String src = channelItemBean.getId();
			RequestVoNews reqVoDetail = new RequestVoNews(src, context,
					new DocBodyParser(new DataCallback<DocUnit>() {
						@Override
						public void processData(DocUnit paramObject,
								boolean paramBoolean) {
							DetailActivity.store(src, paramObject);
							setProgress();
						}
					}));
			getDataFromServer(reqVoDetail);
		}
	}

	public void allList(final Context ctx) {
		RequestVoNews allListVo = new RequestVoNews("", ctx,
				new HomeListParser(new DataCallback<ChannelList>() {
					@Override
					public void processData(ChannelList paramObject,
							boolean paramBoolean) {
						ChannelListUnit unit0 = paramObject.getClu().get(0);
						ArrayList<ChannelItemBean> item = unit0.getItem();
						saveChannelItem(ctx, item);
						saveDetail(ctx, item);
						level += item.size();
						setProgress();
					}
				}));
		ArrayList<String> strs = HomeActivity.getStrs(ctx);
		for (String pageChannelName : strs) {
			String homeListUrl = CommentsManager.getHomeListUrl(
					pageChannelName, 0);
			allListVo.requestUrl = homeListUrl;
			getDataFromServer(allListVo);
		}
		resetProgress();
		level = strs.size();
	}
}
