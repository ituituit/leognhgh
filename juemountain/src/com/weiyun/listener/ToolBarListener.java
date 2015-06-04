package com.weiyun.listener;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.cheesemobile.util.RequestVoNews;
import com.weiyun.domain.VerifyResult;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.newhardware.parser.VerifyResultParser;
import com.weiyun.news.BaseActivity;
import com.weiyun.news.DetailActivity;
import com.weiyun.news.ToolBarView;
import com.weiyun.news.ToolBarView.ToolBarClickListener;

public class ToolBarListener implements ToolBarClickListener {
	private BaseActivity context;
	private Handler contextHandler;

	public void setContextHandler(Handler contextHandler) {
		this.contextHandler = contextHandler;
	}

	public ToolBarListener(BaseActivity context) {
		super();
		this.context = context;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case ToolBarView.BACK_ID:
			context.onBackPressed();
			break;

		case ToolBarView.COMMENTS_ID:
			if (contextHandler != null) {
				Message message = new Message();
				message.what = DetailActivity.MSG_COMMENT;
				contextHandler.sendMessage(message);
			}
			break;
		}
	}

	@Override
	public void onReceiveUploadUrl(String str) {
		Toast.makeText(context.getApplicationContext(), "正在发送评论。。。",
				Toast.LENGTH_SHORT).show();
		RequestVoNews vo = new RequestVoNews(str,
				context.getApplicationContext(), new VerifyResultParser(
						new DataCallback<VerifyResult>() {
							@Override
							public void processData(VerifyResult paramObject,
									boolean paramBoolean) {
								if (paramObject.getResult()) {
									Toast.makeText(
											context.getApplicationContext(),
											"评论成功，谢谢参与！", Toast.LENGTH_SHORT)
											.show();
									if (contextHandler != null) {
										Message message = new Message();
										message.what = DetailActivity.MSG_COMMENT_REFRESH;
										contextHandler.sendMessage(message);
									}
								} else {
									Toast.makeText(
											context.getApplicationContext(),
											"发布失败，稍后再试吧。", Toast.LENGTH_SHORT)
											.show();
								}
							}
						}));
		context.getDataFromServer(vo);
	}
}