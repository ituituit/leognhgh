package com.weiyun.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.juetc.TencentService;
import com.juetc.news.R;

public class LoginDetailActivity extends BaseActivity implements
		OnClickListener {
	private TencentService tencentService;
	int MSG_LOGIN = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_LOGIN) {
//				loginText.setText("退出登陆");
			}
		};
	};
	private ImageButton loginText;

	public void sendLoginMSG() {
		Message message = new Message();
		message.what = MSG_LOGIN;
		handler.sendMessage(message);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_regsiter);
		loginText = (ImageButton) findViewById(R.id.qq_login_button);
		loginText.setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		
	}
}
