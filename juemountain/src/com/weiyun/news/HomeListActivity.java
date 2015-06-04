package com.weiyun.news;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheese.db.QueryDao;
import com.cheesemobile.util.Constants;
import com.cheesemobile.util.RequestVoNews;
import com.juetc.CommentsManager;
import com.juetc.OfflineManager;
import com.juetc.news.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.weiyun.adapter.HomeGalleryAdapter;
import com.weiyun.adapter.HomeListAdapter;
import com.weiyun.domain.channel.entity.ChannelItemBean;
import com.weiyun.domain.channel.entity.ChannelList;
import com.weiyun.domain.channel.entity.ChannelListUnit;
import com.weiyun.domain.gallery.entity.NewsGalleryUnit;
import com.weiyun.domain.gallery.entity.SlideItem;
import com.weiyun.domain.gallery.entity.SlideItemList;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.newhardware.parser.HomeListParser;
import com.weiyun.news.AddableListView.LoadMoreRowListener;
import com.weiyun.news.RefreshView.PullToRefreshListener;

public class HomeListActivity extends BaseActivity implements OnClickListener {
	public final static int startPage = 1;
	private int channelIndex;
	private String pageChannelName;
	private RefreshListView refreshListView;
	private HomeListAdapter hla;
	private View galleryLayout;
	public static int MSG_REFRESH = 0;
	public static int MSG_GALLERY = 2;
	private boolean _inited = false;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_REFRESH) {
				refreshList((int) msg.obj);
			}
			if (msg.what == MSG_GALLERY) {
				adapter.flushContainerTexts((int) msg.obj);
			}
		};
	};

	public void flushContent() {
		if (!_inited) {
			refreshList(startPage);
		}
	}

	private PullToRefreshListener listener;

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

	private String getRefreshUrl(int page) {
		return CommentsManager.getHomeListUrl(pageChannelName, page);
	}

	RequestVoNews refreshVo = new RequestVoNews("", this, new HomeListParser(
			new DataCallback<ChannelList>() {
				@Override
				public void processData(ChannelList paramObject,
						boolean paramBoolean) {
					ChannelListUnit unit0 = paramObject.getClu().get(0);
					ChannelListUnit unitGallery = null;
					if (paramObject.getClu().size() > 1) {
						unitGallery = paramObject.getClu().get(1);
					}
					int currentPage = unit0.getCurrentPage();
					int maxPage = unit0.getTotalPage();
					if (currentPage == startPage) {
						setupListAdapter(unit0.getItem());
						if (unitGallery == null) {
							removeHeader();
						} else {
							showGallery(convertToGallery(unitGallery));
						}
						// String defaultListUrl =
						// CommentsManager.getDefaultListUrl(pageChannelName);
						// RequestVoNews refreshVo = new
						// RequestVoNews(defaultListUrl, HomeListActivity.this,
						// new HomeListParser(
						// new DataCallback<ChannelList>() {
						// @Override
						// public void processData(ChannelList paramObject,
						// boolean paramBoolean) {
						// ChannelListUnit unit0 = paramObject.getClu().get(0);
						// showGallery(convertToGallery(unit0));
						// }
						// }));
						// getDataFromServer(refreshVo);
					} else {
						hla.appendDataSource(paramObject.getClu().get(0)
								.getItem());
					}
					OfflineManager.saveChannelItem(getApplicationContext(),
							unit0.getItem());
					hla.notifyDataSetChanged();
					listener.finishRefreshing();
					refreshListView.setPage(currentPage, maxPage);
					refreshListView
							.addLoadMoreRowListener(new LoadMoreRowListener() {
								@Override
								public void load(int newPage, int count) {
									sendRefreshListMSG(newPage);
								}
							});
				}

				private NewsGalleryUnit convertToGallery(ChannelListUnit unit0) {
					ArrayList<ChannelItemBean> item = unit0.getItem();
					NewsGalleryUnit unit = new NewsGalleryUnit();
					SlideItemList bodyList = new SlideItemList();
					List<SlideItem> slides = new ArrayList<>();
					bodyList.setSlides(slides);
					unit.setBody(bodyList);
					for (ChannelItemBean channelItemBean : item) {
						int channelType = channelItemBean
								.intType(channelItemBean.getType());
						// if (channelType == ChannelItemBean.TYPE_SLIDE) {
						String id = channelItemBean.getId();
						String title = channelItemBean.getTitle();
						String img = channelItemBean.getImages().get(0);
						SlideItem si = new SlideItem();
						si.setImage(img);
						si.setTitle(title);
						si.setId(id);
						slides.add(si);
						// }
					}
					return unit;
				}
			}));
	private HomeGalleryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_list);
		channelIndex = getIntent().getIntExtra("pageIndex", -1);
		pageChannelName = getIntent().getStringExtra("pageChannel");
		galleryLayout = LayoutInflater.from(this).inflate(
				R.layout.gallery_layout, null);
		refreshListView = (RefreshListView) findViewById(R.id.lv_main);
		refreshListView.getListView().addHeaderView(galleryLayout);
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).build();
	}

	private void removeHeader() {
		galleryLayout.setVisibility(View.GONE);
		refreshListView.getListView().removeHeaderView(galleryLayout);
	}

	private void showGallery(NewsGalleryUnit paramObject) {
		if (paramObject == null
				|| paramObject.getBody().getSlides().size() == 0) {
			Log.e(this.getClass() + "", "error: showGallery callback null");
			removeHeader();
			return;
		}
		adapter = new HomeGalleryAdapter(this, paramObject, galleryLayout);
		final PageGallery gallery = (PageGallery) galleryLayout
				.findViewById(R.id.gallery);
		gallery.setAdapter(adapter);
		adapter.setImgScaleType(ScaleType.CENTER_CROP);
		adapter.flushContainerTexts(0);
		adapter.setOnClickLisener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SlideItem slideItem = adapter.getDatasource().getBodyList()
						.get(gallery.getCurrentItem());
				String id = slideItem.getId();
				List<ChannelItemBean> dataSource = hla.getDataSource();
				ChannelItemBean channelItemBean = null;
				for (int i = 0; i < dataSource.size(); i++) {
					if (dataSource.get(i).getId().equals(id)) {
						channelItemBean = hla.getDataSource().get(i);
						break;
					}
				}
				if (channelItemBean != null) {
					startArticle(channelItemBean);
				}
			}
		});
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

		gallery.addPageChangedListener(new PageChangedListener() {
			@Override
			public void changed(int page) {
				adapter.flushContainerTexts(page);
			}
		});
	}

	public void refreshList(final int page) {
		refreshVo.requestUrl = getRefreshUrl(page);
		getDataFromServer(refreshVo);
		this.addNetErrorListener(new NetErrorListener() {
			@Override
			public void onNullObjectReturned() {
				_inited = false;
			}

			@Override
			public void onNetFailedReceived(boolean hasNetWork) {
				QueryDao dao = new QueryDao(getApplicationContext());
				List<ChannelItemBean> queryByUser = dao.queryByChannel(""
						+ ChannelListUnit.intTypeC(pageChannelName));
				// setupListAdapter(queryByUser);
				if (hla == null) {
					setupListAdapter(queryByUser);
					removeHeader();
				} else {
					// hla.appendDataSource(queryByUser);
					// hla.notifyDataSetChanged();
				}
				Toast.makeText(HomeListActivity.this, "网络无连接",
						Toast.LENGTH_SHORT).show();
				_inited = false;
			}
		});
		_inited = true;
	}

	private void setupListAdapter(List<ChannelItemBean> arrayList) {
		hla = new HomeListAdapter(this, arrayList,
				refreshListView.getListView());
		refreshListView.getListView().setAdapter(hla);
		listener = new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				sendRefreshListMSG(1);
				this.refreshing();
			}

			@Override
			public void refreshing() {
				refreshListView.getRefreshView().refreshing();
			}

			@Override
			public void finishRefreshing() {
				refreshListView.getRefreshView().finishRefreshing();
			}
		};

		refreshListView.getRefreshView().setOnRefreshListener(listener,
				channelIndex);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home_list,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getTag().getClass() == HomeListAdapter.ViewHolder.class) {
			int idv = v.getId();
			ChannelItemBean channelItemBean = hla.getDataSource().get(idv);
			startArticle(channelItemBean);
		}
	}

	private void startArticle(ChannelItemBean channelItemBean) {
		int channelType = channelItemBean.intType(channelItemBean.getType());
		switch (channelType) {
		case ChannelItemBean.TYPE_SLIDE:
			startSlide(channelItemBean);
			break;
		default:
			startDetail(channelItemBean);
			break;
		}
	}

	private void startDetail(ChannelItemBean channelItemBean) {
		Intent intent;
		Bundle bundle;
		intent = new Intent(this, DetailActivity.class);
		bundle = new Bundle();
		bundle.putSerializable("channel_obj", channelItemBean);
		intent.putExtras(bundle);
		intent.putExtra("channel_obj", channelItemBean);
		startActivity(intent);
	}

	private void startSlide(ChannelItemBean channelItemBean) {
		Intent intent = new Intent(this, DetailGalleryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("channel_obj", channelItemBean);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private static class ViewHolder {
		TextView text;
		ImageView image;
	}

	String[] imageUrls = Constants.IMAGES;

	class ImageAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

		ImageAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = inflater.inflate(
						R.layout.activity_main_news_title_1_pic, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.title);
				holder.image = (ImageView) view.findViewById(R.id.image);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.text.setText("Item " + (position + 1));
			ImageLoader.getInstance().displayImage(imageUrls[position],
					holder.image, options, animateFirstListener);
			return view;
		}
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	DisplayImageOptions options;

}
