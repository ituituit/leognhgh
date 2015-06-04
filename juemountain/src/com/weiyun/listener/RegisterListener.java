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

public class RegisterListener implements ToolBarClickListener {
	private BaseActivity context;
	private Handler contextHandler;

	public void setContextHandler(Handler contextHandler) {
		this.contextHandler = contextHandler;
	}

	public RegisterListener(BaseActivity context) {
		super();
		this.context = context;
	}

	@Override
	public void onClick(View view) {
//		switch (view.getId()) {
//		case ToolBarView.BACK_ID:
//			context.onBackPressed();
//			break;
//
//		case ToolBarView.COMMENTS_ID:
//			if (contextHandler != null) {
//				Message message = new Message();
//				message.what = DetailActivity.MSG_COMMENT;
//				contextHandler.sendMessage(message);
//			}
//			break;
//		}
	}

	@Override
	public void onReceiveUploadUrl(String str) {
		Toast.makeText(context.getApplicationContext(), "正在注册...",
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
											"注册成功，请登录邮箱激活。", Toast.LENGTH_LONG)
											.show();
									context.onBackPressed();
								} else {
									Toast.makeText(
											context.getApplicationContext(),
											paramObject.getReason(), Toast.LENGTH_SHORT)
											.show();
								}
							}
						}));
		context.getDataFromServer(vo);
	}
}