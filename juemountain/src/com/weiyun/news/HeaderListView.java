package com.weiyun.news;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.Scroller;

public class HeaderListView extends ListView {

	private Scroller scroller;

	private int len = 200;
	private boolean scrollerType = false;

	private onPushRefreshListener listener;

	public void setOnPushRefreshListener(onPushRefreshListener listener) {
		this.listener = listener;
	}

	float startY;

	int bottom;

	private int mTouchSlop;

	public HeaderListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		scroller = new Scroller(context);
		init();
	}

	private void init() {
		final ViewConfiguration configuration = ViewConfiguration
				.get(getContext());
		mTouchSlop = configuration.getScaledTouchSlop();
	}

	public HeaderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(context);
		init();

	}

	public HeaderListView(Context context) {
		super(context);
		scroller = new Scroller(context);
		init();
	}

	@Override
	public void addHeaderView(View v) {
		v.setBackgroundResource(R.color.white);
		super.addHeaderView(v);
	}

	public int getCurrentTop() {
		return 0;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		float currentY = ev.getY();
		View firstChild = getChildAt(0);
		if (firstChild == null) {
			return true;
		}
		Log.e("currentY",
				currentY + " " + this.getScrollY() + " " + firstChild.getTop());
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = currentY;
			bottom = getCurrentTop();
			if (getFirstVisiblePosition() > 0 || firstChild.getTop() < 0) {
				startY = bottom;
			}
		case MotionEvent.ACTION_MOVE:
			if (startY > bottom && startY < currentY
					&& getFirstVisiblePosition() == 0
					&& firstChild.getTop() == 0) {
				int y = (int) (bottom + (currentY - startY) / 1.7f);
				Log.e("ACTION_MOVE", "y =" + y);
				if (y < getCurrentTop() + len && y >= bottom) {
					this.scrollTo(0, -y);
					if (listener != null) {
						listener.pushed(y);
					}
				}
				scrollerType = false;
				return false;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			scrollerType = true;
			Log.e("ACTION_UP", "" + (bottom - getCurrentTop()));
			scroller.startScroll(getLeft(), getCurrentTop(), 0 - getLeft(),
					bottom - getCurrentTop(), 200);
			invalidate();
			if (listener != null) {
				listener.pushed(-1);
			}
			if (startY > bottom && startY < currentY
					&& getFirstVisiblePosition() == 0) {
				return true;
			}
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			int x = scroller.getCurrX();
			int y = scroller.getCurrY();
			scrollTo(x, y);
			// if(listener != null){
			// listener.pushed(-1);
			// }
			// viewPager.layout(0, 0, x + viewPager.getWidth(), y);
			// if (!scroller.isFinished() && scrollerType && y > bottom) {
			// // viewPager.setLayoutParams(new AbsListView.LayoutParams(
			// // viewPager.getWidth(), y));
			// this.scrollTo(0, y);
			// }
			// invalidate();

		}
	}

}

interface onPushRefreshListener {
	public void pushed(int i);
}
