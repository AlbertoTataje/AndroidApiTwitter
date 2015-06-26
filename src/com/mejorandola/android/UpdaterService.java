package com.mejorandola.android;

import java.util.ArrayList;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.mejorandola.android.DB.DBHelper;
import com.mejorandola.android.DB.DBOperations;
import com.mejorandola.android.models.Tweet;
import com.mejorandola.android.utils.ConstantsUtils;
import com.mejorandola.android.utils.TwitterUtils;

public class UpdaterService extends Service{
		
	private final String TAG = UpdaterService.class.getSimpleName();
	private static final int DELAY = 20000;
	private boolean runFlag = false;
	private Updater updater;
	private ApplicationAndroid application;
	private DBOperations dbOperation;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "Oncreated");
		application = (ApplicationAndroid) getApplication();
		updater = new Updater();
		dbOperation = new DBOperations(this);
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "OnDestruied");
		runFlag = false;
		
		updater.interrupt();
		application.setServiceRunningFlag(false);
		//para no ocupar memoria
		updater = null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		Log.d(TAG, "OnStartComand");
		if(!runFlag){
			this.runFlag = true;
			application.setServiceRunningFlag(true);
			updater.start();
		}
		return START_STICKY	 ;
	}
	
	private class Updater extends Thread{
		
		private ArrayList<Tweet> timeline = new ArrayList<Tweet>();
		Intent intent;
		
		public Updater(){
			super("UpdaterService-UpdaterThread");
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			UpdaterService updaterService = UpdaterService.this;
			
			
			while( updaterService.runFlag){
				Log.d(TAG,"UpdateThread-runnig");
				try {
					//Recuperar los tweets de internet 
					timeline=TwitterUtils.getTimelineForSearchTerm(application.getTweetSearch());
					if(timeline!=null){
						//Borra los tweets anteriores
						dbOperation.DeleteRows();
						Log.d(TAG, "Eliminado las filas de la tabla");
					}
			    	//values
			    	ContentValues values = new ContentValues();	
					for(Tweet tweet : timeline){
						values.clear();
						values.put(DBHelper.C_ID, tweet.getId());
						values.put(DBHelper.C_NAME, tweet.getName());
						values.put(DBHelper.C_SCREEN_NAME, tweet.getScreenName());
						values.put(DBHelper.C_IMAGE_PROFILE_URL, tweet.getProfileImageUrl());
						values.put(DBHelper.C_TEXT, tweet.getText());
						values.put(DBHelper.C_CREATED_AT, tweet.getCreatedAt());		
						
						
						
						dbOperation.insertOrIgnore(values);
						
					}
					Log.d(TAG, "Insertar filas de la tabla");
				
					intent = new Intent(ConstantsUtils.NEW_TWEETS_INTENT_FILTER);
					
					
					//Se suscribe a esta clasex
					//Para que esta clase se convierte como un publisher(publicador) a los receiver(suscriptores)
					updaterService.sendBroadcast(intent);
					Thread.sleep(DELAY);
				}catch (Exception e) {
					// TODO: handle exception
					Log.d(TAG,"No se pudo Insertar a la BD");
					updaterService.runFlag=false;
					application.setServiceRunningFlag(false);
				}
			}
		}
		
	}

}
