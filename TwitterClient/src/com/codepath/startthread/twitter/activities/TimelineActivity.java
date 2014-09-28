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

import com.codepath.apps.restclienttemplate.R;
import com.codepath.startthread.twitter.TwitterApplication;
import com.codepath.startthread.twitter.TwitterClient;
import com.codepath.startthread.twitter.adapters.TweetArrayAdapter;
import com.codepath.startthread.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

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
		
		populateTimeline();
	}
	
	public void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable throwable, String message) {
				Log.w(TAG, message);
			}

//			@SuppressLint("NewApi") @Override
//			public void onSuccess(JSONArray response) {
//				adapter.addAll(Tweet.fromJSONArray(response));
//				Log.d(TAG, "received timeline");
//			}
			
			@SuppressLint("NewApi")
			@Override
			public void onSuccess(int arg0, JSONArray arg1) {
				//Log.v(TAG + "11", arg1.toString());
				adapter.addAll(Tweet.fromJSONArray(arg1));
				Log.d(TAG, "received timeline");
			}
		});
	}
}
