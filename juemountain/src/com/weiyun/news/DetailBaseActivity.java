package com.weiyun.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.widget.RelativeLayout;

import com.cheesemobile.util.Constants;
import com.cheesemobile.util.RequestVoNews;
import com.juetc.CommentsManager;
import com.juetc.news.R;
import com.weiyun.adapter.CommentsAdapter;
import com.weiyun.adapter.CommentsAdapter.CommentListener;
import com.weiyun.domain.VerifyResult;
import com.weiyun.domain.comment.entity.Comment;
import com.weiyun.domain.comment.entity.CommentsData;
import com.weiyun.listener.ToolBarListener;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.newhardware.parser.CommentsParser;
import com.weiyun.newhardware.parser.VerifyResultParser;
import com.weiyun.news.AddableListView.LoadMoreRowListener;
import com.weiyun.scroller.DetailView;
import com.weiyun.util.SharaedPreferences;

public class DetailBaseActivity extends BaseActivity {
	public DetailView detailView;
	private RelativeLayout container;
	ToolBarView toolbar;
	private CommentsAdapter commentsAdapter;
	public static int MSG_REFRESH = 0;
	public static int MSG_COMMENT = 1;
	public static int MSG_COMMENT_REFRESH = 2;

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
	}

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_REFRESH) {
				refreshList((int) msg.obj);
			}
			if (msg.what == MSG_COMMENT) {
				commentView();
			}
			if (msg.what == MSG_COMMENT_REFRESH) {
				commentView();
				refreshList(1);
			}
		};
	};
	private View commentView;

	@JavascriptInterface
	public void toCommentView() {
		Message message = new Message();
		message.what = MSG_COMMENT;
		handler.sendMessage(message);
	}

	@JavascriptInterface
	public boolean themeNight() {
		return SharaedPreferences.getTheme(getApplicationContext()) == R.style.DarkTheme;
	}

	public void commentView() {
		detailView.getWorkspace().scrollRightNow();
	}

	public String getCommentsUrl() {
		return null;
	}

	public void refreshList(final int page) {
		String url = CommentsManager.getUrl(getCommentsUrl(), page, "");
		RequestVoNews reqVo = new RequestVoNews(url, this, new CommentsParser(
				new DataCallback<CommentsData>() {
					@Override
					public void processData(CommentsData paramObject,
							boolean paramBoolean) {
						commentsAdapter.appendDataSource(paramObject);
						commentsAdapter.notifyDataSetChanged();
						if (page == 1) {
							noComment(paramObject);
						}
					}
				}));
		getDataFromServer(reqVo);
	}

	public void sendRefreshListMSG(int page) {
		Message message = new Message();
		message.what = MSG_REFRESH;
		message.obj = page;
		handler.sendMessage(message);
	}

	public void noComment(CommentsData pCommentsData) {
		boolean noComment = pCommentsData.getComments().getHottest().size()
				+ pCommentsData.getComments().getNewest().size() == 0;
		View findViewById = commentView.findViewById(R.id.no_comment);
		if (noComment) {
			findViewById.setVisibility(View.VISIBLE);
		} else {
			findViewById.setVisibility(View.GONE);
		}
		findViewById.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				toolbar.showWriteReview();
			}
		});
	}

	public void setupListAdapter(CommentsData pCommentsData) {
		commentView = LayoutInflater.from(container.getContext()).inflate(
				R.layout.widget_comment_view, null);
		noComment(pCommentsData);
		AddableListView lv = (AddableListView) commentView
				.findViewById(R.id.list_view);
		lv.setPage(
				1,
				(int) Math.ceil((float) pCommentsData.getCount()
						/ CommentsManager.pageSize));
		lv.addLoadMoreRowListener(new LoadMoreRowListener() {
			@Override
			public void load(int newPage, int count) {
				sendRefreshListMSG(newPage);
			}
		});
		commentsAdapter = new CommentsAdapter(this, pCommentsData,
				lv.getListView());
		lv.getListView().setAdapter(commentsAdapter);
		commentsAdapter.setCommentLisener(new CommentListener() {
			@Override
			public void onRecommend(Comment comment) {
				String str = Constants.voteUrl + "&cmtId=" + comment.getId()
						+ "&docUrl=" + comment.getDoc_url();
				RequestVoNews vo = new RequestVoNews(str,
						getApplicationContext(), new VerifyResultParser(
								new DataCallback<VerifyResult>() {
									@Override
									public void processData(
											VerifyResult paramObject,
											boolean paramBoolean) {
									}
								}));
				getDataFromServer(vo);
			}
		});
		detailView.getWorkspace().addView(commentView);
	}
}
