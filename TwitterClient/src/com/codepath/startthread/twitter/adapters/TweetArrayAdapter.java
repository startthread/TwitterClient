package com.codepath.startthread.twitter.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.startthread.imagesearch.utils.DateUtils;
import com.codepath.startthread.twitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, R.layout.tweet_item, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		final Tweet tweet = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
		}

		// Get the views
		ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
		TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		
		final ImageLoader imageLoader = ImageLoader.getInstance();
		final String timestamp = DateUtils.getRelativeTimeAgo(tweet.getCreatedAt());
		
		// Populate views with tweet info
		ivProfileImage.setImageResource(android.R.color.transparent);
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvName.setText(tweet.getUser().getName());
		tvUsername.setText(Tweet.AT + tweet.getUser().getScreenName());
		tvTimestamp.setText(timestamp);
		tvBody.setText(tweet.getBody());

		return convertView;
	}
	
}
