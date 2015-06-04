package com.weiyun.news;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.cheese.db.QueryDao;
import com.juetc.news.R;
import com.weiyun.adapter.HomeListAdapter;
import com.weiyun.domain.channel.entity.ChannelItemBean;

public class BookmarkActivity extends BaseActivity implements OnClickListener {

	private HomeListAdapter hla;
	private AddableListView refreshListView;
	public static int MSG_REFRESH = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_list_bookmark);
		refreshListView = new AddableListView(getApplicationContext(), null);
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
		container.addView(refreshListView);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		QueryDao dao = new QueryDao(getApplicationContext());
		List<ChannelItemBean> queryByUser = dao.queryByUser();
		setupListAdapter(queryByUser);
	}

	private void setupListAdapter(List<ChannelItemBean> arrayList) {
		hla = new HomeListAdapter(this, arrayList,
				refreshListView.getListView());
		refreshListView.getListView().setAdapter(hla);
	}

	@Override
	public void onClick(View v) {
		if (v.getTag().getClass() == HomeListAdapter.ViewHolder.class) {
			int idv = v.getId();
			ChannelItemBean channelItemBean = hla.getDataSource().get(idv);
			int channelType = channelItemBean
					.intType(channelItemBean.getType());
			Intent intent;
			Bundle bundle;
			switch (channelType) {
			case ChannelItemBean.TYPE_SLIDE:
				startSlide(channelItemBean);
				break;
			default:
				intent = new Intent(this, DetailActivity.class);
				bundle = new Bundle();
				bundle.putSerializable("channel_obj", channelItemBean);
				intent.putExtras(bundle);
				intent.putExtra("channel_obj", channelItemBean);
				startActivity(intent);
				break;
			}
		}
	}

	private void startSlide(ChannelItemBean channelItemBean) {
		Intent intent = new Intent(this, DetailGalleryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("channel_obj", channelItemBean);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
