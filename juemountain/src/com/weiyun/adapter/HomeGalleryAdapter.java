package com.weiyun.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.cheesemobile.util.ImageUtil;
import com.cheesemobile.util.ImageUtil.ImageCallback;
import com.juetc.news.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.weiyun.domain.gallery.entity.NewsGalleryUnit;

public class HomeGalleryAdapter extends PagerAdapter {
	LayoutInflater layoutInflater;
	private Context context;
	private NewsGalleryUnit datasource;
	private View container;
	private ImageView.ScaleType imgScaleType = ScaleType.FIT_CENTER;
	DisplayImageOptions options;

	public HomeGalleryAdapter(Context context, NewsGalleryUnit arrayList,
			View container) {
		this.context = context;
		this.datasource = arrayList;
		this.container = container;
		layoutInflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_loading)
				.showImageForEmptyUri(R.drawable.icon_loading)
//				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnFail(R.drawable.icon_loading)
				.cacheOnDisk(true).build();
	}

	public Context getContext() {
		return context;
	}

	public NewsGalleryUnit getDatasource() {
		return datasource;
	}

	public ImageView.ScaleType getImgScaleType() {
		return imgScaleType;
	}

	public void setImgScaleType(ImageView.ScaleType imgScaleType) {
		this.imgScaleType = imgScaleType;
	}

	public void setDatasource(NewsGalleryUnit datasource) {
		this.datasource = datasource;
		this.notifyDataSetChanged();
	}

	public View getContainer() {
		return container;
	}

	@Override
	public int getCount() {
		if (datasource == null) {
			return 0;
		}
		return datasource.getBodyList().size();
	}

	private void setImg(ImageView imageView, Bitmap bitmap) {
		imageView.setImageBitmap(bitmap);
		imageView.setScaleType(imgScaleType);
	}

	public void flushContainerTexts(int position) {
		String titleStr = datasource.getBodyList().get(position).getTitle();
		String indStr = "" + (position + 1);
		String pageNumberStr = "/" + datasource.getBodyList().size();
		TextView title = (TextView) container.findViewById(R.id.gallery_title);
		TextView pageInd = (TextView) container.findViewById(R.id.page_ind);
		TextView pageNumber = (TextView) container
				.findViewById(R.id.page_all_number);
		title.setText(titleStr);
		pageInd.setText(indStr);
		pageNumber.setText(pageNumberStr);
	}

	private ImageCallback callback = new ImageCallback() {
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			ImageView imageView = (ImageView) container.findViewById(
					R.id.gallery).findViewWithTag(imagePath);
			if (imageView != null) {
				setImg(imageView, bitmap);
			}
		}
	};

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView;
		imageView = new ImageView(context);

		String imageUrl = datasource.getBodyList().get(position).getImage();
		if (imageUrl.equals("")) {
			imageUrl = datasource.getBodyList().get(position).getThumbnail();
		}
		String imagePath = ImageUtil.getCacheImgPath().concat(
				ImageUtil.md5(imageUrl));
		imageView.setTag(imagePath);
		ImageLoader.getInstance().displayImage(imageUrl, imageView, options,
				animateFirstListener);

//		Bitmap bitmap = ImageUtil.loadImageFullScreen(imagePath, imageUrl,
//				callback);
//		if (bitmap == null) {
//		} else {
//			setImg(imageView, bitmap);
//		}
		container.addView(imageView);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onClick(arg0);
				}
			}
		});
		return imageView;
	}

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

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

	OnClickListener listener;

	public void setOnClickLisener(OnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (View) arg1;
	}
}
