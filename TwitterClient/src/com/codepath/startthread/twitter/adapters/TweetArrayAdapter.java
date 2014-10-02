package com.codepath.startthread.twitter.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.models.Tweet;
import com.codepath.startthread.twitter.utils.DateUtils;
import com.codepath.startthread.twitter.utils.PrettyTime;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	final ImageLoader imageLoader = ImageLoader.getInstance();
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, R.layout.tweet_item, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		final Tweet tweet = getItem(position);
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
		
			holder = new ViewHolder();
			// Get the views
			holder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
			holder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
			holder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
			holder.tvRetweetCount = (TextView) convertView.findViewById(R.id.tvRetweetCount);
			holder.tvFavoriteCount = (TextView) convertView.findViewById(R.id.tvFavoriteCount);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//final String timestamp = DateUtils.getRelativeTimeAgo(tweet.getCreatedAt());
		final String timestamp = PrettyTime.getTwitterTime(DateUtils.getTime(tweet.getCreatedAt()));
		
		// Populate views with tweet info
		holder.ivProfileImage.setImageResource(android.R.color.transparent);
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), holder.ivProfileImage);
		holder.tvName.setText(tweet.getUser().getName());
		holder.tvUsername.setText(Tweet.AT + tweet.getUser().getScreenName());
		holder.tvTimestamp.setText(timestamp);
		holder.tvBody.setText(tweet.getBody());
		holder.tvRetweetCount.setText(tweet.getRetweetCount() > 0 ? Long.toString(tweet.getRetweetCount()) : "");
		holder.tvFavoriteCount.setText(tweet.getFavoriteCount() > 0 ? Long.toString(tweet.getFavoriteCount()) : "");
		holder.tvBody.setText(tweet.getBody());

		return convertView;
	}
	
	private static class ViewHolder {
		public ImageView ivProfileImage;
		public TextView tvName;
		public TextView tvUsername;
		public TextView tvTimestamp;
		public TextView tvBody;
		public TextView tvRetweetCount;
		public TextView tvFavoriteCount;
	}
	
}
