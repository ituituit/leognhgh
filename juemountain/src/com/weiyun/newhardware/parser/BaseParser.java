package com.weiyun.newhardware.parser;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseParser<T> {
	//in baseActivity uses a handler send to activity,this is the callback
	public abstract interface DataCallback<T> {
		public abstract void processData(T paramObject, boolean paramBoolean);
	}

	public abstract T parseData(String bodyFromServer) throws JSONException;

	public abstract DataCallback<T> getCallback();//
	/**
	 * 
	 * @param res
	 * @throws JSONException
	 */
//	public String checkResponse(String paramString) throws JSONException {
//		if (paramString == null) {
//			return null;
//		} else {
//			JSONObject jsonObject = new JSONObject(paramString);
//			String result = jsonObject.getString("response");
//			if (result != null && !result.equals("error")) {
//				return result;
//			} else {
//				return null;
//			}
//
//		}
//	}
}
