package com.codepath.startthread.twitter.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.TwitterApplication;
import com.codepath.startthread.twitter.TwitterClient;
import com.codepath.startthread.twitter.adapters.TweetArrayAdapter;
import com.codepath.startthread.twitter.custom.EndlessScrollListener;
import com.codepath.startthread.twitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

public abstract class TweetListFragment extends SherlockFragment {
	
	private static final String TAG = "TweetListFragment";

	protected TwitterClient client;
	protected List<Tweet> tweets;
	protected TweetArrayAdapter adapter;
	protected PullToRefreshListView lvTweets;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		tweets = new ArrayList<Tweet>();
		adapter = new TweetArrayAdapter(getActivity(), tweets);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);
		lvTweets = (PullToRefreshListView) view.findViewById(R.id.lvTweets);
		
		lvTweets.setAdapter(adapter);
		
		setupViews();
		return view;
	}
	
	private void setupViews() {
		// setup endless scrolling
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d(TAG, "Page: " + page + " totalItemsCount: " + totalItemsCount);
				TweetListFragment.this.onLoadMore();
				//populateTimeline(false);
			}
		});
		
		// setup pull to refresh
		lvTweets.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				TweetListFragment.this.onRefresh();
				//nextMaxId = Long.MAX_VALUE;
				//populateTimeline(true);
			}
		});
	}

	protected abstract void onLoadMore();
	protected abstract void onRefresh();
	
}
