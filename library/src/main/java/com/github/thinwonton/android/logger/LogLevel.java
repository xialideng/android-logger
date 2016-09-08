package com.github.thinwonton.android.logger;

import android.util.Log;

/**
 * Created by hugo on 2016/3/5.
 */
public class LogLevel {
	public static final int VERBOSE = Log.VERBOSE;
	public static final int DEBUG = Log.DEBUG;
	public static final int INFO = Log.INFO;
	public static final int WARN = Log.WARN;
	public static final int ERROR = Log.ERROR;
	public static final int ASSERT = Log.ASSERT;
	public static final int FULL = VERBOSE - 1;
	public static final int NONE = ASSERT + 1;
}
