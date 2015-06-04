package com.weiyun.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juetc.news.R;

public class RefreshView extends RelativeLayout {
	/**
	 * 下拉状态
	 */
	public static final int STATUS_PULL_TO_REFRESH = 0;
	/**
	 * 释放立即刷新状态
	 */
	public static final int STATUS_RELEASE_TO_REFRESH = 1;
	/**
	 * 正在刷新状态
	 */
	public static final int STATUS_REFRESHING = 2;
	/**
	 * 刷新完成或未刷新状态
	 */
	public static final int STATUS_REFRESH_FINISHED = 3;
	/**
	 * 下拉头部回滚的速度
	 */
	public static final int SCROLL_SPEED = -20;
	/**
	 * 一分钟的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_MINUTE = 60 * 1000;
	/**
	 * 一小时的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_HOUR = 60 * ONE_MINUTE;
	/**
	 * 一天的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_DAY = 24 * ONE_HOUR;
	/**
	 * 一月的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_MONTH = 30 * ONE_DAY;
	/**
	 * 一年的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_YEAR = 12 * ONE_MONTH;
	/**
	 * 上次更新时间的字符串常量，用于作为SharedPreferences的键值
	 */
	private static final String UPDATED_AT = "updated_at";
	/**
	 * 下拉刷新的回调接口
	 */
	private PullToRefreshListener listener;
	/**
	 * 用于存储上次更新时间
	 */
	private SharedPreferences preferences;
	/**
	 * 下拉头的View
	 */
	private LinearLayout header;
	/**
	 * 需要去下拉刷新的ListView
	 */
	protected ListView listView;
	/**
	 * 刷新时显示的进度条
	 */
	private ProgressBar progressBar;
	/**
	 * 指示下拉和释放的箭头
	 */
	private ImageView arrow;
	/**
	 * 指示下拉和释放的文字描述
	 */
	private TextView description;
	/**
	 * 上次更新时间的文字描述
	 */
	private TextView updateAt;
	/**
	 * 下拉头的布局参数
	 */
	// private MarginLayoutParams headerLayoutParams;
	/**
	 * 上次更新时间的毫秒值
	 */
	private long lastUpdateTime;
	/**
	 * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
	 */
	private int mId = -1;
	/**
	 * 下拉头的高度
	 */
	/**
	 * 当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,
	 * STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED
	 */
	private int currentStatus = STATUS_REFRESH_FINISHED;;
	/**
	 * 记录上一次的状态是什么，避免进行重复操作
	 */
	private int lastStatus = currentStatus;
	/**
	 * 手指按下时的屏幕纵坐标
	 */
	private float yDown;
	/**
	 * 在被判定为滚动之前用户手指可以移动的最大值。
	 */
	private int touchSlop;
	/**
	 * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
	 */
	private boolean loadOnce;
	/**
	 * 当前是否可以下拉，只有ListView滚动到头的时候才允许下拉
	 */
	private boolean ableToPull;

	private Context context;
	private AnimationDrawable animationDrawable;

	/**
	 * 下拉刷新控件的构造函数，会在运行时动态添加一个下拉头的布局。
	 * 
	 * 
	 * @param context
	 * @param attrs
	 */
	public RefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		header = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.pull_refresh, null);
		progressBar = (ProgressBar) header.findViewById(R.id.progress_bar);
		arrow = (ImageView) header.findViewById(R.id.arrow);
		description = (TextView) header.findViewById(R.id.description);
		updateAt = (TextView) header.findViewById(R.id.updated_at);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		refreshUpdatedAtValue();
		addView(header);
		measureView(header);
		header.setVisibility(View.INVISIBLE);
		Drawable drawable = context.getResources().getDrawable(
				R.drawable.refresh_list_animation);
		animationDrawable = (AnimationDrawable) drawable;
	}

	/**
	 * 进行一些关键性的初始化操作，比如：将下拉头向上偏移进行隐藏，给ListView注册touch事件。
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setListView(ListView listView) {
		this.listView = listView;
		addView(this.listView);
		((HeaderListView) listView)
				.setOnPushRefreshListener(new onPushRefreshListener() {
					@Override
					public void pushed(int i) {
						updateHeaderView(i);
					}
				});
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams params = child.getLayoutParams();
		if (params == null) {
			params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0,
				params.width);
		int lpHeight = params.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 给下拉刷新控件注册一个监听器。
	 * 
	 * @param listener
	 *            监听器的实现。
	 * @param id
	 *            为了防止不同界面的下拉刷新在上次更新时间上互相有冲突， 请不同界面在注册下拉刷新监听器时一定要传入不同的id。
	 */
	public void setOnRefreshListener(PullToRefreshListener listener, int id) {
		this.listener = listener;
		mId = id;
	}

	/**
	 * 更新下拉头中的信息。
	 */
	private void updateHeaderView(int f) {
		if (f == -1) {
			header.setVisibility(View.INVISIBLE);
			if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
				if (listener != null) {
					listener.onRefresh();
				}
			}
			return;
		}
		if (currentStatus == STATUS_REFRESHING) {
			header.setVisibility(View.INVISIBLE);
			return;
		}
		header.setVisibility(View.VISIBLE);
		Log.i("", "updateHeaderVie:" + f);

		int frames = animationDrawable.getNumberOfFrames();
		int index = (int) (f * (frames / 150.f));
		if (index >= frames) {
			index = frames - 1;
			currentStatus = STATUS_RELEASE_TO_REFRESH;
		} else {
			currentStatus = STATUS_PULL_TO_REFRESH;
		}
		if (index >= 0) {
			arrow.setImageDrawable(animationDrawable.getFrame(index));
			refreshUpdatedAtValue();
		}
	}

	/**
	 * 刷新下拉头中上次更新时间的文字描述。
	 */
	private void refreshUpdatedAtValue() {
		lastUpdateTime = preferences.getLong(UPDATED_AT + mId, -1);
		long currentTime = System.currentTimeMillis();
		long timePassed = currentTime - lastUpdateTime;
		long timeIntoFormat;
		String updateAtValue;
		if (lastUpdateTime == -1) {
			updateAtValue = getResources().getString(R.string.not_updated_yet);
		} else if (timePassed < 0) {
			updateAtValue = getResources().getString(R.string.time_error);
		} else if (timePassed < ONE_MINUTE) {
			updateAtValue = getResources().getString(R.string.updated_just_now);
		} else if (timePassed < ONE_HOUR) {
			timeIntoFormat = timePassed / ONE_MINUTE;
			String value = timeIntoFormat + "分钟";
			updateAtValue = String.format(
					getResources().getString(R.string.updated_at), value);
		} else if (timePassed < ONE_DAY) {
			timeIntoFormat = timePassed / ONE_HOUR;
			String value = timeIntoFormat + "小时";
			updateAtValue = String.format(
					getResources().getString(R.string.updated_at), value);
		} else if (timePassed < ONE_MONTH) {
			timeIntoFormat = timePassed / ONE_DAY;
			String value = timeIntoFormat + "天";
			updateAtValue = String.format(
					getResources().getString(R.string.updated_at), value);
		} else if (timePassed < ONE_YEAR) {
			timeIntoFormat = timePassed / ONE_MONTH;
			String value = timeIntoFormat + "个月";
			updateAtValue = String.format(
					getResources().getString(R.string.updated_at), value);
		} else {
			timeIntoFormat = timePassed / ONE_YEAR;
			String value = timeIntoFormat + "年";
			updateAtValue = String.format(
					getResources().getString(R.string.updated_at), value);
		}
		updateAt.setText(updateAtValue);
	}

	public interface PullToRefreshListener {
		void onRefresh();

		public void refreshing();

		public void finishRefreshing();
	}

	public void refreshing() {
		currentStatus = STATUS_REFRESHING;
	}

	public void finishRefreshing() {
		currentStatus = STATUS_REFRESH_FINISHED;
		preferences.edit()
				.putLong(UPDATED_AT + mId, System.currentTimeMillis()).commit();

	}

}