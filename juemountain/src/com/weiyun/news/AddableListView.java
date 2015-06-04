package com.weiyun.news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ListView;

class AddableListView extends LinearLayout {
	// public RefreshListView(Context context, AttributeSet attrs, int defStyle)
	// {
	// super(context, attrs, defStyle);
	// }
	//
	// public RefreshListView(Context context) {
	// super(context);
	// }
	private int currentPage = 0;
	private int maxPage = 0;
	private LoadMoreRowListener listener;
	private ListView listView;

	public void setPage(int current, int max) {
		this.currentPage = current;
		this.maxPage = max;
	}

	public AddableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// this.setListView(new ListView(getContext()));
		// this.listView.setDividerHeight(0);
		// addViews();
	}

	public void init(ListView listView) {
		this.setListView(listView);
		listView.setDividerHeight(0);
		addView(listView);
	}

	// protected void addViews() {
	// this.addView(listView);
	// }

	private void setListView(ListView listView) {
		this.listView = listView;
	}

	public ListView getListView() {
		if (listView == null) {
			init(new ListView(this.getContext()));
		}
		return this.listView;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (listView.getChildCount() == 0) {
			return false;
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int count = listView.getAdapter().getCount()
					- listView.getHeaderViewsCount();
			int id = listView.getChildAt(listView.getChildCount() - 1).getId();
			if (id >= count - 1) {
				currentPage++;
				if (currentPage <= maxPage) {
					if (this.listener != null) {
						listener.load(currentPage, maxPage);
					}
				}
			}
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
	}

	public void addLoadMoreRowListener(LoadMoreRowListener listener) {
		this.listener = listener;
	}

	public interface LoadMoreRowListener {
		public void load(int newPage, int count);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return super.onTouchEvent(ev);
	}

	private float mLastMotionX;
	private float mLastMotionY;

	public int moveHorEvent(MotionEvent ev) {
		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			final int xDiff = (int) Math.abs(x - mLastMotionX);
			final int yDiff = (int) Math.abs(y - mLastMotionY);
			final ViewConfiguration configuration = ViewConfiguration
					.get(getContext());
			float touchSlop = configuration.getScaledTouchSlop();
			boolean xMoved = xDiff > touchSlop;
			boolean yMoved = yDiff > touchSlop;
			if (xMoved) {
				return 1;
			}
			if (yMoved) {
				return 0;
			}
			break;

		default:
			break;
		}
		return -1;
	}
}