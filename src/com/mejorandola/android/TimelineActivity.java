package com.mejorandola.android;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.mejorandola.android.DB.DBOperations;
import com.mejorandola.android.adapterlist.TweetAdapter;
import com.mejorandola.android.models.Tweet;
import com.mejorandola.android.utils.ConstantsUtils;
import com.mejorandola.android.utils.NetworkUtils;
import com.mejorandola.android.utils.TwitterUtils;

public class TimelineActivity extends  ActionBarActivity implements MenuDialog.Communicator{

	private ListView listView;
	private DBOperations dbOperations;
	private TweetAdapter adapter;
	private TimelineReceiver receiver;
	private IntentFilter filter;
	private ApplicationAndroid application;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		listView = (ListView) findViewById(R.id.lv_timeline);
		application = (ApplicationAndroid) getApplication();
		
		dbOperations = new DBOperations(this);
		receiver = new TimelineReceiver();
		filter = new IntentFilter(ConstantsUtils.NEW_TWEETS_INTENT_FILTER);
		
		 new GetTimeLineTask().execute();
		
	}
	
	public void DialogFragment(View v){
		FragmentManager manager_support =getSupportFragmentManager(); 
		MenuDialog dialog = new MenuDialog();
		dialog.show(manager_support, "MyDialog");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//Para registrar a donde llega, y el filtro 
		registerReceiver(receiver, filter);
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(receiver);
	}
	
	private void updateListView(ArrayList<Tweet> tweets){
		adapter=new TweetAdapter(this, R.layout.row_tweet, tweets);
		listView.setAdapter(adapter);
	}
	
	public void updateListWhitCache(){
		adapter = new TweetAdapter(this, R.layout.row_tweet, dbOperations.getStatusUpdates());
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
																						
	public class GetTimeLineTask extends AsyncTask<Object,Void,ArrayList<Tweet>>{

		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(TimelineActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.label_tweet_search_loader));
			progressDialog.show();
		}
		
		@Override
		protected ArrayList<Tweet> doInBackground(Object... arg0) {
			//Acceder a la busqueda por Twitter
	
			if(NetworkUtils.haveNetworkConnection(TimelineActivity.this)){
				return TwitterUtils.getTimelineForSearchTerm(application.getTweetSearch());
			}else{
				
				return dbOperations.getStatusUpdates();
			}
			
		}
		
		@Override
		protected void onPostExecute(ArrayList<Tweet> tweets) {
			// TODO Auto-generated method stub
			super.onPostExecute(tweets);
			progressDialog.dismiss();
			
			if (tweets.isEmpty()) {
				Toast.makeText(TimelineActivity.this, getResources().getString(R.string.label_tweets_not_found),
						Toast.LENGTH_SHORT).show();
			} else {
				updateListView(tweets);
				Toast.makeText(TimelineActivity.this, getResources().getString(R.string.label_tweets_downloaded),
						Toast.LENGTH_SHORT).show();
			}
		}
		                                                       
	}                                                             
	
	
	//el suscriptor subscrito a la clase UpdaterService(publicador)4
	class TimelineReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Toast.makeText(TimelineActivity.this, "Tweets Actualizados",
					Toast.LENGTH_SHORT).show();
			updateListWhitCache();
			
		}
		  
		
	}


	@Override
	public void onDialogMessage(String tweetSearch) {
		// TODO Auto-generated method stub
		
		
		//mato el servicio 
		//application.setServiceRunningFlag(false);
		
		//vuelvo iniciarlo
		application.setTweetSearch(tweetSearch);
		//application.setServiceRunningFlag(true);
		
		 new GetTimeLineTask().execute();
		 
		Toast.makeText(TimelineActivity.this, tweetSearch,
				Toast.LENGTH_SHORT).show();
	}

}
