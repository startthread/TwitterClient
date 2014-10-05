package com.codepath.startthread.twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.TwitterApplication;
import com.codepath.startthread.twitter.fragments.ComposeDialogFragment;
import com.codepath.startthread.twitter.fragments.ComposeDialogFragment.ComposerListener;
import com.codepath.startthread.twitter.fragments.HomeTimelineFragment;
import com.codepath.startthread.twitter.fragments.MentionsTimelineFragment;
import com.codepath.startthread.twitter.listeners.SherlockTabListener;
import com.codepath.startthread.twitter.models.Tweet;

public class TimelineActivity extends SherlockFragmentActivity implements ComposerListener {

	private static final String TAG = "TimelineActivity";
	private static final String HOME_TIMELINE_TAG = "timeline";
	private static final String MENTIONS_TIMELINE_TAG = "mentions";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);	
		setupTabs();
	}
	

	private void setupTabs() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Tab timelineTab = actionBar.newTab()
				.setText(R.string.timeline)
				.setTabListener(new SherlockTabListener<HomeTimelineFragment>(R.id.flContainer, 
						this, HOME_TIMELINE_TAG, HomeTimelineFragment.class));
		
		actionBar.addTab(timelineTab);
		actionBar.selectTab(timelineTab);
		
		Tab mentionsTab = actionBar.newTab()
				.setText(R.string.mentions)
				.setTabListener(new SherlockTabListener<MentionsTimelineFragment>(R.id.flContainer, 
						this, MENTIONS_TIMELINE_TAG, MentionsTimelineFragment.class));

		actionBar.addTab(mentionsTab);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.timeline, menu);
	   return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_compose:
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			Fragment prev = getSupportFragmentManager().findFragmentByTag("composer");
			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);
			ComposeDialogFragment cdf = ComposeDialogFragment.newInstance("Me");
			cdf.setListener(this);
			cdf.show(ft, "composer");
			return true;
			
		case R.id.action_profile:
			final Intent intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
			return true;
		
		case R.id.action_logout:
			TwitterApplication.getRestClient().clearAccessToken();
			finish();
			return true;
		
		}
		
		
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTweetSent(Tweet tweet) {
		Fragment fragment = getSupportFragmentManager().findFragmentByTag(HOME_TIMELINE_TAG);
		if (fragment != null) {
			((HomeTimelineFragment) fragment).addTweetAtStart(tweet);
		}
	}
}
