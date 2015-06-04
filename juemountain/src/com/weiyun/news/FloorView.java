package com.weiyun.news;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juetc.news.R;
import com.weiyun.domain.comment.entity.Comment;
import com.weiyun.domain.comment.entity.ParentComment;

public class FloorView extends LinearLayout {
	private LayoutInflater mLayoutInflater;
	private Paint c;

	public FloorView(Context paramContext) {
		super(paramContext);
		init(paramContext);
	}

	public FloorView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init(paramContext);
	}

	private void init(Context paramContext) {
		setOrientation(1);
		// this.a = ((int) (3.0F *
		// paramContext.getResources().getDisplayMetrics().density));
		this.mLayoutInflater = ((LayoutInflater) getContext().getSystemService(
				"layout_inflater"));
		this.c = new Paint();
		this.c.setColor(-2763307);
		this.c.setStyle(Paint.Style.STROKE);
		this.c.setStrokeWidth(1.0F);
	}

	public final View a(ParentComment paramParentComment) {
		View floorItemView = this.mLayoutInflater.inflate(
				R.layout.widget_comment_list_item_floor_item, null);
		View layout_content = floorItemView.findViewById(R.id.layout_content);
		TextView loadMore = (TextView) floorItemView
				.findViewById(R.id.floor_loadmore_button);
		loadMore.setVisibility(VISIBLE);
		layout_content.setVisibility(GONE);
		TextView from = (TextView) floorItemView.findViewById(R.id.from);
		TextView nickName = (TextView) floorItemView
				.findViewById(R.id.nickname);
		TextView dot = (TextView) floorItemView.findViewById(R.id.dot);
		TextView floorNumber = (TextView) floorItemView
				.findViewById(R.id.floor_number);
		TextView floorContent = (TextView) floorItemView
				.findViewById(R.id.content);
		String fromStr = paramParentComment.getIp_from();
		StringBuilder localStringBuilder = new StringBuilder();
		if (TextUtils.isEmpty(fromStr)) {
			localStringBuilder.append(getContext().getString(
					R.string.comment_default_from));
			from.setText(localStringBuilder.toString());
			// Strign str2 = aae.a(getContext(), paramParentComment);
			// if (!TextUtils.isEmpty(str2))
			// break label237;
			nickName.setText("");
			nickName.setVisibility(INVISIBLE);
			dot.setVisibility(GONE);
			floorNumber
					.setText(String.valueOf(paramParentComment.getFloorNum()));
			String commentContents = paramParentComment.getComment_contents();
			if (!TextUtils.isEmpty(commentContents)) {
				commentContents = commentContents.replace("<br>", "");
				floorContent.setText(commentContents.replace("&quot;", "\""));
				localStringBuilder.append("用户");
				// nickName.setText(str2);
				nickName.setVisibility(0);
				dot.setVisibility(0);
			}
		}
		return floorItemView;
	}

	public final void a() {
		int i = getChildCount();
		for (int j = 0; j < i; j++) {
			View localView = getChildAt(j);
			LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
					-1, -2);
			localLayoutParams.gravity = 49;
			localView.setLayoutParams(localLayoutParams);
		}
	}

	public final void a(Comment paramComment) {
		ArrayList localArrayList = paramComment.getParent();
		if ((localArrayList == null) || (localArrayList.size() == 0)) {
			setVisibility(8);
			return;
		}
		removeAllViews();
		setVisibility(0);
		int i = localArrayList.size();
		int j = 0;
		if (i >= 3) {
			boolean bool = paramComment.isExpansion();
			j = 0;
			if (!bool)
				;
		} else {
			while (j < localArrayList.size()) {
				ParentComment localParentComment = (ParentComment) localArrayList
						.get(j);
				localParentComment.setFloorNum(j + 1);
				addView(a(localParentComment));
				j++;
			}
		}
		((ParentComment) localArrayList.get(0)).setFloorNum(1);
		addView(a((ParentComment) localArrayList.get(0)));
		int k = -2 + localArrayList.size();
		View localView1 = this.mLayoutInflater.inflate(2130903296, null);
		View localView2 = localView1.findViewById(2131231155);
		TextView localTextView = (TextView) localView1.findViewById(2131231627);
		localTextView.setText("展开全部" + k + "条");
		localView2.setVisibility(8);
		localTextView.setVisibility(0);
		// localView1.setOnClickListener(new axz(this, paramComment,
		// localArrayList));
		addView(localView1);
		((ParentComment) localArrayList.get(-1 + localArrayList.size()))
				.setFloorNum(localArrayList.size());
		addView(a((ParentComment) localArrayList
				.get(-1 + localArrayList.size())));
		a();
	}

	protected void dispatchDraw(Canvas paramCanvas) {
		int i = getChildCount();
		if (i > 0)
			for (int j = i - 1; j >= 0; j--) {
				View localView = getChildAt(j);
				paramCanvas.drawRect(new Rect(1 + localView.getLeft(),
						1 + localView.getLeft(), -1 + localView.getRight(), -1
								+ localView.getBottom()), this.c);
			}
		super.dispatchDraw(paramCanvas);
	}

	protected void onMeasure(int paramInt1, int paramInt2) {
		if (getChildCount() <= 0) {
			setMeasuredDimension(0, 0);
			return;
		}
		super.onMeasure(paramInt1, paramInt2);
	}
}