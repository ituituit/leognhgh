package com.cheesemobile.util;

public class _Log {
	private static int Level = 4;

	public static void e(String str) {
		if (Level >= 3) {
			print(str);
		}
	}
	
	public static void e(String str,String content) {
		if (Level >= 3) {
			print(content);
		}
	}

	public static void i(String str,String content) {
		if (Level >= 4) {
			print(content);
		}
	}
	
	public static void i(String str) {
		if (Level >= 4) {
			print(str);
		}
	}

	public static void d(String str) {
		if (Level >= 5) {
			print(str);
		}
	}

	private static void print(String str) {
		if (Constants.debug) {
			System.out.println(str);
		}
	}
}
