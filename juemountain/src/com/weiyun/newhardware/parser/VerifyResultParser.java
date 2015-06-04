package com.weiyun.newhardware.parser;

import com.weiyun.domain.VerifyResult;

public class VerifyResultParser extends BaseParser<VerifyResult> {
	private DataCallback<VerifyResult> callbackVerifyResult;

	public VerifyResultParser(DataCallback<VerifyResult> callbackVerifyResult) {
		super();
		this.callbackVerifyResult = callbackVerifyResult;
	}

	public VerifyResult parseData(String paramString) {
		VerifyResult vr = new VerifyResult();
		vr.setResult(false);
		if (paramString.indexOf("true") == 0 || paramString.equals("1")) {
			vr.setResult(true);
		} else {
			vr.setReason(paramString);
		}
		return vr;
	}

	@Override
	public DataCallback<VerifyResult> getCallback() {
		return callbackVerifyResult;
	}
}