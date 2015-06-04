package com.weiyun.news;

import java.io.File;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.ImageUtil;
import com.cheesemobile.util.RequestVoNews;
import com.juetc.CommentsManager;
import com.juetc.news.R;
import com.weiyun.domain.DocBody;
import com.weiyun.domain.DocBodyInterface;
import com.weiyun.domain.DocUnit;
import com.weiyun.domain.channel.entity.ChannelItemBean;
import com.weiyun.domain.comment.entity.CommentsData;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.newhardware.parser.CommentsParser;
import com.weiyun.newhardware.parser.DocBodyParser;
import com.weiyun.util.ObjectSerializer;

interface SwitchViewInterface {
	public void switchTo();
}

public class DetailActivity extends DetailBaseActivity implements
		SwitchViewInterface {
	private String intruoduction;
	private String currentDocId;
	private String topicSports;
	private String src;

	private static WeiyunWebView wwv;

	private void initWeiyunWebView() {
		wwv = new WeiyunWebView(getApplicationContext());
		wwv.getSettings().setJavaScriptEnabled(true);
		wwv.getSettings().setAppCacheEnabled(false);
		wwv.getSettings().setSupportZoom(false);
		wwv.getSettings().setPluginState(WebSettings.PluginState.ON);
		wwv.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		wwv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		wwv.getSettings().setDomStorageEnabled(true);
		wwv.getSettings().setSupportMultipleWindows(false);
		wwv.getSettings().setBlockNetworkLoads(false);
		wwv.getSettings().setBuiltInZoomControls(false);
		wwv.setHorizontalScrollBarEnabled(false);
		wwv.setVerticalScrollBarEnabled(true);
		wwv.loadUrl("about:blank");
		detailView.getWorkspace().addView(wwv);
	}

	private DocUnit unit2;
	private ChannelItemBean channleItem;

	@Override
	public String getCommentsUrl() {
		return unit2.getBody().getCommentsUrl();
	}

	public static String getCacheImgPath(String url) {
		String md5 = ImageUtil.md5(url);
		String filePath = ImageUtil.SDCARD_CACHE_IMG_PATH + md5;
		return filePath;
	}

	public static void store(String url,DocUnit paramObject){
		String serialize = ObjectSerializer.serialize(paramObject);
		FileUtil.write(getCacheImgPath(url), serialize);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initWeiyunWebView();
		channleItem = (ChannelItemBean) getIntent().getSerializableExtra(
				"channel_obj");
		toolbar.setCurrentNews(channleItem);
		final String src = channleItem.getId();
		RequestVoNews reqVoDetail = new RequestVoNews(src, this,
				new DocBodyParser(new DataCallback<DocUnit>() {
					@Override
					public void processData(DocUnit paramObject,
							boolean paramBoolean) {
						setupDoc(paramObject);
						store(src,paramObject);
					}
				}));
		File file = new File(getCacheImgPath(src));
		if (file.exists() && file.length() > 0) {
			String readToString = FileUtil.readToString(getCacheImgPath(src),true);
			DocUnit paramObject =  (DocUnit) ObjectSerializer.deserialize(readToString);
			if(paramObject.getBody() != null){
				setupDoc(paramObject);
			}else{
				getDataFromServer(reqVoDetail);
			}
		} else {
			getDataFromServer(reqVoDetail);
		}
	}

	@Override
	public void onResetActivity() {
		super.onResetActivity();
	}

	private void setupDoc(DocUnit unit) {
		unit2 = unit;
		DocBody docBody = unit.getBody();
		wwv.clearCache(true);
		wwv.clearHistory();
		wwv.addJavascriptInterface(new DocBodyInterface(docBody), "datas");
		wwv.addJavascriptInterface(this, "controller");
		String url = CommentsManager.getUrl(docBody.getCommentsUrl(), 1, "");
		RequestVoNews reqVo = new RequestVoNews(url, this, new CommentsParser(
				new DataCallback<CommentsData>() {
					@Override
					public void processData(CommentsData paramObject,
							boolean paramBoolean) {
						setupListAdapter(paramObject);
						wwv.addJavascriptInterface(paramObject, "comments");
						wwv.loadUrl("file:///android_asset/detail_page.html");
					}
				}));
		addNetErrorListener(new NetErrorListener() {
			@Override
			public void onNullObjectReturned() {
			}
			
			@Override
			public void onNetFailedReceived(boolean hasNetWork) {
				wwv.loadUrl("file:///android_asset/detail_page.html");
			}
		});
		getDataFromServer(reqVo);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detail,
					container, false);
			return rootView;
		}
	}

	@Override
	public void switchTo() {
	}

}
