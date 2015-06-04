package com.weiyun.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.juetc.news.R;
import com.weiyun.domain.gallery.entity.NewsGalleryUnit;

public class SlideGalleryAdapter extends HomeGalleryAdapter{

	public SlideGalleryAdapter(Context context, NewsGalleryUnit arrayList,
			View container) {
		super(context, arrayList, container);
	}
	
	@Override
	public void flushContainerTexts(int position) {
		View slide = this.getContainer();
		TextView currentView = (TextView) slide.findViewById(R.id.current_page);
		TextView maxView = (TextView) slide.findViewById(R.id.max_page);
		TextView descriptionView = (TextView) slide.findViewById(R.id.description);
		currentView.setText("" + (1+position));
		maxView.setText("/" + getCount());
		String description = getDatasource().getBody().getSlides().get(position).getDescription();
		descriptionView.setText(description);
	}
}