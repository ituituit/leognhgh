package com.weiyun.news;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cheese.db.QueryDao;
import com.juetc.CommentsManager;
import com.juetc.TencentService;
import com.juetc.news.R;
import com.weiyun.domain.EditTextVerify;
import com.weiyun.domain.EditTextVerify.Pattern;
import com.weiyun.domain.channel.entity.ChannelItemBean;
import com.weiyun.util.SettingUtil;

public class ToolBarView extends RelativeLayout implements OnClickListener {
	private LayoutInflater mLayoutInflater;
	private Context context;
	private ChannelItemBean currentNews;

	public void setCurrentNews(ChannelItemBean currentNews) {
		this.currentNews = currentNews;
		flushBookmark();
	}

	public ToolBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context);
	}

	private void init(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		View toolbar = this.mLayoutInflater.inflate(R.layout.toolbar, this,
				true);
		ImageView back = (ImageView) toolbar.findViewById(R.id.back);
		ImageView comments = (ImageView) toolbar.findViewById(R.id.comments);
		ImageView write = (ImageView) toolbar.findViewById(R.id.write);
		bookmark = (ImageView) toolbar.findViewById(R.id.bookmark);
		ImageView share = (ImageView) toolbar.findViewById(R.id.share);
		back.setOnClickListener(this);
		comments.setOnClickListener(this);
		write.setOnClickListener(this);
		bookmark.setOnClickListener(this);
		share.setOnClickListener(this);
		back.setId(BACK_ID);
		comments.setId(COMMENTS_ID);
		write.setId(WRITE_ID);
		bookmark.setId(BOOKMARK_ID);
		share.setId(SHARE_ID);
	}

	public static final int BACK_ID = 0;
	public static final int COMMENTS_ID = 1;
	public static final int WRITE_ID = 2;
	public static final int BOOKMARK_ID = 3;
	public static final int SHARE_ID = 4;

	public void showWriteReview() {
		ReviewDialog d = new ReviewDialog(context);
		d.setDisplay();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case BACK_ID:
			break;
		case COMMENTS_ID:
			// final EditText inputServer = new EditText(context);
			// AlertDialog.Builder builder = new AlertDialog.Builder(context);
			// builder.setTitle("Server")
			// .setIcon(android.R.drawable.ic_dialog_info)
			// .setView(inputServer).setNegativeButton("Cancel", null);
			// builder.setPositiveButton("Ok",
			// new DialogInterface.OnClickListener() {
			//
			// public void onClick(DialogInterface dialog, int which) {
			// inputServer.getText().toString();
			// }
			// });
			// builder.show();
			break;
		case WRITE_ID:
			showWriteReview();
			break;
		case BOOKMARK_ID:
			// SettingUtil.toastDev(context);
			if (TencentService.getInstance(context).isLogin()) {
				toggleBookmark();
			} else {
				ToolBarView.startLogin(context);
			}
			break;
		case SHARE_ID:
			SettingUtil.toastDev(context);
			break;
		default:
			break;
		}
		if (listener != null) {
			listener.onClick(view);
		}
	}

	private void flushBookmark(){
		if(!bookmarkStored()){
			bookmark.setImageResource(R.drawable.bookmark);
		}else{
			bookmark.setImageResource(R.drawable.bookmark_highlight);
		}
	}
	
	private boolean  bookmarkStored(){
		QueryDao dao = new QueryDao(context);
		return (dao.queryWithUser(currentNews.getDocumentId()) != null);
	}
	
	private void toggleBookmark() {
		QueryDao dao = new QueryDao(context);
		if(bookmarkStored()){
			dao.delete(currentNews.getDocumentId());
			Toast.makeText(context, R.string.bookmark_del, Toast.LENGTH_LONG).show();
		}else{
			if(dao.insert(currentNews,true) == -1){
				dao.update(currentNews,true);
			}
			Toast.makeText(context, R.string.bookmark, Toast.LENGTH_LONG).show();
		}
		flushBookmark();
	}

	public static void startLogin(Context context) {
		Intent settingIntent = new Intent(context, LoginActivity.class);
		settingIntent.putExtra("mBack", true);
		context.startActivity(settingIntent);
	}

	private ToolBarClickListener listener;
	private ImageView bookmark;

	public void addToolBarClickListener(ToolBarClickListener listener) {
		this.listener = listener;
	}

	public interface ToolBarClickListener {
		public void onClick(View view);
		public void onReceiveUploadUrl(String str);
	}

	class ReviewDialog extends Dialog implements
			android.view.View.OnClickListener {
		private LinearLayout backButton;
		private LinearLayout sendButton;
		private Window window = null;
		private EditText commentContent;

		public ReviewDialog(Context context) {
			super(context, R.style.review_dialog);
		}

		public void setDisplay() {
			setContentView(R.layout.write_review);// 设置对话框的布局
			backButton = (LinearLayout) findViewById(R.id.back);
			sendButton = (LinearLayout) findViewById(R.id.send);
			commentContent = (EditText) findViewById(R.id.comment_content);
			backButton.setOnClickListener(this);
			sendButton.setOnClickListener(this);
			commentContent.requestFocus();
			setProperty();
			// setTitle("自定义对话框");// 设定对话框的标题
			show();// 显示对话框
		}

		// 要显示这个对话框，只要创建该类对象．然后调用该函数即可．
		public void setProperty() {
			window = getWindow();// 　　　得到对话框的窗口．
			WindowManager.LayoutParams wl = window.getAttributes();
			wl.x = 0;// 这两句设置了对话框的位置．0为中间
			wl.y = 0;
			// wl.alpha = 0.6f;// 这句设置了对话框的透明度
			wl.gravity = Gravity.CENTER;
			window.setAttributes(wl);
			window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		}

		@Override
		public void onClick(View v) {
			if (v.equals(backButton)) {
				dismiss();// 取消
			}
			if (v.equals(sendButton)) {
				if (!TencentService.getInstance(context).isLogin()) {
					ToolBarView.startLogin(context);
				} else {
					if (currentNews != null) {
						final EditTextVerify verifyComment = new EditTextVerify(
								commentContent, Pattern.patternEmpty, context
										.getResources().getString(
												R.string.review_min_length));
						if (verifyComment.verify()) {
							String uploadStr = CommentsManager.uploadStr(
									currentNews.getTitle(),
									currentNews.getId(),
									verifyComment.getEtText(),
									currentNews.getDocumentId(), context);
							listener.onReceiveUploadUrl(uploadStr);
						} else {
							commentContent.setText(verifyComment.getEtText());
							return;
						}
					}
					dismiss();
				}
			}
		}
	}
}