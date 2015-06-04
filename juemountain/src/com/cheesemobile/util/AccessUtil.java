package com.cheesemobile.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Node;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AccessUtil {
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	public static Node getAllNodes(Node node, String indexContent) {
		for (Node n : node.childNodes()) {
			if (n.childNodes().size() != 0) {
				Node str = getAllNodes(n, indexContent);
				if (str != null) {
					return str;
				}
			}
			if (n.toString().indexOf(indexContent) != -1) {
				return n;
			}
		}
		return null;
	}

	// public static Object get(RequestVo vo){
	// DefaultHttpClient client = new DefaultHttpClient();
	// HttpGet get = new
	// HttpGet(vo.context.getString(R.string.app_host).concat(vo.context.getString(vo.requestUrl)));
	// get.setHeaders(headers);
	// Object obj = null;
	// try {
	// HttpResponse response = client.execute(get);
	// if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
	// String result = EntityUtils.toString(response.getEntity(),"UTF-8");
	// Log.e(NetUtil.class.getSimpleName(), result);
	// try {
	// obj = vo.jsonParser.parseJSON(result);
	// } catch (JSONException e) {
	// Log.e(NetUtil.class.getSimpleName(), e.getLocalizedMessage(),e);
	// }
	// return obj;
	// }
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	private static String ENCODE_CHARSET_UTF8 = "UTF-8";

	// public static String sendGet(String url, String param) {
	// String urlNameString = url;
	// if(param.length() != 0){
	// urlNameString = urlNameString + "?" + param;
	// }
	// url = urlNameString;
	// MultiThreadedHttpConnectionManager connectionManager = new
	// MultiThreadedHttpConnectionManager();
	// connectionManager.getParams().setConnectionTimeout(10000);
	// HttpClient httpClient = new HttpClient(connectionManager);
	// GetMethod postMethod = new GetMethod(url);
	// // httpClient.getState ().setProxyCredentials (AuthScope.ANY, new
	// // UsernamePasswordCredentials ("domain_user","domain_user"));
	// try {
	// List<Header> headers = new ArrayList<Header>();
	// String type = "application/x-www-form-urlencoded";
	// headers.add(new Header("Content-Type", type + ";charset="
	// + ENCODE_CHARSET_UTF8));
	// httpClient.getHostConfiguration().getParams()
	// .setParameter("http.default-headers", headers);
	// postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
	// new DefaultHttpMethodRetryHandler(1, false));
	// postMethod.getParams().setParameter(
	// HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODE_CHARSET_UTF8);
	// postMethod.getParams().setParameter(
	// HttpMethodParams.HTTP_URI_CHARSET, ENCODE_CHARSET_UTF8);
	// postMethod.getParams().setParameter(
	// HttpMethodParams.HTTP_ELEMENT_CHARSET, ENCODE_CHARSET_UTF8);
	// // postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
	// // msTimeout);
	// Integer status = httpClient.executeMethod(postMethod);
	// return postMethod.getResponseBodyAsString();
	// } catch (HttpException e) {
	// } catch (IOException e) {
	// } finally {
	// postMethod.releaseConnection();
	// }
	// return "";
	// }

	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			if (param.length() != 0) {
				urlNameString = urlNameString + "?" + param;
			}
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Content-Type", "charset=UTF-8");
			// 建立实际的连接
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				// System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			_Log.i("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String post3G(String ip, int port, String param) {
		// String strServer = url;
		Socket socket;
		try {
			socket = new Socket(ip, port);
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), "UTF-8"));
			wr.write(param);
			wr.flush();
			InputStreamReader inputStreamReader = new InputStreamReader(
					socket.getInputStream());
			BufferedReader rd = new BufferedReader(inputStreamReader);
			char cbuf[] = new char[1];
			int len = 0;
			StringBuilder sb = new StringBuilder();
			while (true) {
				rd.read(cbuf);
				sb.append(cbuf);
				if (cbuf[0] == '\0') {
					break;
				}
			}
			wr.close();
			rd.close();
			return sb.toString();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			// conn.setRequestProperty("user-agent",
			// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			_Log.i("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
