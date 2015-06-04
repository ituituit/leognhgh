package com.weiyun.news;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.juetc.news.R;
import com.weiyun.adapter.IndexGridAdapter;
import com.weiyun.domain.HomeBean;
import com.weiyun.news.DragGridView.AddableViewListener;

public class ChannelSettingActivity extends ActionBarActivity {

	private DragGridView grd_electron;
	private DragGridView grd_electron2;
	private View splitTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_setting);
		splitTag = findViewById(R.id.split_tag);
		grd_electron = (DragGridView) findViewById(R.id.grd_electron);
		grd_electron2 = (DragGridView) findViewById(R.id.more_channel);
		List<String> defaultOrderMenuItems = HomeBean.subscriptions
				.getDefaultOrderMenuItems();
		List<List<String>> list1 = new ArrayList<List<String>>();
		ArrayList<String> arrayList = new ArrayList<String>();
		list1.add(arrayList);
		List<List<String>> list2 = new ArrayList<List<String>>();
		list2.add(defaultOrderMenuItems);
		grd_electron.setAdapter(new IndexGridAdapter(this, list1));
		grd_electron2.setAdapter(new IndexGridAdapter(this, list2));
		grd_electron.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			}
		});
		grd_electron2.addListener(new EditAddableViewListener(grd_electron,
				grd_electron2));
		grd_electron.addListener(new EditAddableViewListener(grd_electron2,
				grd_electron));
	}

	public static class EditAddableViewListener implements AddableViewListener {
		DragGridView from;
		DragGridView to;

		public EditAddableViewListener(DragGridView from, DragGridView to) {
			super();
			this.from = from;
			this.to = to;
		}

		@Override
		public void moveOut(float x, float y) {
			if (DragGridView.isTouchInItem(from, (int) x, (int) y)) {
				from.addPreview(x, y);
			}else{
				cancel();
			}
		}

		@Override
		public void cancel() {
			from.removePreview();
			from.onStopDrag();
		}

		@Override
		public void moveUp(float rawX, float rawY) {
			if (DragGridView.isTouchInItem(from, (int) rawX, (int) rawY)) {
				String str = (String) ((IndexGridAdapter) to.getAdapter())
						.getItem(to.getCurrentPosition());
				((IndexGridAdapter) from.getAdapter()).modify(str);
				from.onStopDrag();
				((IndexGridAdapter) to.getAdapter()).remove(to
						.getCurrentPosition());
			}
		}
	}

	private int getTagY() {
		int[] location = new int[2];
		splitTag.getLocationOnScreen(location);
		return location[1];
	}

	boolean moveToUpper = true;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
