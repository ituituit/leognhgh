package com.weiyun.news;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.juetc.news.R;

public class WebPost extends Activity {
	public WebView webview;
	private boolean forceDefaultBrowser = false;

	@Override
	public void onBackPressed() {
		if (webview.canGoBack()) {
			webview.goBack();
		} else {
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.auth_webview);

		webview = (WebView) findViewById(R.id.webView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i("", "shouldOverrideUrlLoading:" + url);

				String[] matches = { "smartebook01", "smartebook02",
						"shimaumatop" };
				for (int i = 0; i < matches.length; i++) {
					int index = url.indexOf(matches[i]);
					if (index != -1) {
						startActivity(new Intent(Intent.ACTION_VIEW, Uri
								.parse(url.substring(index))));
						finish(); 
						return true;
					}
				}

				if (forceDefaultBrowser) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					finish();
				} else {
					view.loadUrl(url);
				}
				return true;
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
			
			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				Log.i("", "onReceivedSslError:" + error);
				handler.proceed();
			}

			@Override
			public void onPageStarted(WebView view, String url,
					android.graphics.Bitmap favicon) {
				Log.i("", "onPageStarted:" + url);
			}

			@Override
			public void onReceivedHttpAuthRequest(WebView view,
					HttpAuthHandler handler, String host, String realm) {
				Log.i("", "onReceivedHttpAuthRequest:" + host);
				super.onReceivedHttpAuthRequest(view, handler, host, realm);
			}
		});
		String url = getIntent().getStringExtra("url");
		String queryStr = getIntent().getStringExtra("bodyStr");
		String res = "";
		forceDefaultBrowser = getIntent().getBooleanExtra(
				"forceDefaultBrowser", false);
		Log.i("", "webPost_forceBrowser" + forceDefaultBrowser);
		webview.postUrl(url, queryStr.getBytes());
	}

}
