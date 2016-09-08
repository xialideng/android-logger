package com.github.thinwonton.android.logger;

public interface Printer {

	Settings init(String tag);

	Settings getSettings();

	void v(String message, Object... args);

	void v(String tag, String message, Object... args);

	void d(Object object, Object... args);

	void d(String tag, Object object, Object... args);

	void i(String message, Object... args);

	void i(String tag, String message, Object... args);

	void w(String message, Object... args);

	void w(String tag, String message, Object... args);

	void e(String message, Object... args);

	void e(String tag, String message, Object... args);

	void e(Throwable throwable);

	void e(String tag, Throwable throwable);

	void wtf(String message, Object... args);

	void wtf(String tag, String message, Object... args);
}
