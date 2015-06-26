package com.mejorandola.android;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class ApplicationAndroid extends Application {

	private final String TAG = UpdaterService.class.getSimpleName();
	private boolean serviceRunningFlag;
	private String tweetSearch = "mejorandroid";

	public boolean isServiceRunningFlag() {
		return serviceRunningFlag;
	}

	public void setServiceRunningFlag(boolean serviceRunningFlag) {
		this.serviceRunningFlag = serviceRunningFlag;
		if (serviceRunningFlag)
			iniciarServicio();
		else
			pararServicio();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		iniciarServicio();
		Log.d(TAG, "Oncreated");

	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		pararServicio();
		Log.d(TAG, "OnTerminated");
	}

	public void pararServicio() {
		stopService(new Intent(this, UpdaterService.class));
	}

	public void iniciarServicio() {
		startService(new Intent(this, UpdaterService.class));
	}

	public String getTweetSearch() {
		return tweetSearch;
	}

	public void setTweetSearch(String tweetSearch) {
		this.tweetSearch = tweetSearch;
	}
}
