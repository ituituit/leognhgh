package com.weiyun.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.juetc.news.R;
import com.weiyun.domain.comment.entity.Comment;
import com.weiyun.domain.comment.entity.CommentsData;
import com.weiyun.news.FloorView;

public class CommentsAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private Context context;
	private CommentsData dataSource;
	private Set<Integer> recommends = new HashSet<>();
	private CommentListener listener;

	public interface CommentListener {
		public void onRecommend(Comment comment);
	};

	public void setCommentLisener(CommentListener listener) {
		this.listener = listener;
	}

	public CommentsAdapter(Context context, CommentsData dataSource,
			ListView listView) {
		this.context = context;
		this.dataSource = dataSource;
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		List<Comment> newest = dataSource.getComments().getNewest();
		List<Comment> hotest = dataSource.getComments().getHottest();
		return newest.size() + hotest.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(
					R.layout.widget_comment_list_item, null);
			holder.nickname = (TextView) convertView
					.findViewById(R.id.comment_nickname);
			holder.from = (TextView) convertView.findViewById(R.id.from);
			holder.floorList = (FloorView) convertView
					.findViewById(R.id.floor_list);
			holder.commentContent = (TextView) convertView
					.findViewById(R.id.comment_content);
			holder.userIcon = (ImageView) convertView
					.findViewById(R.id.userIcon);
			holder.recommend = (TextView) convertView
					.findViewById(R.id.recommend);
			holder.recommend_icon = (ImageView) convertView
					.findViewById(R.id.recommend_icon);
			holder.recommend_moudle = (LinearLayout) convertView
					.findViewById(R.id.recommend_moudle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ArrayList<Comment> comment = getComments();
		Comment comment2 = comment.get(position);
		String nickname = comment2.getUname();
		String ipfrom = comment2.getIp_from();
		String content = comment2.getComment_contents();
		int recommendNumber = 0;
		if (comment2.getUptimes() == null
				|| comment2.getUptimes().length() == 0) {
		} else {
			recommendNumber = Integer.parseInt(comment2.getUptimes());
		}
		holder.nickname.setText(nickname);
		holder.from.setText(ipfrom);
		holder.commentContent.setText(content);
		if (recommends.contains(position)) {
			holder.recommend.setText(recommendNumber + 1 + "");
			holder.recommend_icon
					.setImageResource(R.drawable.icon_right_highlight);
		} else {
			holder.recommend.setText(recommendNumber + "");
			holder.recommend_icon.setImageResource(R.drawable.icon_right);
		}
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				notifyDataSetChanged();
				if (!recommends.contains(position)) {
					if (listener != null) {
						listener.onRecommend(getComments().get(position));
					}
				}
				recommends.add(position);
			}
		};
		holder.recommend_moudle.setOnClickListener(onClickListener);
		convertView.setId(position);
		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	private ArrayList<Comment> getComments() {
		List<Comment> hotest = dataSource.getComments().getHottest();
		List<Comment> newest = dataSource.getComments().getNewest();
		ArrayList<Comment> comment = new ArrayList<Comment>();
		comment.addAll(hotest);
		comment.addAll(newest);
		return comment;
	}

	private class ViewHolder {
		public TextView nickname;
		public TextView from;
		public TextView recommend;
		public FloorView floorList;
		public TextView commentContent;
		public ImageView userIcon;
		public ImageView recommend_icon;
		public LinearLayout recommend_moudle;
	}
	public void appendDataSource(CommentsData paramObject) {
//		List<Comment> newest = paramObject.getComments().getNewest();
		List<Comment> hotest = paramObject.getComments().getHottest();
		List<Comment> hotest2 = this.dataSource.getComments().getHottest();
//		Collections.copy(this.dataSource.getComments().getHottest(),hotest2);
		List<Comment> addComments = addComments(hotest,hotest2);
		this.dataSource.getComments().getHottest().clear();
		this.dataSource.getComments().getHottest().addAll(addComments);
		Collections.sort(this.dataSource.getComments().getHottest(), c);
	}

	public List<Comment> addComments(List<Comment> a,List<Comment> b){
		List<Integer> commDiff = new ArrayList<Integer>();
		for (Comment comment : a) {
			for (int i = 0; i < b.size(); i++) {
				Comment comment2 = b.get(i);
				if(comment2.getId().equals(comment.getId())){
					commDiff.add(i);
				}
			}
		}
		Collections.reverse(commDiff);
		for(int i = 0; i < b.size(); i++){
			if(!commDiff.contains(i)){
				a.add(b.get(i));
			}
		}
		return a;
	}
	
	Comparator<Comment> c = new Comparator<Comment>() {
		@Override
		public int compare(Comment arg0, Comment arg1) {
			int parseInt0 = Integer.parseInt(arg0.getId());
			int parseInt1 = Integer.parseInt(arg1.getId());
			if(parseInt0 > parseInt1){
				return -1;
			}else{
				return 1;
			}
		}
	};
}
