package com.mejorandola.android.adapterlist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mejorandola.android.R;
import com.mejorandola.android.models.Tweet;
import com.mejorandola.android.utils.BitmapManager;
import com.mejorandola.android.utils.DateUtils;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	private Context context;
	private ArrayList<Tweet> tweets;

	public TweetAdapter(Context context, int resource,ArrayList<Tweet> tweets) {
		super(context, resource,tweets);
		
		this.context = context;
		this.tweets=tweets;
		
	}

	static class ViewHolder {
		public ImageView avatar;
		public TextView name;
		public TextView screenName;
		public TextView text;
		public TextView createdAt;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		ViewHolder viewHolder;
		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(
					R.layout.row_tweet, parent, false);

			 viewHolder = new ViewHolder();
			
			//recuperar las Views del xml
			viewHolder.avatar = (ImageView) convertView
					.findViewById(R.id.avatar);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.screenName = (TextView) convertView
					.findViewById(R.id.screen_name);
			viewHolder.text = (TextView) convertView.findViewById(R.id.text);
			viewHolder.createdAt = (TextView) convertView
					.findViewById(R.id.created_at);
			convertView.setTag(viewHolder);
		}

		else{
		viewHolder = (ViewHolder) convertView.getTag();
		}
		//recupera la imagen usando la clase 
		
		BitmapManager.getInstance().loadBitmap(
				tweets.get(position).getProfileImageUrl(), viewHolder.avatar);
		
		viewHolder.name.setText(tweets.get(position).getName());
		//viewHolder.screenName.setText(tweets.get(position).getScreenName());
		viewHolder.screenName.setText("@" + tweets.get(position).getScreenName());
		viewHolder.text.setText(tweets.get(position).getText());
		//viewHolder.createdAt.setText(tweets.get(position).getCreatedAt());
		viewHolder.createdAt.setText(DateUtils.setDateFormat(tweets.get(position).getCreatedAt()));
		
		return convertView;
		
	}
	
	
	


}
