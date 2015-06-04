package com.weiyun.listener;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.cheesemobile.util.RequestVoNews;
import com.juetc.TencentService;
import com.juetc.news.R;
import com.weiyun.domain.UserInfo;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.newhardware.parser.UserInfoParser;
import com.weiyun.news.BaseActivity;
import com.weiyun.news.LoginActivity;
import com.weiyun.news.ToolBarView.ToolBarClickListener;

public class LoginListener implements ToolBarClickListener {
	private BaseActivity context;
	private Handler contextHandler;

	public void setContextHandler(Handler contextHandler) {
		this.contextHandler = contextHandler;
	}

	public LoginListener(BaseActivity context) {
		super();
		this.context = context;
	}

	@Override
	public void onClick(View view) {
		// switch (view.getId()) {
		// case ToolBarView.BACK_ID:
		// context.onBackPressed();
		// break;
		//
		// case ToolBarView.COMMENTS_ID:
		// if (contextHandler != null) {
		// Message message = new Message();
		// message.what = DetailActivity.MSG_COMMENT;
		// contextHandler.sendMessage(message);
		// }
		// break;
		// }
	}

	@Override
	public void onReceiveUploadUrl(String str) {
		Toast.makeText(context.getApplicationContext(), "正在登录...",
				Toast.LENGTH_SHORT).show();
		RequestVoNews vo = new RequestVoNews(str,
				context.getApplicationContext(), new UserInfoParser(
						new DataCallback<UserInfo>() {
							@Override
							public void processData(UserInfo paramObject,
									boolean paramBoolean) {
								if (paramObject.getVr().getResult()) {
									Toast.makeText(
											context.getApplicationContext(),
											context.getResources().getString(
													R.string.login_ready),
											Toast.LENGTH_LONG).show();
									TencentService.getInstance(context).init(
											paramObject);
									((LoginActivity) context).sendLoginMSG();
								} else {
									Toast.makeText(
											context.getApplicationContext(),
											paramObject.getVr().getReason(),
											Toast.LENGTH_LONG).show();
								}
							}
						}));
		context.getDataFromServer(vo);
	}
}