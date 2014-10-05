package com.codepath.startthread.twitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	
	private static final String TAG = "TwitterClient";
	
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "CrsegfjpmNEX30TFdt6TEGXcS";       // Change this
	public static final String REST_CONSUMER_SECRET = "m3ZrCoSElkF2QuPQJBG0cF9YDkouIWCYtmCUpfdVAu6EnHbWiP"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
	
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	public void getHomeTimeline(long maxId, AsyncHttpResponseHandler handler) {
		String apiUri = getApiUrl("statuses/home_timeline.json"); 
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		
		if (maxId != Long.MAX_VALUE) {
			params.put("max_id", Long.toString(maxId));
		}
		
		Log.d(TAG, "getHomeTimeline params; " + params.toString());
		client.get(apiUri, params, handler);
	}
	
	public void postTweet(String body, AsyncHttpResponseHandler handler) {
		String apiUri = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", body);
		client.post(apiUri, params, handler);
		Log.d(TAG, "postTweet");
	}

	public void getMentionsTimeline(long maxId, JsonHttpResponseHandler handler) {
		String apiUri = getApiUrl("statuses/mentions_timeline.json"); 
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		
		if (maxId != Long.MAX_VALUE) {
			params.put("max_id", Long.toString(maxId));
		}
		
		Log.d(TAG, "getMentionsTimeline params; " + params.toString());
		client.get(apiUri, params, handler);
		
	}
	
	public void getUserTimeline(long maxId, JsonHttpResponseHandler handler) {
		String apiUri = getApiUrl("statuses/user_timeline.json"); 
		Log.d(TAG, "getUserTimeline");
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		
		if (maxId != Long.MAX_VALUE) {
			params.put("max_id", Long.toString(maxId));
		}
		
		client.get(apiUri, params, handler);
	}
	
	public void getMyInfo(JsonHttpResponseHandler handler) {
		String apiUri = getApiUrl("account/verify_credentials.json"); 
		Log.d(TAG, "getMyInfo");
		client.get(apiUri, null, handler);
	}
	
	
}