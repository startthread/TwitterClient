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

public class HomeTimelineFragment extends TweetListFragment {
	private static final String TAG = "HomeTimelineFragment";
	
	private long nextMaxId = Long.MAX_VALUE;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateTimeline(true);
	}
		
	public void populateTimeline(final boolean cleanOnLoad) {
		getActivity().setProgressBarIndeterminateVisibility(true);
		client.getHomeTimeline(nextMaxId, new JsonHttpResponseHandler() {
			@Override
			protected void handleFailureMessage(Throwable throwable, String response) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				lvTweets.onRefreshComplete();
				Log.w(TAG, "failed to get timeline: " + response, throwable);
				Toast.makeText(getActivity(), R.string.error_msg_loading_timeline, 
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(Throwable throwable, String message) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				lvTweets.onRefreshComplete();
				Toast.makeText(getActivity(), R.string.error_msg_loading_timeline, 
						Toast.LENGTH_SHORT).show();
				Log.w(TAG, message);
			}
			
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				List<Tweet> tweets = Tweet.fromJSONArray(response);
				
				// update max_id to be used next time
				try {
//				ActiveAndroid.beginTransaction();
					for(Tweet t : tweets) {
//						User u = User.getUser(t.getUser());
//						if (u != null) {
//							u.delete();
//						}
//						t.getUser().save();
//						Tweet t2 = Tweet.getTweet(t);
//						if (t2 != null) {
//							t2.delete();
//						}
//						t.save();
						if (t.getTweetId() < nextMaxId) {
							nextMaxId = t.getTweetId();
						}
					}
//					ActiveAndroid.setTransactionSuccessful();
				} finally {
//					ActiveAndroid.endTransaction();
				}
				
				// in iteration load tweet with id less than current id
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

	public void addTweetAtStart(Tweet tweet) {
		tweets.add(0, tweet);
		adapter.notifyDataSetChanged();
	}
}
