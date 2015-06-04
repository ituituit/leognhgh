package com.weiyun.news;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheesemobile.util.Constants;
import com.cheesemobile.util.RequestVoNews;
import com.example.weiyunnews.util.SystemUiHider;
import com.google.zxing.WriterException;
import com.juetc.TencentService;
import com.juetc.news.R;
import com.weiyun.domain.QRCodeBean;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.newhardware.parser.QRCodeParser;
import com.weiyun.util.SettingUtil;
import com.weiyun.util.SharaedPreferences;
import com.weiyun.util.ZXingUtil;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GiftActivity extends BaseActivity implements OnClickListener {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	private static final String TextView = null;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	private boolean stop = false;

	private void restoreBright() {
		SettingUtil.setScreenBrightness(screenBrightness, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		restoreBright();
		stop = true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gift);
		contentView = findViewById(R.id.fullscreen_content);
		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		login = (TextView) findViewById(R.id.login);
		Button backBtn = (Button) findViewById(R.id.back_button);
		backBtn.setOnClickListener(this);
		login.setOnClickListener(this);
		mSystemUiHider = SystemUiHider.getInstance(this, controlsView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView.animate().alpha(visible ? 1.f : 0)
									.setDuration(mShortAnimTime);
							// .translationY(visible ? 0 : mControlsHeight)

						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		// findViewById(R.id.dummy_button).setOnTouchListener(
		// mDelayHideTouchListener);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// flushView();
		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
	}

	private void flushView() {
		if (TencentService.getInstance(this).isLogin()) {
			drawACode();
			contentView.setVisibility(View.VISIBLE);
			login.setVisibility(View.GONE);
		} else {
			contentView.setVisibility(View.INVISIBLE);
			login.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onResume() {
		flushView();
		screenBrightness = SettingUtil.getScreenBrightness(GiftActivity.this);
		super.onResume();
	}

	public void drawACode() {
		QRCodeBean qrCode = SharaedPreferences
				.getQRCode(getApplicationContext());
		String codeTex = "";
		if (qrCode != null) {
			codeTex = qrCode.getCodenum();// StringUtil.genNumbers(6);
		} else {
			String string = SharaedPreferences.getLoginInfo(
					getApplicationContext()).get("openId");
			String encodeUserId = string.substring(0,
					Constants.maxGiftUserLength);
			String url = com.cheesemobile.util.Constants.giftHostNew;
			url += "&" + "userkey=" + "newdevice";
			url += "&" + "cuscode=" + encodeUserId;
			RequestVoNews reqVo = new RequestVoNews(url, this,
					new QRCodeParser(new DataCallback<QRCodeBean>() {
						@Override
						public void processData(QRCodeBean paramObject,
								boolean paramBoolean) {
							if (paramObject.getRet() == 203) {
								Toast.makeText(
										getApplicationContext(),
										getApplicationContext().getResources()
												.getString(R.string.reutrn_203),
										Toast.LENGTH_SHORT).show();
								contentView.setVisibility(View.INVISIBLE);
								login.setVisibility(View.VISIBLE);
							} else {
								SharaedPreferences.setQRCode(
										getApplicationContext(), paramObject);
								QRCodeBean qrCode = SharaedPreferences
										.getQRCode(getApplicationContext());
								String codeTex = "";
								if (qrCode != null) {
									codeTex = qrCode.getCodenum();// StringUtil.genNumbers(6);
									drawCode(codeTex);
									delayedHide(100);
								}
							}
						}
					}));
			getDataFromServer(reqVo);
			return;
		}
		drawCode(codeTex);
	}

	private void drawCode(String codeTex) {
		maxBright(6000);
		ImageView code = (ImageView) findViewById(R.id.code_image);
		try {
			TypedArray a = this
					.obtainStyledAttributes(new int[] { R.attr.gray_dark_text });
			int resourceId = a.getColor(0, 0);
			a.recycle();

			code.setImageBitmap(ZXingUtil.Create2DCode(codeTex, resourceId));
		} catch (WriterException e) {
			e.printStackTrace();
		}
		TextView value = (TextView) findViewById(R.id.code_value);
		value.setText(codeTex);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	// View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener()
	// {
	// @Override
	// public boolean onTouch(View view, MotionEvent motionEvent) {
	// if (AUTO_HIDE) {
	// delayedHide(AUTO_HIDE_DELAY_MILLIS);
	// }
	// return false;
	// }
	// };

	Handler mHideHandler = new Handler() {
	};
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	Runnable mBrightRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				SettingUtil.setScreenBrightness(255, GiftActivity.this);
//				int delayedSpace = 16;
//				float plus = .1f;
//
//				float current = screenBrightness;
//				while (current < hardDelay) {
//					if (stop) {
//						break;
//					}
//					Log.i("", current + "");
//					Message msg = new Message();
//					msg.obj = current;
//					mHideHandler.sendMessage(msg);
//					Thread.sleep(delayedSpace);
//					current += plus;
//				}
			} catch (Exception e) {
			} finally {
			}
		}
	};

	int hardDelay = 255;

	private View contentView;

	private TextView login;

	private int screenBrightness;

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}

	private void maxBright(int delayMillis) {
		mHideHandler.removeCallbacks(mBrightRunnable);
		mHideHandler.postDelayed(mBrightRunnable, delayMillis);
		mSystemUiHider.toggle();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login:
			ToolBarView.startLogin(GiftActivity.this);
			break;
		case R.id.back_button:
			onBackPressed();
			break;
		default:
			break;
		}
	}
}
