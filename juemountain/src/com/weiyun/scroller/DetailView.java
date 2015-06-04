package com.weiyun.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.juetc.news.R;

//

public class DetailView extends LinearLayout implements OnClickListener {
	private LinearLayout ll_content;
	private Workspace ws_main;
	private Context context;
	

	public DetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(
				R.layout.order_scroller_title, this, true);
		View findViewById = findViewById(R.id.title_tab_bar);
		findViewById.setVisibility(View.GONE);
		ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
		this.context = context;
		ws_main = new Workspace(context, this);
		ll_content.addView(ws_main);
//		initWeiyunWebView();
		// wwv.addJavascriptInterface(docBody, "datas");
		// wwv.loadUrl("file:///android_asset/detail_page.html");
		// this.ll_content.addView(wwv);
	}

	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	public Workspace getWorkspace() {
		return ws_main;
	}
}

// public class DetailView extends LinearLayout implements OnClickListener {
// // private WeiyunWebView wwv;
//
//
// private ImageView iv_home;
// private TitleGroup vg_title;
// private LinearLayout ll_title;
// private LinearLayout ll_content;
// private List<String> strs;
// private Workspace ws_main;
// // private ImageView iv_next;
// // private ImageView iv_previous;
// private Context context;
//
// public Workspace getWorkspace() {
// return ws_main;
// }
//
// // public DetailView(Context paramContext, View paramView) {
// // super(paramContext);
// // View view = LayoutInflater.from(paramContext).inflate(
// // R.layout.order_scroller_title, this, true);
// // ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
// // initWeiyunWebView(paramContext);
// // }
//
// public DetailView(Context context, AttributeSet attrs) {
// super(context, attrs);
// vg_title = new TitleGroup(context);
// View view = LayoutInflater.from(context).inflate(
// R.layout.order_scroller_title, this, true);
// ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
// ll_title = (LinearLayout) view.findViewById(R.id.ll_title);
// // iv_previous = (ImageView) view.findViewById(R.id.iv_previous);
// // iv_next = (ImageView) view.findViewById(R.id.iv_next);
// // iv_previous.setOnClickListener(this);
// // iv_next.setOnClickListener(this);
// vg_title.allowTouch = true;
// this.context = context;
// }
//
// private void initWeiyunWebView(Context paramContext) {
//
// ws_main = new Workspace(paramContext, this);
// // ws_main.addSnapToScreenListener(new SnapToScreenListener() {
// // @Override
// // public void snapToScreen(int whichScreen) {
// // DetailView.this.titleSnapToScreen(whichScreen);
// // }
// // });
// ll_content.addView(ws_main);
// // wwv = new WeiyunWebView(this.getContext());
// // ll_content.addView(wwv);
//
// // ws_main.addView(wwv);
// // wwv.getSettings().setAppCacheEnabled(false);
// // wwv.getSettings().setSupportZoom(false);
// // wwv.getSettings().setJavaScriptEnabled(true);
// // wwv.getSettings().setPluginState(WebSettings.PluginState.ON);
// // wwv.getSettings().setLayoutAlgorithm(
// // WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
// // wwv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
// // wwv.getSettings().setDomStorageEnabled(true);
// // wwv.getSettings().setSupportMultipleWindows(false);
// // wwv.getSettings().setBlockNetworkLoads(false);
// // wwv.getSettings().setBuiltInZoomControls(false);
// // wwv.setHorizontalScrollBarEnabled(false);
// // wwv.setVerticalScrollBarEnabled(true);
// //
// // WebChromeClient localaxx = new WebChromeClient();
// // wwv.setWebChromeClient(localaxx );
// // wwv.setWebViewClient(localaxy);
// }
//
// protected void titleSnapToScreen(int whichScreen) {
// // TODO Auto-generated method stub
//
// }
//
// // public WeiyunWebView getWwv() {
// // return wwv;
// // }
// //
// // public void setWwv(WeiyunWebView wwv) {
// // this.wwv = wwv;
// // }
//
// @Override
// protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4)
// {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onClick(View arg0) {
// // TODO Auto-generated method stub
//
// }
//
// }