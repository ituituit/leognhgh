package com.weiyun.news;

import java.util.ArrayList;
import java.util.List;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.juetc.TencentService;
import com.juetc.news.R;
import com.weiyun.adapter.IndexGridAdapter;
import com.weiyun.domain.EditTextVerify;
import com.weiyun.domain.EditTextVerify.Pattern;
import com.weiyun.news.ChannelSettingActivity.EditAddableViewListener;
import com.weiyun.news.ToolBarView.ToolBarClickListener;
import com.weiyun.scroller.Workspace;
import com.weiyun.scroller.Workspace.SnapToScreenListener;

//

public class DetailSettingView extends SettingView implements OnClickListener {

	private LocalActivityManager activitymanager;
	private View createChannelSetting;
	private View createLoginSetting;
	// private boolean addedCreateChannelSetting = false;

	private ArrayList<String> showList = new ArrayList<>();
	private ArrayList<String> hideList = new ArrayList<>();
	private DragGridView grd_electron;
	private DragGridView grd_electron2;
	private View createRegisterSetting;
	private Workspace workspace;

	public DetailSettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		workspace = getWorkspace();
		workspace.addSnapToScreenListener(new SnapToScreenListener() {
			@Override
			public void snapToScreen(int next) {
				if (workspace.getCurrentScreen() > next) {
					View childAt = workspace.getChildAt(workspace
							.getChildCount() - 1);
					workspace.removeView(childAt);
				}
			}
		});
		createChannelSetting = createChannelSetting();
		createLoginSetting = createLoginSetting();
		createRegisterSetting = createRegisterSetting();
		createPannelsetting = createShowSetting();
	}

	public void flush() {
		if (TencentService.getInstance(context).isLogin()) {
			getWorkspace().removeAllViews();
			showSetting();
		} else {
			getWorkspace().removeAllViews();
			showLogin();
		}
	}

	private void showSetting() {
		getWorkspace().addView(createPannelsetting);
	}

	public void showLogin() {
		getWorkspace().addView(createLoginSetting);
	}

	public void showRegister() {
		getWorkspace().addView(createRegisterSetting);
	}

	private void showChannel() {
		getWorkspace().addView(createChannelSetting);
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.story_home:
			intent = new Intent(context, MainActivity.class);
			intent.putExtra("data", LoginActivity.MSG_HOME_RETURN);
			context.startActivity(intent);
			break;
		case R.id.comment_email:
			StringBuilder localStringBuilder1 = new StringBuilder();
			localStringBuilder1.append("mailto:").append("wycm2188858@163.com");
			localStringBuilder1.append("?subject=").append("意见箱");
			StringBuilder localStringBuilder2 = localStringBuilder1
					.append("&body=");
			StringBuilder localStringBuilder3 = new StringBuilder();
			localStringBuilder3.append("系统:").append("android");
			localStringBuilder3.append("\n");
			localStringBuilder2.append(localStringBuilder3.toString());
			Intent localIntent2 = new Intent("android.intent.action.SENDTO",
					Uri.parse(localStringBuilder1.toString()));
			context.startActivity(localIntent2);
			break;
		case R.id.channel_setting:
			showChannel();
			break;
		case R.id.bookmark_my:
			intent = new Intent(context,BookmarkActivity.class);
			context.startActivity(intent);
			break;
		case R.id.registerBtn:
			showRegister();

			break;
		}
	}

	public ArrayList<String> getChannelShow(boolean show) {
		if (show) {
			return showList;
		} else {
			return hideList;
		}
	}

	public void setChannelList(boolean show, ArrayList<String> list) {
		if (show) {
			showList = list;
			((IndexGridAdapter) grd_electron.getAdapter()).reload(showList);
		} else {
			hideList = list;
			((IndexGridAdapter) grd_electron2.getAdapter()).reload(hideList);
		}
	}

	private View createChannelSetting() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.activity_channel_setting, null);
		View splitTag = findViewById(R.id.split_tag);
		grd_electron = (DragGridView) view.findViewById(R.id.grd_electron);
		grd_electron2 = (DragGridView) view.findViewById(R.id.more_channel);
		List<List<String>> list1 = new ArrayList<List<String>>();
		List<List<String>> list2 = new ArrayList<List<String>>();
		list1.add(showList);
		list2.add(hideList);
		grd_electron.setAdapter(new IndexGridAdapter(context, list1));
		grd_electron2.setAdapter(new IndexGridAdapter(context, list2));
		grd_electron2.addListener(new EditAddableViewListener(grd_electron,
				grd_electron2));
		grd_electron.addListener(new EditAddableViewListener(grd_electron2,
				grd_electron));
		return view;
	}

	private View createShowSetting() {
		View view = LayoutInflater.from(context).inflate(R.layout.setting_view,
				null);
		// >>>>>>>
		Button homeBtn = (Button) view.findViewById(R.id.story_home);
		Button emailBtn = (Button) view.findViewById(R.id.comment_email);
		Button channelBtn = (Button) view.findViewById(R.id.channel_setting);
		Button bookmarkBtn = (Button) view.findViewById(R.id.bookmark_my);
		// <<<<<<<
		homeBtn.setOnClickListener(this);
		emailBtn.setOnClickListener(this);
		channelBtn.setOnClickListener(this);
		bookmarkBtn.setOnClickListener(this);
		return view;
	}

	private View createLoginSetting() {
		View view = LayoutInflater.from(context).inflate(R.layout.login_layout,
				null);
		EditText email = (EditText) view.findViewById(R.id.editTextEmail);
		EditText password = (EditText) view.findViewById(R.id.editTextPass);
		final EditTextVerify verifyEmail = new EditTextVerify(email,
				Pattern.patternEmail, this.getResources().getString(
						R.string.email_format));
		final EditTextVerify verifyPassword = new EditTextVerify(password,
				Pattern.patternDefPassword, this.getResources().getString(
						R.string.pass_format));
		Button loginBtn = (Button) view.findViewById(R.id.loginBtn);
		Button registerBtn = (Button) view.findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(this);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (verifyEmail.verify() && verifyPassword.verify()) {
					String login = TencentService.login(
							verifyEmail.getEtText(), verifyPassword.getEtText());
					if (listenerLogin != null) {
						listenerLogin.onReceiveUploadUrl(login);
					}
				}
			}
		});
		return view;
	}

	private View createRegisterSetting() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.login_regsiter, null);
		EditText email = (EditText) view.findViewById(R.id.editTextEmail);
		EditText password = (EditText) view.findViewById(R.id.editTextPass);
		EditText password2 = (EditText) view.findViewById(R.id.editTextPassRe);
		final EditTextVerify verifyEmail = new EditTextVerify(email,
				Pattern.patternEmail, this.getResources().getString(
						R.string.email_format));
		final EditTextVerify verifyPassword = new EditTextVerify(password,
				Pattern.patternDefPassword, this.getResources().getString(
						R.string.pass_format));
		final EditTextVerify verifyPassword2 = new EditTextVerify(password2,
				Pattern.patternDefPassword, this.getResources().getString(
						R.string.pass_format));

		Button loginBtn = (Button) view.findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (verifyEmail.verify() && verifyPassword.verify()
						&& verifyPassword2.verify(verifyPassword)) {
					String register = TencentService.register(
							verifyEmail.getEtText(), verifyPassword.getEtText());
					if (listener != null) {
						listener.onReceiveUploadUrl(register);
					}
				}
			}
		});
		return view;
	}

	private ToolBarClickListener listener;
	private ToolBarClickListener listenerLogin;
	private View createPannelsetting;

	public void setRegisterListener(ToolBarClickListener listener) {
		this.listener = listener;
	}

	public void setLoginListener(ToolBarClickListener listener) {
		this.listenerLogin = listener;
	}

	public boolean onBackPressed() {
		if (workspace.getChildCount() == 1) {
			return false;
		}
		workspace.scrollLeftNow();
		return true;
	}
}