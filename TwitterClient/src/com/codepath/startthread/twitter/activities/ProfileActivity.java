package com.codepath.startthread.twitter.activities;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.TwitterApplication;
import com.codepath.startthread.twitter.fragments.UserTimelineFragment;
import com.codepath.startthread.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends SherlockFragmentActivity {

	private static final String TAG = "ProfileActivity";
	
	public static final String EXTRA_SCREEN_NAME = "screen_name";
	public static final String EXTRA_USER_ID = "user_id";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		final String screenName = getIntent().getStringExtra(EXTRA_SCREEN_NAME);
		final long userId = getIntent().getLongExtra(EXTRA_USER_ID, 0);
		loadProfileInfo(userId, screenName);
		
		final UserTimelineFragment fragment = (UserTimelineFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragmentUserTimeline);
		Log.d(TAG, "onCreate: " + screenName);
		fragment.setScreenName(screenName);
		fragment.populateTimeline(true);
	}

	private void loadProfileInfo(long userId, String screenName) {
		TwitterApplication.getRestClient().getUserInfo(userId, screenName, new JsonHttpResponseHandler() {
			@Override
			protected void handleFailureMessage(Throwable throwable, String response) {
			
				Log.w(TAG, "failed to get profile: " + response, throwable);
				Toast.makeText(ProfileActivity.this, R.string.error_msg_loading_profile, 
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(Throwable throwable, String message) {
				
				Toast.makeText(ProfileActivity.this, R.string.error_msg_loading_profile, 
						Toast.LENGTH_SHORT).show();
				Log.w(TAG, message);
			}
			
			@Override
			public void onSuccess(JSONObject response) {
				User user = User.fromJSON(response);
				getActionBar().setTitle("@" + user.getScreenName());
				Log.d(TAG, "received profile info: " + response.toString());
				populateProfileHeader(user);
			}
		});
	}
	
	private void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		TextView tvTweets = (TextView) findViewById(R.id.tvTweets);
		ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvTweets.setText(user.getStatusesCount() + " Tweets");
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFollowingCount() + " Following");
		
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfile);
	}

}
