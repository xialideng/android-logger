package com.github.thinwonton.android.logger;

import android.util.Log;

/**
 * Created by hugo on 2016/3/7.
 */
public class ConsoleInterceptor implements Interceptor {

	private static final int CHUNK_SIZE = 4000;

	private int level;

	public ConsoleInterceptor(int level) {
		this.level = level;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public void log(int level, String tag, String[] caller, String message) {
		String callerInfos = makeCallerInfos(caller);
		String printStack = makePrintStack(callerInfos, message);
		logChunk(level, tag, printStack);
	}

	private void logChunk(int type, String tag, String chunk) {
		int subNum = chunk.length() / CHUNK_SIZE;
		if (subNum > 0) {
			int index = 0;
			for (int i = 0; i < subNum; i++) {
				int lastIndex = index + CHUNK_SIZE;
				String sub = chunk.substring(index, lastIndex);
				print(type, tag, sub);
				index = lastIndex;
			}
			print(type, tag, chunk.substring(index, chunk.length()));
		} else {
			print(type, tag, chunk);
		}
	}

    protected String makePrintStack(String caller, String message) {
        return Helper.makePrintStack(caller, message);
	}

	protected String makeCallerInfos(String[] caller) {
		return Helper.makeCallerInfos(caller);
	}

	private void print(int type, String tag, String message) {
		switch (type) {
			case LogLevel.ERROR :
				Log.e(tag, message);
				break;
			case LogLevel.INFO :
				Log.i(tag, message);
				break;
			case LogLevel.VERBOSE :
				Log.v(tag, message);
				break;
			case LogLevel.WARN :
				Log.w(tag, message);
				break;
			case LogLevel.ASSERT :
				Log.wtf(tag, message);
				break;
			case LogLevel.DEBUG :
				Log.d(tag, message);
			default :
				break;
		}
	}
}
