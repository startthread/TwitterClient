package com.codepath.startthread.twitter.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.query.Select;

import android.util.Log;

@Table(name = "tweets")
public class Tweet extends Model {
	public static final String AT = "@";
	private static final String TAG = "Tweet";

	@Column(name = "tweetId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long tweetId;

	@Column(name = "body")
	private String body;

	@Column(name = "created_at")
	private String createdAt;

	@Column(name = "retweet_count")
	private long retweetCount;
	
	@Column(name = "favorite_count")
	private long favoriteCount;
	
	@Column(name = "user")
	private User user;

	public Tweet() {
		super();
	}

	public String getBody() {
		return body;
	}

	public long getTweetId() {
		return tweetId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}
	
	public long getRetweetCount() {
		return retweetCount;
	}

	public long getFavoriteCount() {
		return favoriteCount;
	}

	public static Tweet fromJSON(JSONObject json) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = json.getString("text");
			tweet.tweetId = json.getLong("id");
			tweet.createdAt = json.getString("created_at");
			tweet.retweetCount = json.optLong("retweet_count");
			tweet.favoriteCount = json.optLong("favourites_count");
			tweet.user = User.fromJSON(json.getJSONObject("user"));
		} catch (JSONException e) {
			Log.w(TAG, "could not parse json", e);
			return null;
		}

		return tweet;
	}


	public static List<Tweet> fromJSONArray(JSONArray json) {
		final List<Tweet> tweets = new ArrayList<Tweet>(json.length());
		for (int i = 0; i < json.length(); i++) {
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

	public static Tweet getTweet(Tweet tweet) {
	    return new Select()
	        .from(Tweet.class)
	        .where("tweetId = ?", tweet.getTweetId())
	        .executeSingle();
	}
}
