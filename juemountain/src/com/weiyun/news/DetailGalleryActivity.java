package com.weiyun.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.cheesemobile.util.RequestVoNews;
import com.juetc.CommentsManager;
import com.juetc.news.R;
import com.weiyun.adapter.SlideGalleryAdapter;
import com.weiyun.domain.channel.entity.ChannelItemBean;
import com.weiyun.domain.comment.entity.CommentsData;
import com.weiyun.domain.gallery.entity.NewsGalleryUnit;
import com.weiyun.listener.ToolBarListener;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.newhardware.parser.CommentsParser;
import com.weiyun.newhardware.parser.GalleryParser;
import com.weiyun.scroller.DetailView;

public class DetailGalleryActivity extends DetailBaseActivity {
	public static int MSG_GALLERY = 2;

	ToolBarView toolbar;
	private RelativeLayout container;
	private SlideGalleryAdapter adapter;
	private NewsGalleryUnit galleryData;
	
	
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_GALLERY) {
				adapter.flushContainerTexts((int) msg.obj);
			}
			if (msg.what == MSG_REFRESH) {
				refreshList((int) msg.obj);
			}
			if (msg.what == MSG_COMMENT) {
				detailView.getWorkspace().scrollRight();
			}
		};
	};

	public void sendRefreshListMSG(int page) {
		Message message = new Message();
		message.what = MSG_REFRESH;
		message.obj = page;
		handler.sendMessage(message);
	}

	public void sendGallery(int page) {
		Message message = new Message();
		message.what = MSG_GALLERY;
		message.obj = page;
		handler.sendMessage(message);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		container = (RelativeLayout) findViewById(R.id.container);
		detailView = (DetailView) findViewById(R.id.detail_view);
		toolbar = (ToolBarView) findViewById(R.id.tool_bar);
		ToolBarListener toolBarListener = new ToolBarListener(this);
		toolBarListener.setContextHandler(handler);
		toolbar.addToolBarClickListener(toolBarListener);
		ChannelItemBean channleItem = (ChannelItemBean) getIntent()
				.getSerializableExtra("channel_obj");
		toolbar.setCurrentNews(channleItem);
		String src = channleItem.getId();
		RequestVoNews vo = new RequestVoNews(src, this, new GalleryParser(
				new DataCallback<NewsGalleryUnit>() {
					@Override
					public void processData(NewsGalleryUnit paramObject,
							boolean paramBoolean) {
						galleryData = paramObject;
						showGallery(paramObject);
						getComments();
					}
				}));
		getDataFromServer(vo);
		showGallery();
	}

	private void showGallery() {
		View view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.slide, null);
		RelativeLayout galleryLayout = (RelativeLayout) view
				.findViewById(R.id.container);
		PageGallery gallery = (PageGallery) galleryLayout
				.findViewById(R.id.gallery);
		adapter = new SlideGalleryAdapter(this, null, galleryLayout);
		gallery.setAdapter(adapter);
		gallery.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				sendGallery(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		detailView.getWorkspace().addView(view);
	}

	private void showGallery(NewsGalleryUnit paramObject) {
		adapter.setDatasource(paramObject);
		adapter.flushContainerTexts(0);
	}

	@Override
	public String getCommentsUrl() {
		return galleryData.getMeta().getDocumentId();
	}

	private void getComments() {
		String url = CommentsManager.getUrl(getCommentsUrl(), 1, "");
		RequestVoNews reqVo = new RequestVoNews(url, this, new CommentsParser(
				new DataCallback<CommentsData>() {
					@Override
					public void processData(CommentsData paramObject,
							boolean paramBoolean) {
						setupListAdapter(paramObject);
					}
				}));
		getDataFromServer(reqVo);
	}

}
