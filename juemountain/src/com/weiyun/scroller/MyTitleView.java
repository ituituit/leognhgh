package com.weiyun.scroller;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juetc.news.R;
import com.weiyun.scroller.Workspace.SnapToScreenListener;

public class MyTitleView extends LinearLayout implements OnClickListener {
	private ImageView iv_home;
	private TitleGroup vg_title;
	private LinearLayout ll_title;
	private LinearLayout ll_content;
	private List<String> strs;
	private Workspace ws_main;
	// private ImageView iv_next;
	// private ImageView iv_previous;
	private Context context;

	public MyTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		vg_title = new TitleGroup(context);
		View view = LayoutInflater.from(context).inflate(
				R.layout.order_scroller_title, this, true);
		ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
		ll_title = (LinearLayout) view.findViewById(R.id.ll_title);
		// iv_previous = (ImageView) view.findViewById(R.id.iv_previous);
		// iv_next = (ImageView) view.findViewById(R.id.iv_next);
		// iv_previous.setOnClickListener(this);
		// iv_next.setOnClickListener(this);
		vg_title.allowTouch = true;
		this.context = context;
	}

	public void createViews() {
		for (int i = 0; i < strs.size(); i++) {
			TextView v = (TextView) LinearLayout.inflate(context,
					R.layout.order_scroller_title_item, null);
			v.setText(strs.get(i));
			v.setPadding(20, 0, 20, 0);
			v.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
			v.setSingleLine();
			v.setId(0);
			v.setTag(i);
			v.setOnClickListener(this);
			vg_title.addView(v);
		}
		ll_title.addView(vg_title, 0, new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.FILL_PARENT));

		ws_main = new Workspace(context, this);
		ws_main.addSnapToScreenListener(new SnapToScreenListener() {
			@Override
			public void snapToScreen(int whichScreen) {
				MyTitleView.this.titleSnapToScreen(whichScreen);
			}
		});
		ll_content.addView(ws_main);
	}
	public void removeWorkspaceView(){
		ll_content.removeView(ws_main);
	}

	public TitleGroup getTitleGroup() {
		return vg_title;
	}

	public void setTitle(String text) {
	}

	public ImageView getHomeIcon() {
		return iv_home;
	}

	public List<String> getStrs() {
		return strs;
	}

	public void setStrs(List<String> strs) {
		this.strs = strs;
	}

	public Workspace getMain() {
		return ws_main;
	}

	public void titleSnapToScreen(int whichScreen) {
		vg_title.snapToScreen(whichScreen);
	}

	public void contentSnapToScreen(int whichScreen) {
		ws_main.snapToScreen(whichScreen);
	}

	private long LastClickTime = System.currentTimeMillis();
	int fastCurrentScreen = 0;

	public void onClick(View v) {
		if (System.currentTimeMillis() - LastClickTime > 600) {
			fastCurrentScreen = ws_main.getCurrentScreen();
			LastClickTime = System.currentTimeMillis();
		}
		switch (v.getId()) {
		case 0:
			ws_main.snapToScreen((int) v.getTag());
			break;

		// case R.id.iv_previous:
		// System.out.println(System.currentTimeMillis() - LastClickTime);
		// ws_main.scrollLeftNow();
		// break;
		// case R.id.iv_next:
		// ws_main.scrollRightNow();
		// break;
		}
	}
}
