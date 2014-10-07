package com.codepath.startthread.twitter.fragments;

import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetListFragment {
	private static final String TAG = "UserTimelineFragment";
	private long nextMaxId = Long.MAX_VALUE;
	private String screenName;
	
	public void populateTimeline(final boolean cleanOnLoad) {
		client.getUserTimeline(screenName, nextMaxId, new JsonHttpResponseHandler() {
			@Override
			protected void handleFailureMessage(Throwable throwable, String response) {
				lvTweets.onRefreshComplete();
				Log.w(TAG, "failed to get timeline: " + response, throwable);
				Toast.makeText(getActivity(), R.string.error_msg_loading_timeline, 
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(Throwable throwable, String message) {
				
				lvTweets.onRefreshComplete();
				Toast.makeText(getActivity(), R.string.error_msg_loading_timeline, 
						Toast.LENGTH_SHORT).show();
				Log.w(TAG, message);
			}
			
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				List<Tweet> tweets = Tweet.fromJSONArray(response);

				for(Tweet t : tweets) {
					if (t.getTweetId() < nextMaxId) {
						nextMaxId = t.getTweetId();
					}
				}
			
				nextMaxId = nextMaxId -1;
			
				if (cleanOnLoad) {
					adapter.clear();
				}
				adapter.addAll(tweets);
				lvTweets.onRefreshComplete();
				Log.d(TAG, "received timeline with tweets # " + tweets.size());
			}
		});
	}
	
	@Override
	protected void onLoadMore() {
		populateTimeline(false);
		
	}

	@Override
	protected void onRefresh() {
		nextMaxId = Long.MAX_VALUE;
		populateTimeline(true);
		
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
