package com.codepath.startthread.twitter.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Tweet {
	public static final String AT = "@";
	
	private static final String TAG = "Tweet";
	
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	
	public static Tweet fromJSON(JSONObject json) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = json.getString("text");
			tweet.uid = json.getLong("id");
			tweet.createdAt = json.getString("created_at");
			tweet.user = User.fromJSON(json.getJSONObject("user"));
		} catch (JSONException e) {
			Log.w(TAG, "could not parse json", e);
			return null;
		}
		
		return tweet;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return body;
	}

	public static List<Tweet> fromJSONArray(JSONArray json) {
		final List<Tweet> tweets = new ArrayList<Tweet>(json.length());
		for (int i=0; i<json.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = json.getJSONObject(i);
			} catch (JSONException e) {
				Log.w(TAG, "error while getting tweet json", e);
				continue;
			}
			
			final Tweet tweet = Tweet.fromJSON(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}
		return tweets;
	}
	
	
}
