package com.weiyun.util;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.text.InputType;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SettingUtil {
	/**
	 * 获得当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
	 */

	public static void verify(InputType type) {
		if (type.equals(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)) {

		}
		if (type.equals(InputType.TYPE_TEXT_VARIATION_PASSWORD)) {

		}
	}

	public static void toastDev(Context context) {
		Toast.makeText(context, "该功能正在维护中。。。", Toast.LENGTH_LONG).show();
	}

	public static int getScreenMode(Context context) {
		int screenMode = 0;
		try {
			screenMode = Settings.System.getInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (Exception localException) {
		}
		return screenMode;
	}

	/**
	 * 获得当前屏幕亮度值 0--255
	 */
	public static int getScreenBrightness(Context context) {
		int screenBrightness = 255;
		try {
			screenBrightness = Settings.System.getInt(
					context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception localException) {

		}
		return screenBrightness;
	}

	/**
	 * 设置当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
	 */
	public static void setScreenMode(int paramInt, Context context) {
		try {
			Settings.System.putInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	/**
	 * 设置当前屏幕亮度值 0--255
	 */
	public static void saveScreenBrightness(float paramInt, Context context) {
		try {
			Settings.System.putFloat(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS, paramInt);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	/**
	 * 保存当前的屏幕亮度值，并使之生效
	 */
	public static void setScreenBrightness(float paramInt, Activity context) {
		Window localWindow = context.getWindow();
		WindowManager.LayoutParams localLayoutParams = localWindow
				.getAttributes();
		float f = paramInt / 255.0F;
		localLayoutParams.screenBrightness = f;
		localWindow.setAttributes(localLayoutParams);
	}

}
