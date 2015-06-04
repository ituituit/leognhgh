package com.juetc;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.cheesemobile.util.Constants;
import com.cheesemobile.util.ImageUtil;
import com.cheesemobile.util.RequestVoDownLoad;
import com.cheesemobile.util.RequestVoNews;
import com.weiyun.domain.Version;
import com.weiyun.newhardware.parser.BaseParser.DataCallback;
import com.weiyun.newhardware.parser.VersionParser;
import com.weiyun.news.BaseService;

public class VersionManager extends BaseService {

	private Context ctx;
	private String curVersion;
	private int curVersionCode;
	public static final String ACTION_SERVICE = "com.weiyun.VersionManager";

	public static void startService(Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_SERVICE);
		context.startService(intent);
	}

	public static void stopService(Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_SERVICE);
		context.stopService(intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		ctx = this.getApplicationContext();
		getCurVersion(ctx);
		String url = Constants.getVersion;
		RequestVoNews reqVo = new RequestVoNews(url, this, new VersionParser(
				new DataCallback<Version>() {
					@Override
					public void processData(Version paramObject,
							boolean paramBoolean) {
						if (paramObject.getVersion() > curVersionCode) {
							getAPK(paramObject);
						}
					}
				}));
		getDataFromServer(reqVo);
	}

	private void getAPK(Version paramObject) {
		String requestUrl = paramObject.getApkPath();
		String FileName = requestUrl.substring(requestUrl.lastIndexOf("/"));
		final String destPath = ImageUtil.getCacheImgPath().concat(FileName);
		File file = new File(destPath);
		if (file.exists()) {
			String md5 = ImageUtil.md5(file);
			if (md5 == paramObject.getMd5()) {
				update(ctx, destPath);
				stopService(ctx);
				return;
			}
		}
		RequestVoDownLoad reqVo = new RequestVoDownLoad(requestUrl,
				getApplicationContext(), new DataCallback<String>() {
					@Override
					public void processData(String paramObject,
							boolean paramBoolean) {
						update(ctx, destPath);
						stopService(ctx);
					}
				}, destPath);
		downloadFromServer(reqVo);
	}

	private void getCurVersion(Context ctx) {
		try {
			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			curVersion = pInfo.versionName;
			curVersionCode = pInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.e("update", e.getMessage());
			curVersion = "1.0";
			curVersionCode = 1;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public static void update(Context context, String destPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(new File(destPath)),
				"application/vnd.android.package-archive");
		context.getApplicationContext().startActivity(intent);
	}
	//
	// public void downloadPackage() {
	//
	// new Thread() {
	// @Override
	// public void run() {
	// try {
	// URL url = new URL(UPDATE_DOWNURL);
	//
	// HttpURLConnection conn = (HttpURLConnection) url
	// .openConnection();
	// conn.connect();
	// int length = conn.getContentLength();
	// InputStream is = conn.getInputStream();
	//
	// File ApkFile = new File(savefolder, UPDATE_SAVENAME);
	//
	// if (ApkFile.exists()) {
	//
	// ApkFile.delete();
	// }
	//
	// FileOutputStream fos = new FileOutputStream(ApkFile);
	//
	// int count = 0;
	// byte buf[] = new byte[512];
	//
	// do {
	//
	// int numread = is.read(buf);
	// count += numread;
	// progress = (int) (((float) count / length) * 100);
	//
	// updateHandler.sendMessage(updateHandler
	// .obtainMessage(UPDATE_DOWNLOADING));
	// if (numread <= 0) {
	//
	// updateHandler
	// .sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED);
	// break;
	// }
	// fos.write(buf, 0, numread);
	// } while (!canceled);
	// if (canceled) {
	// updateHandler
	// .sendEmptyMessage(UPDATE_DOWNLOAD_CANCELED);
	// }
	// fos.close();
	// is.close();
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	//
	// updateHandler.sendMessage(updateHandler.obtainMessage(
	// UPDATE_DOWNLOAD_ERROR, e.getMessage()));
	// } catch (IOException e) {
	// e.printStackTrace();
	//
	// updateHandler.sendMessage(updateHandler.obtainMessage(
	// UPDATE_DOWNLOAD_ERROR, e.getMessage()));
	// }
	//
	// }
	// }.start();
	// }

}
