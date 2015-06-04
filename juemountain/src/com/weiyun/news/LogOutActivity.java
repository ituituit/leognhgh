package com.weiyun.news;

import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.juetc.TencentService;
import com.juetc.news.R;
import com.weiyun.util.SharaedPreferences;

public class LogOutActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout);
		Button logout = (Button)findViewById(R.id.logout);
		ImageView  icon = (ImageView)findViewById(R.id.user_icon);
		TextView nickname = (TextView)findViewById(R.id.user_name);
		nickname.setOnClickListener(this);
		logout.setOnClickListener(this);
		icon.setOnClickListener(this);
		Map<String, String> loginInfo = SharaedPreferences.getLoginInfo(getApplicationContext());
		nickname.setText(loginInfo.get("nickname"));
		LoginActivity.getImage(icon, loginInfo.get("imgUrl"));
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.logout:
			TencentService.getInstance(getApplicationContext()).logout(this);
			setResult(LoginActivity.resultCodeChanged);
			finish();
			break;
		default:
			break;
		}
	}
}
