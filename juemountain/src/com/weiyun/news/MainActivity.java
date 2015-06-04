package com.weiyun.news;

import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.cheese.db.QueryDao;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.ImageUtil;
import com.juetc.VersionManager;
import com.juetc.news.R;

public class MainActivity extends BaseActivity {
	private int hardDelay = 3000;
	private int delayed = 50;
	private boolean skiped = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView adImage = (ImageView) findViewById(R.id.main_ad_view);
		Animation animation = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.slide_in_from_top);
		adImage.startAnimation(animation);
		try {
			InputStream open = this.getAssets().open("ad_main.jpg");
			String imagePath = ImageUtil.getCacheImgPath()
					.concat("ad_main.jpg");
			FileUtil.copyFile(open, imagePath);
			Bitmap imageFromLocal = ImageUtil.getImageFromLocal(imagePath,
					ImageUtil.PreviewKeys.fullscreen);
			adImage.setImageBitmap(imageFromLocal);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Button skip = (Button) findViewById(R.id.skip);
		final int returnVal = getIntent().getIntExtra("data", -1);
		skip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				skiped = true;
				if (returnVal == LoginActivity.MSG_HOME_RETURN) {
					finish();
				}
			}
		});
		if (returnVal == LoginActivity.MSG_HOME_RETURN) {
		} else {
			VersionManager.startService(this);
			splashTread.start();
			QueryDao.initDataBaseDir(this, getString(R.string.db_name));
		}
	}

	Thread splashTread = new Thread() {
		@Override
		public void run() {
			try {
				int delayedSpace = delayed;
				while (delayed < hardDelay) {
					Thread.sleep(delayedSpace);
					delayed += delayedSpace;
					if (skiped) {
						break;
					}
				}
				startActivity(new Intent(MainActivity.this, HomeActivity.class));

			} catch (Exception e) {
				// do nothing
			} finally {
				finish();
			}
		}
	};

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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
