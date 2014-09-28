package com.codepath.startthread.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class User {
	private static final String TAG = "User";
	
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	
	public static User fromJSON(JSONObject jsonObject) {
		final User user = new User();
		
		try {
			user.name = jsonObject.getString("name");
			user.uid = jsonObject.getLong("id");
			user.screenName = jsonObject.getString("screen_name");
			user.profileImageUrl = jsonObject.getString("profile_image_url");
		} catch (JSONException e) {
			Log.w(TAG, "could not parse json", e);
			return null;
		}
		return user;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	

}
