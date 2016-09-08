package com.github.thinwonton.android.logger.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.thinwonton.android.logger.ConsoleInterceptor;
import com.github.thinwonton.android.logger.LogLevel;
import com.github.thinwonton.android.logger.Logger;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		testGlobalTag();
		testLocalTag();
		testFilter();
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

		Logger.v("hello1");
		Logger.d("local", "hello1");
		Logger.i("hello1");
		Logger.w("local", "hello1");
		Logger.e("hello1");
		Logger.wtf("hello1");
	}

	private void testFilter() {
		Logger.init().tag("global").add(new ConsoleInterceptor(LogLevel.DEBUG));

		Logger.v("hello2");
		Logger.d("local", "hello2");
		Logger.i("hello2");
		Logger.w("local", "hello2");
		Logger.e(new Exception("hello2 exception"));
		Logger.e("local", new Exception("hello2 exception"));
		Logger.wtf("hello2");
	}
}
