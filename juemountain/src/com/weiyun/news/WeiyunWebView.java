package com.weiyun.news;

import java.io.Serializable;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.webkit.WebView;

public class WeiyunWebView extends WebView implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2347869678491197994L;
	private ScaleGestureDetector detector;
	private boolean isDestroy = false;

	public WeiyunWebView(Context context) {
		super(context);
	}

	public void destroy() {
		this.isDestroy = true;
		super.destroy();
	}

	public void loadUrl(String paramString) {
		if (this.isDestroy) {
			return;
		}
		super.loadUrl(paramString);
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		if (this.detector != null) {
			this.detector.onTouchEvent(paramMotionEvent);
		}
		return super.onTouchEvent(paramMotionEvent);
	}

	public void setScaleGestureDetector(
			ScaleGestureDetector paramScaleGestureDetector) {
		this.detector = paramScaleGestureDetector;
	}

	// @Override
	// public void onPageFinished(WebView view, String url) {
	// super.onPageFinished(view, url);
	// }
}
