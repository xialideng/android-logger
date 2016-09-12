package com.github.thinwonton.android.logger;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;

/**
 * Created by hugo on 2016/9/12.
 */
public class SimpleFileCreator implements FileCreator {

	private final String SUFFIX = ".log";

	private Context applicationContext;

	public SimpleFileCreator(Context context) {
		this.applicationContext = context.getApplicationContext();
	}

	@Override
	public String getFileName() {
		return DateFormat.format("yyyy-MM-dd", System.currentTimeMillis()) + SUFFIX;
	}

	@Override
	public String getDirPath() {
		if (checkSDcard() && hasExternalStoragePermission(getContext())) {
			return getExternalStorageDir();
		} else {
			return getInternalStorageDir();
		}
	}

	protected Context getContext() {
		return this.applicationContext;
	}

	protected String getInternalStorageDir() {
		return "/data/data/" + getContext().getPackageName() + "/file/log/";
	}

	protected String getExternalStorageDir() {
		return Environment.getExternalStorageDirectory().getPath() + File.separator + getContext().getPackageName()
				+ "/log/";
	}

	/**
	 * 检测SD卡是否存在
	 */
	private boolean checkSDcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 外部存储器访问权限
	 */
    private final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private boolean hasExternalStoragePermission(Context context) {
		int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}
}
