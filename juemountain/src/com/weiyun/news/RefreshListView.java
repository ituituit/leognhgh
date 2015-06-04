package com.weiyun.news;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

class RefreshListView extends AddableListView {
//	protected static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
//	protected static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";

	private RefreshView refreshView;

	public RefreshView getRefreshView() {
		return refreshView;
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		refreshView = new RefreshView(context, attrs);
		HeaderListView headerListView = new HeaderListView(context);
		init(headerListView);
		applyScrollListener();
	}

	@Override
	public void addView(View child) {
		refreshView.setListView((HeaderListView)child);
		super.addView(refreshView);
	}

	protected boolean pauseOnScroll = true;
	protected boolean pauseOnFling = true;
	private void applyScrollListener() {
		getListView().setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
	}
}