package com.github.thinwonton.android.logger.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.thinwonton.android.logger.ConsoleInterceptor;
import com.github.thinwonton.android.logger.FileInterceptor;
import com.github.thinwonton.android.logger.LogLevel;
import com.github.thinwonton.android.logger.Logger;
import com.github.thinwonton.android.logger.SimpleFileCreator;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Logger.DEBUG = true;
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		testGlobalTag();
		testLocalTag();
		testFilter();
		testLogFile();
	}

	private void testLogFile() {
		FileInterceptor fileInterceptor = new FileInterceptor.Builder().append(true).level(LogLevel.INFO)
				.fileCreator(new SimpleFileCreator(getApplicationContext())).build();
		Logger.init().tag("global").add(new ConsoleInterceptor(LogLevel.DEBUG)).add(fileInterceptor);

		Logger.v("v logFile");
		Logger.d("local", "debug logFile");
		Logger.i("info logFile");
		Logger.w("local", "warn logFile");
		Logger.e(new Exception("global tag logFile exception"));
		Logger.e("local", new Exception("local tag logFile exception"));
		Logger.wtf("wtf logFile");
	}

	private void testGlobalTag() {
		Logger.init().tag("global").add(new ConsoleInterceptor(LogLevel.FULL));

		Logger.v("hello");
		Logger.d("hello");
		Logger.i("hello");
		Logger.w("hello");
		Logger.e("hello");
		Logger.wtf("hello");
	}

	private void testLocalTag() {
		Logger.init().tag("global").add(new ConsoleInterceptor(LogLevel.FULL));

		Logger.v("LocalTag");
		Logger.d("local", "LocalTag");
		Logger.i("LocalTag");
		Logger.w("local", "LocalTag");
		Logger.e("LocalTag");
		Logger.wtf("LocalTag");
	}

	private void testFilter() {
		Logger.init().tag("global").add(new ConsoleInterceptor(LogLevel.DEBUG));

		Logger.v("Filter");
		Logger.d("local", "Filter");
		Logger.i("Filter");
		Logger.w("local", "Filter");
		Logger.e(new Exception("Filter exception"));
		Logger.e("local", new Exception("Filter exception"));
		Logger.wtf("Filter");
	}
}
