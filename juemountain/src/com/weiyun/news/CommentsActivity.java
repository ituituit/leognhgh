package com.weiyun.news;

import android.app.Activity;

import com.cheesemobile.util.RequestVoNews;
import com.juetc.CommentsManager;
import com.weiyun.adapter.CommentsAdapter;
import com.weiyun.domain.DocUnit;
import com.weiyun.domain.comment.entity.CommentsData;
import com.weiyun.newhardware.parser.CommentsParser;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;

public class CommentsActivity extends BaseActivity {
	private DocUnit unit2;
	private CommentsAdapter commentsAdapter;

	private void refreshList(final int page) {
		String url = CommentsManager.getUrl(unit2.getBody().getCommentsUrl(),
				page, "");
		RequestVoNews reqVo = new RequestVoNews(url, this, new CommentsParser(
				new DataCallback<CommentsData>() {
					@Override
					public void processData(CommentsData paramObject,
							boolean paramBoolean) {
						commentsAdapter.appendDataSource(paramObject);
						commentsAdapter.notifyDataSetChanged();
					}
				}));
		getDataFromServer(reqVo);
	}
}
