package com.weiyun.news;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.cheesemobile.util.Constants;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.ImageUtil;
import com.juetc.news.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.weiyun.domain.HomeBean;
import com.weiyun.scroller.MyTitleView;
import com.weiyun.scroller.Workspace;
import com.weiyun.scroller.Workspace.SnapToScreenListener;
import com.weiyun.util.SharaedPreferences;

public class HomeActivity extends ActivityGroup implements OnClickListener {
	private MyTitleView titleView;
	private static LocalActivityManager activitymanager;
//	private HomeBean homeBean;
	private ImageView settingBtn;
	private Workspace workspace;
	private List<String> strs = new ArrayList<String>();

	public static int REQUEST_CODE_LOGIN = 10917;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(SharaedPreferences.getTheme(getApplicationContext()));
		initGolobalValue();
		setContentView(R.layout.activity_home_new);
		titleView = (MyTitleView) findViewById(R.id.mtv);
		View findViewById = findViewById(R.id.gift_button);
		settingBtn = (ImageView) findViewById(R.id.setting);
		findViewById.setOnClickListener(this);
		settingBtn.setOnClickListener(this);
		processData(false);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ArrayList<String> channel = SharaedPreferences.getChannel(this, true);
		if (channel != null) {
			boolean channelChanged = !channel.equals(strs);
			if (channelChanged) {
				BaseActivity.resetActivity(this, this.getClass());
			}
		}
		BaseActivity.themeResetActivity(this, this.getClass());
	}

	public void initGolobalValue() {
		try {
			InputStream open = this.getAssets().open("config.txt");
			String readToString = FileUtil.readToString(open);
			HomeBean homeBean = JSON.parseObject(readToString, HomeBean.class);
			homeBean.setGetComment(Constants.getComment);
			homeBean.setSendComment(Constants.sendComment);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initImageLoader(getApplicationContext());
	}

	public static void initImageLoader(Context context) {
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		File cacheDir = new File(ImageUtil.SDCARD_CACHE_IMG_PATH);
		// ImageLoaderConfiguration config =
		// ImageLoaderConfiguration.createDefault(getA);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.memoryCache(new FIFOLimitedMemoryCache(1 * 1024 * 1024))
				.diskCacheSize(200 * 1024 * 1024)
				.diskCache(new UnlimitedDiscCache(cacheDir))
//				.diskCacheExtraOptions(85, 85, new BitmapProcessor() {
//					@Override
//					public Bitmap process(Bitmap arg0) {
//						return null;
//					}
//				})
//				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
	}

	public static ArrayList<String> getStrs(Context context){
		ArrayList<String> strs = new ArrayList<String>();
			ArrayList<String> channel = SharaedPreferences.getChannel(context, true);
			if (channel == null) {
				strs = HomeBean.subscriptions.getDefaultOrderMenuItems();
			} else {
				strs = channel;
			}
			return strs;
	}
	
	public void processData(boolean paramBoolean) {
		strs = getStrs(this);
		titleView.setStrs(strs);
		titleView.createViews();
		workspace = titleView.getMain();
		activitymanager = getLocalActivityManager();
		Window w;
		View v;
		Intent intent;
		for (int i = 0; i < strs.size(); i++) {
			intent = new Intent(HomeActivity.this, HomeListActivity.class);
			intent.putExtra("pageIndex", i);
			intent.putExtra("pageChannel", strs.get(i));
			Log.i("", "start new channel:" + strs.get(i));
			// HomeActivity.initImageLoader(this);
			// intent = new Intent(this, SimpleImageActivity.class);
			// intent.putExtra(Constants.Extra.FRAGMENT_INDEX,
			// ImageListFragment.INDEX);
			// startActivity(intent);
			// Intent intent = new Intent(this, SimpleImageActivity.class);
			// intent.putExtra(Constants.Extra.FRAGMENT_INDEX,
			// ImageListFragment.INDEX);
			// startActivity(intent);
			w = activitymanager.startActivity(i + "", intent);
			v = w.getDecorView();
			workspace.addView(v);
		}
		workspace.addSnapToScreenListener(new SnapToScreenListener() {
			@Override
			public void snapToScreen(int whichScreen) {
				List<Integer> array = new ArrayList<Integer>();
				array.add(whichScreen);
				if (whichScreen - 1 >= 0) {
					array.add(whichScreen - 1);
				}
				if (whichScreen + 1 <= workspace.getChildCount() - 1) {
					array.add(whichScreen + 1);
				}
				for (int i = 0; i < array.size(); i++) {
					HomeListActivity activity = (HomeListActivity) activitymanager
							.getActivity(array.get(i) + "");
					activity.flushContent();
				}
			}
		});
		workspace.snapToScreen(0);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.gift_button) {
			Intent giftIntent = new Intent(HomeActivity.this,
					GiftActivity.class);
			startActivity(giftIntent);
		}
		if (view.getId() == R.id.setting) {
			Intent settingIntent = new Intent(HomeActivity.this,
					LoginActivity.class);
			startActivityForResult(settingIntent,
					HomeActivity.REQUEST_CODE_LOGIN);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_LOGIN) {

		}
	}

	// public static void initImageLoader(Context context) {
	// // This configuration tuning is custom. You can tune every option, you
	// // may tune some of them,
	// // or you can create default configuration by
	// // ImageLoaderConfiguration.createDefault(this);
	// // method.
	// ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
	// context).threadPriority(Thread.NORM_PRIORITY - 2)
	// .denyCacheImageMultipleSizesInMemory()
	// .diskCacheFileNameGenerator(new Md5FileNameGenerator())
	// .diskCacheSize(50 * 1024 * 1024)
	// // 50 Mb
	// .tasksProcessingOrder(QueueProcessingType.LIFO)
	// .writeDebugLogs() // Remove for release app
	// .build();
	// // Initialize ImageLoader with configuration.
	// ImageLoader.getInstance().init(config);
	// }
}
