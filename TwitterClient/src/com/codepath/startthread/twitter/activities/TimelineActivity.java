package com.codepath.startthread.twitter.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.startthread.twitter.TwitterApplication;
import com.codepath.startthread.twitter.TwitterClient;
import com.codepath.startthread.twitter.adapters.TweetArrayAdapter;
import com.codepath.startthread.twitter.custom.EndlessScrollListener;
import com.codepath.startthread.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;

public class TimelineActivity extends SherlockFragmentActivity {

	private static final String TAG = "TimelineActivity";
	private TwitterClient client;
	private List<Tweet> tweets;
	private TweetArrayAdapter adapter;
	private PullToRefreshListView lvTweets;
	private long nextMaxId = Long.MAX_VALUE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		client = TwitterApplication.getRestClient();
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		adapter = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(adapter);
		
		setupViews();
		populateTimeline();
	}
	
	private void setupViews() {
		// setup endless scrolling
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d(TAG, "Page: " + page + " totalItemsCount: " + totalItemsCount);
				populateTimeline();
			}
		});
		
		// setup pull to refresh
		lvTweets.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				nextMaxId = Long.MAX_VALUE;
				adapter.clear();
				populateTimeline();
			}
		});
	}

	public void populateTimeline() {
		client.getHomeTimeline(nextMaxId, new JsonHttpResponseHandler() {
			@Override
			protected void handleFailureMessage(Throwable throwable, String response) {
				lvTweets.onRefreshComplete();
				Log.w(TAG, "failed to get timeline: " + response, throwable);
				Toast.makeText(TimelineActivity.this, R.string.error_msg_loading_timeline, 
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(Throwable throwable, String message) {
				lvTweets.onRefreshComplete();
				Toast.makeText(TimelineActivity.this, R.string.error_msg_loading_timeline, 
						Toast.LENGTH_SHORT).show();
				Log.w(TAG, message);
			}
			
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				List<Tweet> tweets = Tweet.fromJSONArray(response);
				
				// update max_id to be used next time
				for(Tweet t : tweets) {
					t.save();
					if (t.getTweetId() < nextMaxId) {
						nextMaxId = t.getTweetId();
					}
				}
				// in iteration load tweet with id less than current id
				nextMaxId = nextMaxId -1; 
				
				adapter.addAll(tweets);
				lvTweets.onRefreshComplete();
				Log.d(TAG, "received timeline with tweets # " + tweets.size());
			}
		});
	}
	
	
}
