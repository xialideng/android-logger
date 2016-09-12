package com.github.thinwonton.android.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hugo on 2016/3/5.
 */
public class AndroidPrinter implements Printer {

	private Settings settings;

	@Override
	public Settings init(String tag) {
		settings = new Settings();
		settings.tag(tag);
		return settings;
	}

	@Override
	public Settings getSettings() {
		return this.settings;
	}

	@Override
	public void d(Object object, Object... args) {
		d(getSettings().getTag(), object, args);
	}

	@Override
	public void d(String tag, Object object, Object... args) {
		String message;
		if (object.getClass().isArray()) {
			message = Arrays.deepToString((Object[]) object);
		} else {
			message = object.toString();
		}

		log(LogLevel.DEBUG, tag, null, message, args);
	}

	@Override
	public void e(String message, Object... args) {
		e(getSettings().getTag(), message, args);
	}

	@Override
	public void e(Throwable throwable) {
		e(getSettings().getTag(), throwable);
	}

	@Override
	public void e(String tag, String message, Object... args) {
		log(LogLevel.ERROR, tag, null, message, args);
	}

	@Override
	public void e(String tag, Throwable throwable) {
		log(LogLevel.ERROR, tag, throwable, null);
	}

	@Override
	public void w(String message, Object... args) {
		w(getSettings().getTag(), message, args);
	}

	@Override
	public void w(String tag, String message, Object... args) {
		log(LogLevel.WARN, tag, null, message, args);
	}

	@Override
	public void i(String message, Object... args) {
		i(getSettings().getTag(), message, args);
	}

	@Override
	public void i(String tag, String message, Object... args) {
		log(LogLevel.INFO, tag, null, message, args);
	}

	@Override
	public void v(String message, Object... args) {
		v(getSettings().getTag(), message, args);
	}

	@Override
	public void v(String tag, String message, Object... args) {
		log(LogLevel.VERBOSE, tag, null, message, args);
	}

	@Override
	public void wtf(String message, Object... args) {
		wtf(getSettings().getTag(), message, args);
	}

	@Override
	public void wtf(String tag, String message, Object... args) {
		log(LogLevel.ASSERT, tag, null, message, args);
	}

	private synchronized void log(int type, String tag, Throwable throwable, String message, Object... args) {

		// 任何一个拦截器的日志级别小于当前打印级别才继续往下执行，否则结束任务
		boolean run = false;
		for (Interceptor interceptor : getSettings().getInterceptors()) {
			if (type >= interceptor.getLevel()) {
				run = true;
				break;
			}
		}

		if (run) {
			String msg = createMessage(message, args);
			log(type, tag, msg, throwable);
		}

	}

	private void log(int type, String tag, String message, Throwable throwable) {

		String[] callerInfos = getCallerInfos();
		String msg = createMessage(message, throwable);
		if (!Helper.isEmpty(msg)) {
			List<Interceptor> interceptors = getSettings().getInterceptors();
			for (Interceptor interceptor : interceptors) {
                if (type >= interceptor.getLevel()) {
                    interceptor.log(type, tag, callerInfos, msg);
                }
			}
		}

	}

	/**
	 * 获取调用者的信息
	 *
	 * @return string[0] 线程名 <br/>
	 *         string[1] 类名 <br/>
	 *         string[2] 行号 <br/>
	 *         string[3] 方法名 <br/>
	 */
	private String[] getCallerInfos() {

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement caller = findCallerStackTraceElement(stackTrace);

		// 类名
		String className = caller.getClassName();
		String[] classNameInfo = className.split("\\.");
		if (classNameInfo.length > 0) {
			className = classNameInfo[classNameInfo.length - 1];
		}
		if (className.contains("$")) {
			className = className.split("\\$")[0];
		}

		// 行号
		int lineNumber = caller.getLineNumber();
		if (lineNumber < 0) {
			lineNumber = 0;
		}

		// 方法名
		String methodName = caller.getMethodName();

		String[] callerInfos = new String[4];
		callerInfos[0] = Thread.currentThread().getName();
		callerInfos[1] = className;
		callerInfos[2] = String.valueOf(lineNumber);
		callerInfos[3] = methodName;

		return callerInfos;
	}

    /**
     * 找到最后那个前缀为入口类Logger的栈元素，它的下一个就是要找的栈记录
     * @param stackTrace
     * @return
     */
	private StackTraceElement findCallerStackTraceElement(StackTraceElement[] stackTrace) {
		int index = 0;
		int lastIndex = 0;
		for (StackTraceElement element : stackTrace) {
			if (element.getClassName() != null && element.getClassName().trim().startsWith(Logger.class.getName())) {
				lastIndex = index;
			}
			index++;
		}

		return stackTrace[lastIndex + 1];
	}

	private String createMessage(String message, Throwable throwable) {
		String finalContent;
		if (Helper.isEmpty(message) && Helper.isNull(throwable)) {
			finalContent = "";
		} else if (!Helper.isEmpty(message) && Helper.isNull(throwable)) {
			finalContent = message;
		} else if (Helper.isEmpty(message) && !Helper.isNull(throwable)) {
			finalContent = getStackTraceString(throwable);
		} else {
			finalContent = String.format("%s : %s", message, getStackTraceString(throwable));
		}
		return finalContent;
	}

    /**
     * 获取异常栈中的信息
     * @param tr
     * @return
     */
	private String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}

	private String createMessage(String message, Object[] args) {
		return args == null || args.length == 0 ? message : String.format(message, args);
	}
}
