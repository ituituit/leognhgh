package com.weiyun.scroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

import com.cheesemobile.util._Log;
import com.juetc.news.R;
import com.weiyun.scroller.Workspace.SavedState;

public class TitleGroup extends ViewGroup {
	private static final int INVALID_SCREEN = -1;
	private Scroller mScroller;
	private int mCurrentScreen;
	private int mDefaultScreen;
	private int mNextScreen = INVALID_SCREEN;
	private int mTouchSlop;
	private int mMaximumVelocity;

	private float mLastMotionX;
	private float mLastMotionY;
	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;
	private int mTouchState = TOUCH_STATE_REST;
	public boolean allowTouch = true;

	private void initWorkspace() {
		mScroller = new Scroller(getContext());
		mCurrentScreen = mDefaultScreen;
		// / Launcher.setScreen(mCurrentScreen);
		Log.i("144", mScroller.isFinished() + "");
		final ViewConfiguration configuration = ViewConfiguration
				.get(getContext());
		mTouchSlop = configuration.getScaledTouchSlop();
		_Log.i("mTouchSlop:" + mTouchSlop);
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
	}

	public TitleGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mDefaultScreen = 0;
		initWorkspace();
	}

//	@Override
//	protected void dispatchDraw(Canvas canvas) {
//		final long drawingTime = getDrawingTime();
//		final int count = getChildCount();
//		for (int i = 0; i < count; i++) {
//			drawChild(canvas, getChildAt(i), drawingTime);
//			TextView v = (TextView) getChildAt(i);
//			float x = getScrollX() - (i * getwidth());
//			double deg = x/getwidth();
//			deg = Math.abs(deg);
//			double alpha = 255;
//			if(deg<=1){
//				alpha *=  1 - deg;
//			}else{
//				alpha = 0;
//			}
//			Log.i("","deg "+ i +" is:" + x);
//			v.setTextColor(Color.argb((int)alpha, 0, 0, 0));
////			v.getBackground().setAlpha(0);
//		}
//	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		final long drawingTime = getDrawingTime();
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			drawChild(canvas, getChildAt(i), drawingTime);
			TextView v = (TextView) getChildAt(i);
//			final Resources.Theme theme = v.getContext().getTheme();
			
			TypedArray a = v.getContext().obtainStyledAttributes(new int[] {  
	                R.attr.gray_light_text});  
			int resourceId = a.getColor(0, 0);
//			a.recycle();
			v.setTextColor(resourceId);
//			v.getBackground().setAlpha(0);
		}
		TextView v = (TextView) getChildAt(mCurrentScreen);
		v.setTextColor(Color.argb(255, 255, 0, 0));
	}

	public int getwidth() {
		return getWidth()/2;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

			postInvalidate();
		} else if (mNextScreen != INVALID_SCREEN) {
			mCurrentScreen = Math.max(0,
					Math.min(mNextScreen, getChildCount() - 1));

			mNextScreen = INVALID_SCREEN;
		}
	}

	@Override
	public boolean isOpaque() {

		return false;
	}

	@Override
	public boolean requestChildRectangleOnScreen(View child, Rect rectangle,
			boolean immediate) {
		int screen = indexOfChild(child);
		if (screen != mCurrentScreen || !mScroller.isFinished()) {

			snapToScreen(screen);

			return true;
		}
		return false;
	}

	public void snapToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		boolean changingScreens = whichScreen != mCurrentScreen;

		mNextScreen = whichScreen;

		View focusedChild = getFocusedChild();
		if (focusedChild != null && changingScreens
				&& focusedChild == getChildAt(mCurrentScreen)) {
			focusedChild.clearFocus();
		}
		View view = getChildAt(mNextScreen);
		int left = getChildAt(mNextScreen).getLeft();
		int minRight = left + view.getWidth();
		int maxLeft = left; 
		final int newX = left;//getChildAt(mCurrentScreen).getLeft();
		int currentPlace = this.getScrollX();
		int currentPlaceRight = this.getScrollX() + this.getWidth();
		int newPlace = currentPlace;
		if(currentPlace > maxLeft){
			newPlace = maxLeft;
		}
		if(currentPlaceRight < minRight){
			newPlace = minRight - this.getWidth();
		}
		
		final int delta = newPlace - this.getScrollX();
		mScroller.startScroll(this.getScrollX(), 0, delta, 0,
				Math.abs(delta) * 2);
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		/*
		 * if (widthMode != MeasureSpec.UNSPECIFIED) { throw new
		 * IllegalStateException("Workspace can only be used in EXACTLY mode.");
		 * }
		 */
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		/*
		 * if (heightMode != MeasureSpec.AT_MOST) { throw new
		 * IllegalStateException("Workspace can only be used in EXACTLY mode.");
		 * }
		 */

		// The children are given the same width and height as the workspace
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		int childLeft = 0;

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				final int childWidth = child.getMeasuredWidth();
				child.layout(childLeft, 0, childLeft + childWidth,
						child.getMeasuredHeight());
				
				childLeft += child.getWidth() ;
			}
		}
	}

	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {

		return super.dispatchUnhandledMove(focused, direction);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// System.out.println("onInterceptTouchEvent");
		/*
		 * This method JUST determines whether we want to intercept the motion.
		 * If we return true, onTouchEvent will be called and we do the actual
		 * scrolling there.
		 */

		/*
		 * Shortcut the most recurring case: the user is in the dragging state
		 * and he is moving his finger. We want to intercept this motion.
		 */

		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();
		final float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_MOVE:
			/*
			 * mIsBeingDragged == false, otherwise the shortcut would have
			 * caught it. Check whether the user has moved far enough from his
			 * original down touch.
			 */

			/*
			 * Locally do absolute value. mLastMotionX is set to the y value of
			 * the down event.
			 */
			final int xDiff = (int) Math.abs(x - mLastMotionX);
			final int yDiff = (int) Math.abs(y - mLastMotionY);

			final int touchSlop = mTouchSlop;
			boolean xMoved = xDiff > touchSlop;
			boolean yMoved = yDiff > touchSlop;

			if (xMoved || yMoved) {

				if (xMoved) {
					// Scroll if the user moved far enough along the X axis
					mTouchState = TOUCH_STATE_SCROLLING;
					mLastMotionX = x;
				}

			}
			break;

		case MotionEvent.ACTION_DOWN:
			// Remember location of down touch
			mLastMotionX = x;
			mLastMotionY = y;

			/*
			 * If being flinged and user touches the screen, initiate drag;
			 * otherwise don't. mScroller.isFinished should be false when being
			 * flinged.
			 */
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;

			// System.out.println("449 mTouchState" + mTouchState);
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;

			// Release the drag

			// mTouchState = TOUCH_STATE_REST;
			// mAllowLongPress = false;
			break;
		}

		/*
		 * The only time we want to intercept motion events is if we are in the
		 * drag mode.
		 */
		return mTouchState != TOUCH_STATE_REST;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(!allowTouch){
			return true;
		}
		// System.out.println("onTouchEvent" + mTouchState);
		final int action = ev.getAction();
		final float x = ev.getX();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mTouchState = TOUCH_STATE_SCROLLING;
			/*
			 * If being flinged and user touches, stop the fling. isFinished
			 * will be false if being flinged.
			 */
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
				Log.i("495", mScroller.isFinished() + "");
			}

			// Remember where the motion event started
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			// System.out.println("493 mTouchState" + mTouchState);
			if (mTouchState == TOUCH_STATE_SCROLLING) {
				// Scroll to follow the motion event
				// System.out.println("MotionEvent.ACTION_MOVE");
				final int deltaX = (int) (mLastMotionX - x);
				mLastMotionX = x;

				if (deltaX < 0) {
					if (this.getScrollX() > 0) {
						scrollBy(Math.max(-this.getScrollX(), deltaX), 0);

					}
				} else if (deltaX > 0) {
					final int availableToScroll = getChildAt(
							getChildCount() - 1).getRight()
							- this.getScrollX() - getWidth();
					if (availableToScroll > 0) {
						scrollBy(Math.min(availableToScroll, deltaX), 0);

					}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mTouchState == TOUCH_STATE_SCROLLING) {

			}
			// System.out.println("536 mTouchState" + mTouchState);
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			// System.out.println("540 mTouchState" + mTouchState);
			mTouchState = TOUCH_STATE_REST;
		}

		return true;
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		final SavedState state = new SavedState(super.onSaveInstanceState());
		state.currentScreen = mCurrentScreen;
		return state;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());
		if (savedState.currentScreen != -1) {
			mCurrentScreen = savedState.currentScreen;

		}
	}

}
