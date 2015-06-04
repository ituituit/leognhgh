package com.weiyun.domain;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.juetc.news.R;

public class EditTextVerify {
	private EditText et;
	private String pattern;
	private String errorMessage;

	public EditTextVerify(EditText et, Pattern inputType, String em) {
		this.et = et;
		this.et.setOnFocusChangeListener(ofcl);
		this.pattern = inputType.getPattern();
		this.errorMessage = em;
	}

	public String getEtText(){
		return  et.getText().toString().trim();
	}
	public boolean verify() {
		if (!getEtText().matches(pattern)) {
			Toast.makeText(et.getContext(), errorMessage, Toast.LENGTH_LONG)
					.show();
			return false;
		}
		return true;
	}

	public boolean verify(EditTextVerify etv) {
		if (!etv.getEtText().equals(this.getEtText())) {
			Toast.makeText(
					et.getContext(),
					et.getContext().getResources()
							.getString(R.string.input_not_same),
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	public enum Pattern {
		patternEmail {
			public String getPattern() {
				return patternEmails;
			}
		},
		patternPhoneNumber {
			public String getPattern() {
				return patternPhoneNumbers;
			}
		},
		patternZipCode {
			public String getPattern() {
				return patternZipCodes;
			}
		},
		patternVoid {
			public String getPattern() {
				return patternVoids;
			}
		},
		patternEmpty {
			public String getPattern() {
				return patternEmptys;
			}
		},
		patternLonger {
			public String getPattern() {
				return patternLongers;
			}
		},
		patternDefPassword {
			public String getPattern() {
				return patternDefPasswords;
			}
		};
		public String getPattern() {
			return "";
		}
	}

	OnFocusChangeListener ofcl = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
//			v.getClass();
//			if (!hasFocus) {
//				verify();
//			}
		}
	};
	
	static String patternEmails = "(^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)$)";
	static String patternPhoneNumbers = "(^[0-9])([0-9]{1,20})+([0-9]{1,10}$)";
	static String patternZipCodes = "(^[0-9]{3})([0-9]{4}$)";// [0-9-]{2,10}
	static String patternVoids = "^(.){0,100}$";
	static String patternEmptys = "^(.){1,100}$";
	static String patternLongers = "^(.){1,127}$";
	static String patternDefPasswords = "^(.){6,10}$";
}
