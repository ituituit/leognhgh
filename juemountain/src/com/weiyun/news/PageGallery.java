package com.weiyun.news;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.Gallery;

public class PageGallery extends ViewPager {// SlideOnePageGallery
	private PageChangedListener listener = null;

	public PageGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	float lastDownX = 0;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		touchIt(true);
		int childCount = this.getAdapter().getCount();
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if (lastDownX - .5 < event.getX() && this.getCurrentItem() == 0) {
				// minus .5 to avoid,new touch place may deviation to other side
				touchIt(false);
			} else {
			}
			if (lastDownX + .5 > event.getX()
					&& this.getCurrentItem() == childCount - 1) {
				touchIt(false);
			} else {
			}
			break;
		case MotionEvent.ACTION_DOWN:
			lastDownX = event.getX();
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	public void touchIt(boolean touching) {
		ViewParent pager = this.getParent().getParent();
		ViewParent pager1 = this.getParent();
		if (touching) {
			pager.requestDisallowInterceptTouchEvent(true);
			pager1.requestDisallowInterceptTouchEvent(true);
		} else {
			pager.requestDisallowInterceptTouchEvent(false);
			pager1.requestDisallowInterceptTouchEvent(false);
		}

	}

	public void addPageChangedListener(PageChangedListener listener) {
		this.listener = listener;
	}

}

interface PageChangedListener {
	void changed(int page);
}