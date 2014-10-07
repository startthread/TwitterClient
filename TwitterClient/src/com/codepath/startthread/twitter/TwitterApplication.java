package com.codepath.startthread.twitter;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.codepath.startthread.twitter.activities.ProfileActivity;
import com.codepath.startthread.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 * 
 *     RestClient client = RestClientApp.getRestClient();
 *     // use client to send requests to API
 *     
 */
public class TwitterApplication extends com.activeandroid.app.Application {
	private static final String TAG = "TwitterApplication";
	
	private static Context context;
	private User currentUser;

	@Override
	public void onCreate() {
		super.onCreate();
		TwitterApplication.context = this;

		loadCurrentUserInfo();
		
		// Create global configuration and initialize ImageLoader with this configuration
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
				cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.defaultDisplayImageOptions(defaultOptions)
		.build();
		ImageLoader.getInstance().init(config);
	}

	public static TwitterClient getRestClient() {
		return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterApplication.context);
	}
	
	public void loadCurrentUserInfo() {
		getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			protected void handleFailureMessage(Throwable throwable, String response) {
			
				Log.w(TAG, "failed to get current user info: " + response, throwable);
			}
			
			@Override
			public void onFailure(Throwable throwable, String message) {
				Log.w(TAG, message);
			}
			
			@Override
			public void onSuccess(JSONObject response) {
				currentUser = User.fromJSON(response);
			}
		});
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
}