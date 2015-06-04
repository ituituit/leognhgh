package com.weiyun.news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.juetc.news.R;
import com.weiyun.scroller.MutableViewWorkspace;
import com.weiyun.scroller.Workspace;

//

public class SettingView extends LinearLayout implements OnClickListener {
	private LinearLayout ll_content;
	private Workspace ws_main;
	protected Context context;

	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(
				R.layout.order_scroller_title, this, true);
		View findViewById = findViewById(R.id.title_tab_bar);
		findViewById.setVisibility(View.GONE);
		ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
		this.context = context;
		ws_main = new MutableViewWorkspace(context, this);
		ll_content.addView(ws_main);
	}

	@Override
	public void onClick(View arg0) {

	}

	public Workspace getWorkspace() {
		return ws_main;
	}

}