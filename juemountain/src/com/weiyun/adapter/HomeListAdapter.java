package com.weiyun.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import com.cheesemobile.util.ImageUtil.ImageCallback;
import com.juetc.news.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.weiyun.domain.channel.entity.ChannelItemBean;

public class HomeListAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater = null;
	private Context context;
	private ListView listView;
	private List<ChannelItemBean> dataSource;
	DisplayImageOptions options;

	public HomeListAdapter(Context context, List<ChannelItemBean> dataSource,
			ListView listView) {
		this.context = context;
		this.dataSource = dataSource;
		this.listView = listView;
		mLayoutInflater = LayoutInflater.from(context);
		TypedArray a = context
				.obtainStyledAttributes(new int[] { R.attr.white });
		resourceId = a.getColor(0, 0);
		a.recycle();

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_loading)
				.showImageForEmptyUri(R.drawable.icon_loading)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnFail(R.drawable.icon_loading).cacheInMemory(true)
				.cacheOnDisk(true).build();
	}

	@Override
	public int getCount() {
		int val = dataSource.size();
		return val;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	ViewHolder holder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChannelItemBean channelItemBean = dataSource.get(position);
		int layoutId = R.layout.activity_main_news_title_1_pic;
		int channelType = channelItemBean.intType(channelItemBean.getType());
		switch (channelType) {
		case ChannelItemBean.TYPE_NORMAL:
			layoutId = R.layout.activity_main_news_title_1_pic;
			break;
		case ChannelItemBean.TYPE_SLIDE:
			layoutId = R.layout.activity_main_news_title_3_pic;
			break;

		default:
			break;
		}
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
			ImageView image = (ImageView) convertView.findViewById(R.id.image);
		}
		if (convertView == null || holder.type != channelType) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(layoutId, null);
			holder.type = channelType;
			holder.clickNum = (TextView) convertView
					.findViewById(R.id.click_number);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			loadExtraViews(holder, convertView);
			convertView.setTag(holder);
			convertView.setBackgroundColor(resourceId);
		}
		holder.clickNum.setText("" + channelItemBean.getCommentsall());
		holder.date.setText("" + channelItemBean.getUpdateTime());
		holder.title.setText("" + channelItemBean.getTitle());
		if (position >= 19) {
			convertView.setId(position);
		}
		convertView.setId(position);
		convertView.setOnClickListener((OnClickListener) context);

		List<String> imageUrls = channelItemBean.getImages();
		if (imageUrls.size() != 0) {
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("str", imageUrls.get(0));
			msg.setData(data);
			loadImage(imageUrls.get(0), holder.image);
			loadExtraImages(holder, imageUrls);
		}

		return convertView;
	}

	// private Handler handler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// ImageLoader.getInstance().displayImage(
	// msg.getData().getString("str"), holder.image, options,
	// animateFirstListener);
	// };
	// };

	private void loadExtraViews(ViewHolder holder, View convertView) {
		int type = holder.type;
		if (type == ChannelItemBean.TYPE_SLIDE) {
			holder.image2 = (ImageView) convertView.findViewById(R.id.image2);
			holder.image3 = (ImageView) convertView.findViewById(R.id.image3);
		}
	}

	private void loadExtraImages(ViewHolder holder, List<String> imageUrls) {
		int type = holder.type;
		if (type == ChannelItemBean.TYPE_SLIDE) {
			if (imageUrls.size() >= 2) {
				loadImage(imageUrls.get(1), holder.image2);
			}
			if (imageUrls.size() >= 3) {
				loadImage(imageUrls.get(2), holder.image3);
			}
		}
	}

	private void loadImage(String imageUrl, ImageView imageView) {
		// AsynImageLoader loader = new AsynImageLoader();
		// loader.showImageAsyn(imageView,imageUrl,R.drawable.icon_loading);
		imageView.setScaleType(ScaleType.CENTER_INSIDE);
		ImageLoader.getInstance().displayImage(imageUrl, imageView, options,
				animateFirstListener);

		// String imagePath = ImageUtil.getCacheImgPath().concat(
		// ImageUtil.md5(imageUrl));
		// imageView.setTag(imagePath);
		// Bitmap bitmap = ImageUtil.loadImage(imagePath, imageUrl,
		// callbackRow);
		// if (bitmap == null) {
		// imageView.setImageResource(R.drawable.icon_loading);
		// imageView.setScaleType(ScaleType.CENTER);
		// } else {
		// setImg(imageView, bitmap);
		// }
	}

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	// @Override
	// public void onDestroy() {
	// super.onDestroy();
	// AnimateFirstDisplayListener.displayedImages.clear();
	// }
	//
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {
		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// boolean firstDisplay = !displayedImages.contains(imageUri);
				// if (firstDisplay) {
				imageView.setScaleType(ScaleType.CENTER_CROP);
				FadeInBitmapDisplayer.animate(imageView, 300);
				// displayedImages.remove(imageUri);
				// }
			}
		}
	}

	private void setImg(ImageView iv, Bitmap bitmap) {
		iv.setImageBitmap(bitmap);
		iv.setScaleType(ScaleType.CENTER_CROP);
	}

	ImageCallback callbackRow = new ImageCallback() {
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			if (listView == null) {
				listView.toString();
			}
			ImageView imgView = (ImageView) listView.findViewWithTag(imagePath);
			if (imgView != null) {
				setImg(imgView, bitmap);
			}
		}
	};
	private int resourceId;

	public void reloadView() {
		this.notifyDataSetChanged();
	}

	public class ViewHolder {
		public int type;
		public ImageView image;
		public ImageView image2;
		public ImageView image3;

		public TextView title;
		public TextView date;
		public TextView clickNum;
	}

	public List<ChannelItemBean> getDataSource() {
		return dataSource;
	}

	public void setDataSource(ArrayList<ChannelItemBean> dataSource) {
		this.dataSource = dataSource;
	}

	public void appendDataSource(List<ChannelItemBean> dataSource) {
		this.dataSource.addAll(dataSource);
	}
}
