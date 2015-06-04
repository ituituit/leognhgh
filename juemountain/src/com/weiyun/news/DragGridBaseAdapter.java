package com.weiyun.news;

public interface DragGridBaseAdapter {
	public void reorderItems(int oldPosition, int newPosition);
	public void setHideItem(int hidePosition);
	public void insert(String str, int position);
	public int indexObj(String str);
	public void remove(int i);
	public void modify(String str);
}
