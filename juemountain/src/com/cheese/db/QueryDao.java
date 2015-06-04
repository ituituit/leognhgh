package com.cheese.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.juetc.news.R;
import com.weiyun.domain.channel.entity.ChannelItemBean;
import com.weiyun.domain.channel.entity.ChannelStyle;
import com.weiyun.util.ObjectSerializer;
import com.weiyun.util.SharaedPreferences;

public class QueryDao {
	private Context context;
	private SQLiteDatabase localSQLiteDatabase;

	public QueryDao(Context context) {
		this.context = context;
		setLocalSQLiteDatabase(context);
	}

	private void setLocalSQLiteDatabase(Context context) {
		String path = String.valueOf(context.getFilesDir().getAbsolutePath());
		String fullpath = path + "/" + context.getString(R.string.db_name);
		localSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(new File(
				fullpath), null);
	}

	private void isOpen() {
		if (!localSQLiteDatabase.isOpen()) {
			localSQLiteDatabase = null;
			setLocalSQLiteDatabase(context);
		}
	}

	public List<ChannelItemBean> queryByUser() {
		isOpen();
		Cursor cursor = localSQLiteDatabase.rawQuery(
				"select * from DocBody where user=? order by u_Time desc",
				new String[] { String.valueOf(getUser()) });
		List<ChannelItemBean> list = new ArrayList<ChannelItemBean>();
		while (cursor.moveToNext()) {
			ChannelItemBean e = new ChannelItemBean();
			setBean(e, cursor);
			list.add(e);
		}
		cursor.close();
		localSQLiteDatabase.close();
		return list;
	}

	public List<ChannelItemBean> queryByChannel(String channel) {
		isOpen();
		Cursor cursor = localSQLiteDatabase.rawQuery(
				"select * from DocBody where channel=? order by u_Time desc",
				new String[] { String.valueOf(channel) });
		List<ChannelItemBean> list = new ArrayList<ChannelItemBean>();
		while (cursor.moveToNext()) {
			ChannelItemBean e = new ChannelItemBean();
			setBean(e, cursor);
			list.add(e);
		}
		cursor.close();
		localSQLiteDatabase.close();
		return list;
	}

	public ChannelItemBean query(String documentId) {
		isOpen();
		Cursor cursor = localSQLiteDatabase.rawQuery(
				"select * from DocBody where document_Id=?",
				new String[] { String.valueOf(documentId) });
		ChannelItemBean e = null;
		if (cursor.moveToFirst()) {
			e = new ChannelItemBean();
			setBean(e, cursor);
		}
		cursor.close();
		localSQLiteDatabase.close();
		return e;
	}

	public ChannelItemBean queryWithUser(String documentId) {
		isOpen();
		Cursor cursor = localSQLiteDatabase.rawQuery(
				"select * from DocBody where document_Id=? and user=?",
				new String[] { documentId, getUser() });
		ChannelItemBean e = null;
		if (cursor.moveToFirst()) {
			e = new ChannelItemBean();
			setBean(e, cursor);
		}
		cursor.close();
		localSQLiteDatabase.close();
		return e;
	}

	private String getUser() {
		return SharaedPreferences.getLoginInfo(context).get("openId");
	}

	private void setBean(ChannelItemBean e, Cursor cursor) {
		e.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
		e.setTitle(cursor.getString(cursor.getColumnIndex("title")));
		e.setSource(cursor.getString(cursor.getColumnIndex("source")));
		e.setChannel(cursor.getString(cursor.getColumnIndex("channel")));
		e.setUpdateTime(cursor.getString(cursor.getColumnIndex("u_Time")));
		e.setId(cursor.getString(cursor.getColumnIndex("id")));
		e.setDocumentId(cursor.getString(cursor.getColumnIndex("document_Id")));
		e.setType(cursor.getString(cursor.getColumnIndex("type")));
		e.setCommentUrl(cursor.getString(cursor.getColumnIndex("comments_Url")));
		// e.setComments(cursor.getString(cursor.getColumnIndex("comments")));
		e.setCommentsall(cursor.getString(cursor.getColumnIndex("commentsall")));
		String images = cursor.getString(cursor.getColumnIndex("images"));
		if (images != null) {
			ChannelStyle style = new ChannelStyle();
			style.setImages((ArrayList<String>) ObjectSerializer
					.deserialize(images));
			e.setStyle(style);
		}
		// e.setLink(cursor.getString(cursor.getColumnIndex("link")));
		// e.style(cursor.getString(cursor.getColumnIndex("style")));
		// e.sportsLiveExt(cursor.getString(cursor.getColumnIndex("sportsLiveExt")));
		System.out.println(e);
	}

	public int delete(String documentId) {
		isOpen();
		int cursor = localSQLiteDatabase.delete("DocBody", "document_Id=?",
				new String[] { String.valueOf(documentId) });
		localSQLiteDatabase.close();
		return cursor;
	}

	public long insert(ChannelItemBean currentNews, boolean withUser) {
		isOpen();
		ContentValues cv = new ContentValues();
		cv.put("thumbnail", currentNews.getThumbnail());
		cv.put("title", currentNews.getTitle());
		cv.put("source", currentNews.getSource());
		cv.put("u_Time", currentNews.getUpdateTime());
		cv.put("id", currentNews.getId());
		cv.put("document_Id", currentNews.getDocumentId());
		cv.put("comments_Url", currentNews.getCommentUrl());
		cv.put("type", currentNews.getType());
		if (withUser) {
			cv.put("user", getUser());
		}
		cv.put("commentsall", currentNews.getCommentsall());
		cv.put("channel", currentNews.getChannel());
		if (currentNews.getStyle() != null) {
			ArrayList<String> images = currentNews.getStyle().getImages();
			cv.put("images", ObjectSerializer.serialize(images));
		}
		// cv.put("channel", currentNews.getChannel());
		// cv.put("comments", currentNews.getComments());
		long insert = localSQLiteDatabase.insert("DocBody", null, cv);
		localSQLiteDatabase.close();
		return insert;
	}

	public long update(ChannelItemBean currentNews, boolean withUser) {
		isOpen();
		ContentValues cv = new ContentValues();
		cv.put("thumbnail", currentNews.getThumbnail());
		cv.put("title", currentNews.getTitle());
		cv.put("source", currentNews.getSource());
		cv.put("u_Time", currentNews.getUpdateTime());
		cv.put("id", currentNews.getId());
		cv.put("document_Id", currentNews.getDocumentId());
		cv.put("comments_Url", currentNews.getCommentUrl());
		cv.put("type", currentNews.getType());
		if (withUser) {
			cv.put("user", getUser());
		}
		cv.put("commentsall", currentNews.getCommentsall());
		cv.put("channel", currentNews.getChannel());
		if (currentNews.getStyle() != null) {
			ArrayList<String> images = currentNews.getStyle().getImages();
			cv.put("images", ObjectSerializer.serialize(images));
		}
		// cv.put("channel", currentNews.getChannel());
		// cv.put("comments", currentNews.getComments());
		long insert = localSQLiteDatabase.update("DocBody", cv,
				"document_Id=?", new String[] { currentNews.getDocumentId() });
		// long insert = localSQLiteDatabase.update("DocBody", null, cv);
		localSQLiteDatabase.close();
		return insert;
	}

	public static void initDataBaseDir(Context context, String databaseName) {
		String str1 = String.valueOf(context.getFilesDir().getAbsolutePath());
		String str2 = str1 + "/" + databaseName;
		File localObject = new File(str2);
		if (!localObject.exists()) {
			try {
				InputStream localInputStream = context.getAssets().open(
						databaseName);
				FileOutputStream localFileOutputStream = new FileOutputStream(
						localObject);
				byte[] bytes = new byte[1024];
				int i1;
				int i2 = 0;
				while ((i1 = localInputStream.read(bytes)) != -1) {
					localFileOutputStream.write(bytes, i2, i1);
				}
				Log.i(context.getClass().toString(),
						"copy database read success"
								+ localObject.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
}
