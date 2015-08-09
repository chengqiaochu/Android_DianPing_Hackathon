package com.miao.movieshow.movie;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

public class LocateGetApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(this);
	}

}