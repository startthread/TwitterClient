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
import com.codepath.startthread.imagesearch.custom.EndlessScrollListener;
import com.codepath.startthread.twitter.TwitterApplication;
import com.codepath.startthread.twitter.TwitterClient;
import com.codepath.startthread.twitter.adapters.TweetArrayAdapter;
import com.codepath.startthread.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends SherlockFragmentActivity {

	private static final String TAG = "TimelineActivity";
	private TwitterClient client;
	private List<Tweet> tweets;
	private TweetArrayAdapter adapter;
	private ListView lvTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		client = TwitterApplication.getRestClient();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		adapter = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(adapter);
		
		setupViews();
		populateTimeline();
	}
	
	private void setupViews() {
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d(TAG, "Page: " + page + " totalItemsCount: " + totalItemsCount);
				populateTimeline();
			}
		});
		
	}

	public void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			protected void handleFailureMessage(Throwable throwable, String response) {
				Log.w(TAG, "failed to get timeline: " + response, throwable);
				Toast.makeText(TimelineActivity.this, R.string.error_msg_loading_timeline, 
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(Throwable throwable, String message) {
				Log.w(TAG, message);
			}
			
			@SuppressLint("NewApi")
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				List<Tweet> tweets = Tweet.fromJSONArray(response);
				client.updateLeastLoadedId(tweets);
				adapter.addAll(tweets);
				Log.d(TAG, "received timeline");
			}
		});
	}
}
