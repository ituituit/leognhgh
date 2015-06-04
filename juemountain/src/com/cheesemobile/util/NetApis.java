package com.cheesemobile.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.message.BasicHeader;

import android.util.Log;

public class NetApis {
	public static BasicHeader[] headers = new BasicHeader[10];

	static {
		headers[0] = new BasicHeader("deviceID", "14010900");
		headers[1] = new BasicHeader("Udid", "");// 手机串号
		headers[2] = new BasicHeader("Os", "android");//
		headers[3] = new BasicHeader("Osversion", "");//
		headers[4] = new BasicHeader("Appversion", "");// 1.0
		headers[5] = new BasicHeader("Sourceid", "");//
		headers[6] = new BasicHeader("Ver", "");
		headers[7] = new BasicHeader("Userid", "");
		headers[8] = new BasicHeader("Usersession", "");
		headers[9] = new BasicHeader("Unique", "");
	}

	public static void download(String requestUrl,String destPath) {
		File file = new File(destPath);
		try {
			URL url = new URL(requestUrl);
			URLConnection conn = url.openConnection();
			int fileLength = conn.getContentLength();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.connect();
			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream(), 8192);
			ImageUtil.inputstreamtofile(bis, destPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Object postBy3G(RequestVo vo) {
		try {
			if (vo.requestDataString.length() == 0) {
				return null;
			}
			String postStr = AccessUtil.post3G(vo.requestUrl, vo.requestUrlid,
					vo.requestDataString);
			Object obj = vo.xmlParser.parseData(postStr);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getByApache(RequestVoNews vo) {
		try {
			String postStr = null;
			Log.i("", "postbyApache_string");
			if (vo.requestUrl.length() == 0) {
				return null;
			}
			postStr = AccessUtil.sendGet(vo.requestUrl, "");

			Object obj = vo.xmlParser.parseData(postStr);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
