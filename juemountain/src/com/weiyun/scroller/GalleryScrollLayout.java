package com.weiyun.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class GalleryScrollLayout extends ViewGroup {
	private static final int SNAP_VELOCITY = 900;
	private static final String TAG = "ScrollLayout";
	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;
	private int mCurScreen;
	private int mDefaultScreen = 0;
	private float mLastMotionX;
	private float mLastMotionY;
	private Scroller mScroller;
	private int mTouchSlop;
	private int mTouchState = 0;
	private VelocityTracker mVelocityTracker;
	private OnScreenChangeListener onScreenChangeListener;
	private boolean mHorizontalScroll = false;

	public void setHorizontalScroll(boolean bool) {
		if (bool == true) {
			mHorizontalScroll = true;
		} else {
			mHorizontalScroll = false;
		}
	}

	public GalleryScrollLayout(Context paramContext,
			AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
	}

	public GalleryScrollLayout(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		setHorizontalScroll(true);
		mScroller = new Scroller(paramContext);
		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

	}

	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	public int getCurScreen() {
		return mCurScreen;
	}

	public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
		int i = 1;
		Log.e("ScrollLayout", "onInterceptTouchEvent-slop:" + mTouchSlop);
		int j = paramMotionEvent.getAction();
		if ((j != 2) || (mTouchState == 0)) {
			float f1 = paramMotionEvent.getX();
			float f2 = paramMotionEvent.getY();
			switch (j) {
			case MotionEvent.ACTION_DOWN:
				mLastMotionX = f1;
				mLastMotionY = f2;
				if (!mScroller.isFinished())
					j = i;
				else
					j = 0;
				mTouchState = j;
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mTouchState = 0;
				break;
			case MotionEvent.ACTION_MOVE:
				if (mHorizontalScroll) {
					if ((int) Math.abs(mLastMotionY - f1) <= mTouchSlop)
						break;
				} else {
					if ((int) Math.abs(mLastMotionX - f2) <= mTouchSlop)
						break;
				}
				mTouchState = i;
			}
			if (mTouchState == 0)
				return false;
		}
		return true;
	}

	protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2,
			int paramInt3, int paramInt4) {
		Log.e("ScrollLayout", "onLayout");
		if (paramBoolean) {
			int j = 0;
			int m = getChildCount();
			for (int i = 0; i < m; i++) {
				View localView = getChildAt(i);
				if (localView.getVisibility() == 8)
					continue;
				int k = localView.getMeasuredWidth();
				localView.layout(j, 0, j + k, localView.getMeasuredHeight());
				j += k;
			}
		}
	}

	protected void onMeasure(int paramInt1, int paramInt2) {
		Log.e("ScrollLayout", "onMeasure");
		super.onMeasure(paramInt1, paramInt2);
		int i = View.MeasureSpec.getSize(paramInt1);
		int j = getChildCount();
		for (int k = 0; k < j; k++)
			getChildAt(k).measure(paramInt1, paramInt2);
		if (mHorizontalScroll) {
			scrollTo(0, i * mCurScreen);
		} else {
			scrollTo(i * mCurScreen, 0);
		}
		return;
	}
	public boolean onTouchEvent(MotionEvent paramMotionEvent){
		if(mHorizontalScroll){
			return onTouchEventY(paramMotionEvent);
		}else{
			return onTouchEventX(paramMotionEvent);
		}
	}
	public boolean onTouchEventY(MotionEvent paramMotionEvent) {
		if (mVelocityTracker == null)
			mVelocityTracker = VelocityTracker.obtain();
		mVelocityTracker.addMovement(paramMotionEvent);
		int i = paramMotionEvent.getAction();
		float f = paramMotionEvent.getY();
		int j;
		switch (i) {
		case MotionEvent.ACTION_DOWN:
			Log.e("ScrollLayout", "event down!");
			if (!mScroller.isFinished())
				mScroller.abortAnimation();
			mLastMotionY = f;
			break;
		case MotionEvent.ACTION_UP:
			Log.e("ScrollLayout", "event : up");
			VelocityTracker localVelocityTracker1 = mVelocityTracker;
			localVelocityTracker1.computeCurrentVelocity(1000);
			j = (int) localVelocityTracker1.getYVelocity();
			Log.e("ScrollLayout", "velocityY:" + j);
			if ((j <= 500) || (mCurScreen <= 0)) {
				if ((j >= -500) || (mCurScreen >= -1 + getChildCount())) {
					snapToDestination();
				} else {
					Log.e("ScrollLayout", "snap right");
					snapToScreen(1 + mCurScreen);
				}
			} else {
				Log.e("ScrollLayout", "snap left");
				snapToScreen(-1 + mCurScreen);
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			mTouchState = 0;
			break;
		case 2:
			j = (int) (mLastMotionY - f);
			mLastMotionY = f;
			VelocityTracker localVelocityTracker2 = mVelocityTracker;
			localVelocityTracker2.computeCurrentVelocity(1000);
			int k = (int) localVelocityTracker2.getYVelocity();
			if ((mCurScreen != 0) || (k <= 500)) {
				if ((mCurScreen != -1 + getChildCount()) || (k >= -500))
					scrollBy(0, j);
				else
					scrollBy(0, 0);
			} else
				scrollBy(0, 0);
			break;
		case 3:
			mTouchState = 0;
		}
		return true;
	}

	public boolean onTouchEventX(MotionEvent paramMotionEvent) {
		if (mVelocityTracker == null)
			mVelocityTracker = VelocityTracker.obtain();
		mVelocityTracker.addMovement(paramMotionEvent);
		int i = paramMotionEvent.getAction();
		float f = paramMotionEvent.getX();
		paramMotionEvent.getY();
		int j;
		switch (i) {
		case 0:
			Log.e("ScrollLayout", "event down!");
			if (!mScroller.isFinished())
				mScroller.abortAnimation();
			mLastMotionX = f;
			break;
		case 1:
			Log.e("ScrollLayout", "event : up");
			VelocityTracker localVelocityTracker1 = mVelocityTracker;
			localVelocityTracker1.computeCurrentVelocity(1000);
			j = (int) localVelocityTracker1.getXVelocity();
			Log.e("ScrollLayout", "velocityX:" + j);
			if ((j <= 500) || (mCurScreen <= 0)) {
				if ((j >= -500) || (mCurScreen >= -1 + getChildCount())) {
					snapToDestination();
				} else {
					Log.e("ScrollLayout", "snap right");
					snapToScreen(1 + mCurScreen);
				}
			} else {
				Log.e("ScrollLayout", "snap left");
				snapToScreen(-1 + mCurScreen);
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			mTouchState = 0;
			break;
		case 2:
			j = (int) (mLastMotionX - f);
			mLastMotionX = f;
			VelocityTracker localVelocityTracker2 = mVelocityTracker;
			localVelocityTracker2.computeCurrentVelocity(1000);
			int k = (int) localVelocityTracker2.getXVelocity();
			if ((mCurScreen != 0) || (k <= 500)) {
				if ((mCurScreen != -1 + getChildCount()) || (k >= -500))
					scrollBy(j, 0);
				else
					scrollBy(0, 0);
			} else
				scrollBy(0, 0);
			break;
		case 3:
			mTouchState = 0;
		}
		return true;
	}

	public void setOnScreenChangeListener(
			OnScreenChangeListener paramOnScreenChangeListener) {
		onScreenChangeListener = paramOnScreenChangeListener;
	}

	public void setToScreen(int paramInt) {
		int i = Math.max(0, Math.min(paramInt, -1 + getChildCount()));
		mCurScreen = i;
		if (mHorizontalScroll) {
			scrollTo(0, i * getHeight());
		} else {
			scrollTo(i * getWidth(), 0);
		}
	}

	public void snapToDestination() {

		if (mHorizontalScroll) {
			int i = getWidth();
			snapToScreen((getScrollY() + i / 2) / i);
		} else {
			int i = getWidth();
			snapToScreen((getScrollX() + i / 2) / i);
		}
	}

	public void snapToScreen(int paramInt) {
		int j = Math.max(0, Math.min(paramInt, -1 + getChildCount()));
		if (mHorizontalScroll) {
			if (getScrollY() != j * getHeight()) {
				int i = j * getHeight() - getScrollY();
				mScroller.startScroll(0, getScrollY(), 0, i, 2 * Math.abs(i));
				mCurScreen = j;
				onScreenChangeListener.onScreenChange(mCurScreen);
				invalidate();
			}
		} else {
			if (getScrollX() != j * getWidth()) {
				int i = j * getWidth() - getScrollX();
				mScroller.startScroll(getScrollX(), 0, i, 0, 2 * Math.abs(i));
				mCurScreen = j;
				onScreenChangeListener.onScreenChange(mCurScreen);
				invalidate();
			}
		}
	}

	public static abstract interface OnScreenChangeListener {
		public abstract void onScreenChange(int paramInt);
	}

}
