package com.cheesemobile.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ImageUtil {
	public static final String SDCARD_CACHE_IMG_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ Constants.TumbinalInSD;

	/**
	 * 锟斤拷锟斤拷图片锟斤拷SD锟斤拷
	 * 
	 * @param imagePath
	 * @param buffer
	 * @throws IOException
	 */
	public static void saveImage(String imagePath, byte[] buffer)
			throws IOException {
		File f = new File(imagePath);
		if (f.exists()) {
			return;
		} else {
			File parentFile = f.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(imagePath);
			fos.write(buffer);
			fos.flush();
			fos.close();
		}
	}

	/**
	 * 锟斤拷SD锟斤拷锟斤拷锟斤拷图片
	 * 
	 * @param imagePath
	 * @return
	 */
	public enum PreviewKeys {
		thumbinal {
			public int size() {
				return 192 * 128;
			}
		},
		preview {
			public int size() {
				return 480 * 320;
			}
		},
		fullscreen {
			public int size() {
				return 960 * 640;
			}
		};

		public int size() {
			return 0;
		}
	}

	public static Bitmap getImageFromLocal(String imagePath, PreviewKeys key) {
		File file = new File(imagePath);
		if (!file.exists() || file.length() == 0) {
			return null;
		}
		int size = key.size();
		HashMap<String, Object> l = ImageUtil
				.coverToPreviewImg(imagePath, size);
		if (l == null) {// image is broken
			return null;
		}
		Bitmap bMap = (Bitmap) l.get("previewBmp");
		return bMap;

	}

	public static Bitmap getImageFromLocal(String imagePath, boolean preview) {
		File file = new File(imagePath);
		if (!file.exists() || file.length() == 0) {
			return null;
		}
		String parameter = "thumbinal";
		if (preview) {

		} else {
			parameter = "preview";
		}
		// Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		int size = 0;
		PreviewKeys[] keys = PreviewKeys.values();
		for (int i = 0; i < keys.length; i++) {
			PreviewKeys key = keys[i];
			if (key.name().equals(parameter)) {
				size = key.size();
				break;
			}
		}
		HashMap<String, Object> l = ImageUtil
				.coverToPreviewImg(imagePath, size);
		if (l == null) {// image is broken
			return null;
		}
		Bitmap bMap = (Bitmap) l.get("previewBmp");
		// file.setLastModified(System.currentTimeMillis());
		return bMap;
		// }
		// return null;
	}

	public static Bitmap getImageFromLocal(String imagePath) {
		return getImageFromLocal(imagePath, true);
	}

	/**
	 * Bitmap转锟斤拷锟斤拷Byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bas);
		return bas.toByteArray();
	}

	public static boolean isImage(String path) {
		int last = path.lastIndexOf(".");
		String suffix = "";
		if (last > 0) {
			suffix = path.substring(last).trim().toLowerCase();
		}
		String[] imageType = new String[] { ".jpg", ".bmp", ".gif", ".png" };
		for (int i = 0; i < imageType.length; i++) {
			if (suffix.equals(imageType[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 锟接憋拷锟截伙拷锟竭凤拷锟斤拷思锟斤拷锟酵计�
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Bitmap loadImage(final String imagePath, final String imgUrl,
			final ImageCallback callback) {
		if (imgUrl.equals("'")) { // || !isImage(imgUrl)) {
			Log.i("loadImage", "url is empty");
			return null;
		}

		Bitmap bitmap = getImageFromLocal(imagePath);
		if (bitmap != null) {
			return bitmap;
		} else {
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					Bitmap imageFromLocal = getImageFromLocal(imagePath);
					callback.loadImage(imageFromLocal, imagePath);
				}
			};

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						URL url = new URL(imgUrl);
						Log.i("加载：", imgUrl);
						URLConnection conn = url.openConnection();
						conn.setConnectTimeout(5000);
						conn.setReadTimeout(5000);
						conn.connect();
						BufferedInputStream bis = new BufferedInputStream(
								conn.getInputStream(), 8192);
						// Bitmap bitmap = BitmapFactory.decodeStream(bis);
						inputstreamtofile(bis, imagePath);
						// saveImage(imagePath, bitmap2Bytes(bitmap));
						// bitmap.recycle();
						handler.sendEmptyMessage(0);
					} catch (IOException e) {
						Log.e(ImageUtil.class.getName(), "imageUtil failed:"
								+ e);
					}
				}
			};
			ThreadPoolManager.getInstance().addTask(runnable);
		}
		return null;
	}

	public static Bitmap loadImageFullScreen(final String imagePath,
			final String imgUrl, final ImageCallback callback) {
		if (imgUrl.equals("'")) { // || !isImage(imgUrl)) {
			Log.i("loadImage", "url is empty");
			return null;
		}

		Bitmap bitmap = getImageFromLocal(imagePath, false);
		if (bitmap != null) {
			return bitmap;
		} else {
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					Bitmap imageFromLocal = getImageFromLocal(imagePath, false);
					callback.loadImage(imageFromLocal, imagePath);
				}
			};

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						URL url = new URL(imgUrl);
						Log.i("加载：", imgUrl);
						URLConnection conn = url.openConnection();
						conn.setConnectTimeout(5000);
						conn.setReadTimeout(5000);
						conn.connect();
						BufferedInputStream bis = new BufferedInputStream(
								conn.getInputStream(), 8192);
						// Bitmap bitmap = BitmapFactory.decodeStream(bis);
						inputstreamtofile(bis, imagePath);
						// saveImage(imagePath, bitmap2Bytes(bitmap));
						// bitmap.recycle();
						handler.sendEmptyMessage(0);
					} catch (IOException e) {
						Log.e(ImageUtil.class.getName(), "imageUtil failed:"
								+ e);
					}
				}
			};
			ThreadPoolManager.getInstance().addTask(runnable);
		}
		return null;
	}

	// 锟斤拷锟斤拷图片锟芥到sd锟斤拷锟斤拷路锟斤拷
	public static String getCacheImgPath() {
		return SDCARD_CACHE_IMG_PATH;
	}

	public static String md5(String paramString) {
		String returnStr;
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramString.getBytes());
			returnStr = byteToHexString(localMessageDigest.digest());
			return returnStr;
		} catch (Exception e) {
			return paramString;
		}
	}

	public static String md5(File file) {
		String returnStr;
		try {
			FileInputStream in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(byteBuffer);
			returnStr = byteToHexString(localMessageDigest.digest());
			return returnStr;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 锟斤拷指锟斤拷byte锟斤拷锟斤拷转锟斤拷锟斤拷16锟斤拷锟斤拷锟街凤拷
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	/**
	 * 
	 * @author Mathew
	 * 
	 */

	public static HashMap<String, Object> getBmp2ThumbAndSize(
			BufferedInputStream iStream, String tmpPath) {
		File ifile = new File(tmpPath);
		ImageUtil.inputstreamtofile(iStream, ifile);
		try {
			iStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashMap<String, Object> map = ImageUtil.coverToPreviewImg(tmpPath,
				256 * 128);
		if (ifile.isFile()) {
			ifile.delete();
		}
		return map;
	}

	public static void inputstreamtofile(BufferedInputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void inputstreamtofile(BufferedInputStream ins, String str) {
		try {
			OutputStream out = new FileOutputStream(new File(str));
			// FileOutputStream fos = new FileOutputStream(imagePath);
			// ByteArrayOutputStream out = new ByteArrayOutputStream();
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			// saveImage(str, out.toByteArray());
			out.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<String, Object> coverToPreviewImg(String tmpPath,
			int sizeInPixel) {
		Bitmap bMap;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(tmpPath, opts);
		HashMap<String, Integer> sizeMap = new HashMap<String, Integer>();
		sizeMap.put("width", opts.outWidth);
		sizeMap.put("height", opts.outHeight);
		opts.inSampleSize = ImageUtil.computeSampleSize(opts, -1, sizeInPixel);
		opts.inJustDecodeBounds = false;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		bMap = BitmapFactory.decodeFile(tmpPath, opts);
		_Log.e("", "sourceSize:" + tmpPath + " " + opts.outWidth + " "
				+ opts.outHeight);
		if (opts.outWidth == -1) {
			return null;
		}
		bMap = judgeRotate(tmpPath, bMap);
		int w, h;
		w = sizeMap.get("width");
		h = sizeMap.get("height");
		if ((bMap.getWidth() < bMap.getHeight() && w > h)
				|| (bMap.getWidth() > bMap.getHeight() && w < h)) {
			sizeMap.clear();
			sizeMap.put("width", h);
			sizeMap.put("height", w);
		}
		_Log.i("", "after:" + bMap.getWidth() + "," + bMap.getHeight() + ","
				+ sizeMap.get("width") + " " + sizeMap.get("height"));
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("previewBmp", bMap);
		hm.put("sourceSizeMap", sizeMap);
		return hm;
		/*----------------------------------
		 * HashMap<String, Object> l = ImageUtil.coverToPreviewImg(tmpPath,128*128);
		 * Bitmap bMap = (Bitmap)l.get("previewBmp");
		 * sizeMaps.add((HashMap<String, Integer>)l.get("sourceSizeMap"));
		 * ---------------------------------
		 */
	}

	public static Bitmap judgeRotate(String myNewPath, Bitmap bmp) {
		try {
			ExifInterface tempNewExif = new ExifInterface(myNewPath);
			int tempNewOrientation = Integer.parseInt(tempNewExif
					.getAttribute(ExifInterface.TAG_ORIENTATION));
			switch (tempNewOrientation) {
			case ExifInterface.ORIENTATION_NORMAL:
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				bmp = rotate(bmp, 90);
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				bmp = rotate(bmp, 180);
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				bmp = rotate(bmp, 270);
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmp;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {

		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static Bitmap rotate(Bitmap b, int degrees) {

		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				ex.printStackTrace();
				return b;
			}
		}
		return b;
	}

	public interface ImageCallback {
		public void loadImage(Bitmap bitmap, String imagePath);
	}
}
