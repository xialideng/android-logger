package com.github.thinwonton.android.logger;

/**
 * Created by hugo on 2016/3/5.
 */
public interface Interceptor {
	/**
	 * 获取当前日志拦截器的打印级别
	 * 
	 * @return
	 */
	int getLevel();

	/**
	 *
	 * @param level
	 *            日志打印级别
	 * @param tag
	 *            标签
	 * @param caller
	 *            string[0] 线程名 <br/>
	 *            string[1] 类名 <br/>
	 *            string[2] 行号 <br/>
	 *            string[3] 方法名 <br/>
	 * @param message
	 *            消息
	 */
	void log(int level, String tag, String[] caller, String message);
}
