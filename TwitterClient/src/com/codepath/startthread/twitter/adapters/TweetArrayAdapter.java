package com.codepath.startthread.twitter.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.activities.ProfileActivity;
import com.codepath.startthread.twitter.models.Tweet;
import com.codepath.startthread.twitter.models.Tweet.TweetType;
import com.codepath.startthread.twitter.utils.DateUtils;
import com.codepath.startthread.twitter.utils.PrettyTime;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	final ImageLoader imageLoader = ImageLoader.getInstance();
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, R.layout.tweet_item, tweets);
	}
	
	@Override
	public int getViewTypeCount() {
		return Tweet.TweetType.values().length;
	}
	
	@Override
	public int getItemViewType(int position) {
		return getItem(position).type.ordinal();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		final Tweet tweet = getItem(position);
		ViewHolder holder = null;
		
		if (convertView == null) {
			int type = getItemViewType(position);
			// Inflate XML layout based on the type     
			convertView = getInflatedLayoutForType(type);
		
			holder = new ViewHolder();
			// Get the views
			holder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
			holder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
			holder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
			holder.tvRetweetCount = (TextView) convertView.findViewById(R.id.tvRetweetCount);
			holder.tvFavoriteCount = (TextView) convertView.findViewById(R.id.tvFavoriteCount);
			
			holder.ivMediaOne = (ImageView) convertView.findViewById(R.id.ivMediaOne);
			holder.ivMediaTwo = (ImageView) convertView.findViewById(R.id.ivMediaTwo);
			//holder.ivMediaThree = (ImageView) convertView.findViewById(R.id.ivMediaThree);
			//holder.ivMediaFour = (ImageView) convertView.findViewById(R.id.ivMediaFour);
			
			holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					final Intent intent = new Intent(getContext(), ProfileActivity.class);
					intent.putExtra(ProfileActivity.EXTRA_SCREEN_NAME, tweet.getUser().getScreenName());
					intent.putExtra(ProfileActivity.EXTRA_USER_ID, tweet.getUser().getUserId());
					getContext().startActivity(intent);
				}
			});
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

		if (holder.ivMediaOne != null) {
			imageLoader.displayImage(tweet.getMedia(0), holder.ivMediaOne);
		}
		if (holder.ivMediaTwo != null) {
			imageLoader.displayImage(tweet.getMedia(1), holder.ivMediaTwo);
		}
		return convertView;
	}
	
	private View getInflatedLayoutForType(int type) {
		if (type == TweetType.TEXT.ordinal()) {
			return LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, null);
		} else if (type == TweetType.MEDIA_ONE.ordinal()) {
			return LayoutInflater.from(getContext()).inflate(R.layout.tweet_item_media_one, null);
		} else if (type == TweetType.MEDIA_TWO.ordinal()) {
			return LayoutInflater.from(getContext()).inflate(R.layout.tweet_item_media_two, null);
		} else {
			return null;
		}
	}
	
	
	
	private static class ViewHolder {
		public ImageView ivProfileImage;
		public TextView tvName;
		public TextView tvUsername;
		public TextView tvTimestamp;
		public TextView tvBody;
		public TextView tvRetweetCount;
		public TextView tvFavoriteCount;
		
		public ImageView ivMediaOne;
		public ImageView ivMediaTwo;
		public ImageView ivMediaThree;
		public ImageView ivMediaFour;
	}
	
}
