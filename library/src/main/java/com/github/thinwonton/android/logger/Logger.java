package com.github.thinwonton.android.logger;

/**
 * Created by hugo on 2016/3/5.
 */
public class Logger {

    public static boolean DEBUG = false;

	private static Printer printer;

	private static final String DEFAULT_TAG = Logger.class.getSimpleName();

	public static Settings init() {
		return init(DEFAULT_TAG);
	}

	public static Settings init(String tag) {
		printer = new AndroidPrinter();
		return printer.init(tag);
	}

	public static void d(String message, Object... args) {
		printer.d(message, args);
	}

	public static void d(Object object) {
		printer.d(object);
	}

	public static void d(String tag, String message, Object... args) {
		printer.d(tag, message, args);
	}

	public static void d(String tag, Object object) {
		printer.d(tag, object);
	}

	public static void e(String message, Object... args) {
		printer.e(message, args);
	}

	public static void e(String tag, String message, Object... args) {
		printer.e(tag, message, args);
	}

	public static void e(Throwable throwable) {
		printer.e(throwable);
	}

	public static void e(String tag, Throwable throwable) {
		printer.e(tag, throwable);
	}

	public static void i(String message, Object... args) {
		printer.i(message, args);
	}

	public static void i(String tag, String message, Object... args) {
		printer.i(tag, message, args);
	}

	public static void v(String message, Object... args) {
		printer.v(message, args);
	}

	public static void v(String tag, String message, Object... args) {
		printer.v(tag, message, args);
	}

	public static void w(String message, Object... args) {
		printer.w(message, args);
	}

	public static void w(String tag, String message, Object... args) {
		printer.w(tag, message, args);
	}

	public static void wtf(String message, Object... args) {
		printer.wtf(message, args);
	}

	public static void wtf(String tag, String message, Object... args) {
		printer.wtf(tag, message, args);
	}

}
