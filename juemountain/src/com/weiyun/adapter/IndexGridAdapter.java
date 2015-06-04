package com.weiyun.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheesemobile.util._Log;
import com.juetc.news.R;
import com.weiyun.news.DragGridBaseAdapter;

public class IndexGridAdapter extends BaseAdapter implements
		DragGridBaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<String> list1;

	public IndexGridAdapter(Context paramContext, List<List<String>> paramList) {
		context = paramContext;
		inflater = LayoutInflater.from(context);
		list1 = paramList.get(0);
	}

	public int getCount() {
		int i;
		if (list1 == null)
			i = 0;
		else
			i = list1.size();
		return i;
	}

	public void insert(String str, int position) {
		if (position == -1) {
			position = list1.size();
		}
		list1.add(position, str);
		mHidePosition = position;
		notifyDataSetChanged();
	}

	public String getItem(int paramInt) {
		return list1.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		_Log.i("IndexGridAdapter>>>" + paramInt);
		if (paramView == null)
			paramView = inflater.inflate(R.layout.gridview_item, null);
		TextView tv1 = (TextView) paramView.findViewById(R.id.grid_textView1);
		tv1.setText(list1.get(paramInt));
		if (paramInt == mHidePosition) {
			paramView.setVisibility(View.INVISIBLE);
		} else {
			paramView.setVisibility(View.VISIBLE);
		}
		return paramView;
	}

	@Override
	public void reorderItems(int oldPosition, int newPosition) {
		String string = list1.get(oldPosition);
		if (oldPosition < newPosition) {
			for (int i = oldPosition; i < newPosition; i++) {
				Collections.swap(list1, i, i + 1);
			}
		} else if (oldPosition > newPosition) {
			for (int i = oldPosition; i > newPosition; i--) {
				Collections.swap(list1, i, i - 1);
			}
		}
		list1.set(newPosition, string);
	}

	private int mHidePosition = -1;

	@Override
	public void setHideItem(int hidePosition) {
		this.mHidePosition = hidePosition;
		notifyDataSetChanged();
	}

	@Override
	public int indexObj(String str) {
		return list1.indexOf(str);
	}

	public void reload(ArrayList<String> list) {
		list1 = list;
		notifyDataSetChanged();
	}

	@Override
	public void remove(int i) {
		if (i != -1) {
			list1.remove(i);
			notifyDataSetChanged();
		}
	}

	@Override
	public void modify(String str) {
		int indexOf = list1.indexOf("test");
		list1.set(indexOf, str);
		notifyDataSetChanged();
	}
}