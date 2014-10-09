package com.codepath.startthread.twitter.activities;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.Twitter;
import com.codepath.startthread.twitter.TwitterApplication;
import com.codepath.startthread.twitter.fragments.ProfileHeadePageOneFragment;
import com.codepath.startthread.twitter.fragments.ProfileHeadePageTwoFragment;
import com.codepath.startthread.twitter.fragments.UserTimelineFragment;
import com.codepath.startthread.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

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
				Log.d(TAG, "received profile info: " + response.toString());
				User user = User.fromJSON(response);
				getActionBar().setTitle(Twitter.AT + user.getScreenName());
				
				final ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
				final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), user);
				viewPager.setAdapter(adapter);
				
				final PageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
		        indicator.setViewPager(viewPager);
		        
				populateProfileHeader(user);
			}
		});
	}
	
	private void populateProfileHeader(User user) {
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		TextView tvTweets = (TextView) findViewById(R.id.tvTweets);
		ImageView ivProfileBackground = (ImageView) findViewById(R.id.ivProfileBackground);
		
		tvTweets.setText(Long.toString(user.getStatusesCount()));
		tvFollowers.setText(Long.toString(user.getFollowersCount()));
		tvFollowing.setText(Long.toString(user.getFollowingCount()));
		
		ImageLoader.getInstance().displayImage(user.getProfileBannerUrl(), ivProfileBackground);
	}
	
	private static class PagerAdapter extends FragmentPagerAdapter {
		private static final int NUM_PAGES = 2;
		
		private User user;
		
		public PagerAdapter(FragmentManager fm, User user) {
			super(fm);
			this.user = user;
		}
		
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return ProfileHeadePageOneFragment.newInstance(user.getProfileImageUrl(),
						user.getName(), user.getScreenName());
						
			case 1:
				return ProfileHeadePageTwoFragment.newInstance(user.getTagline(), user.getLocation());
			}
			return null;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
		
	}

}
