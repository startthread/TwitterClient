package com.codepath.startthread.twitter.activities;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.TwitterApplication;
import com.codepath.startthread.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends SherlockFragmentActivity {

	private static final String TAG = "ProfileActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		loadProfileInfo();
	}

	private void loadProfileInfo() {
		TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
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
				Log.d(TAG, "received profile info");
				populateProfileHeader(user);
			}
		});
	}
	
	private void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFollowingCount() + " Following");
		
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfile);
	}

}
