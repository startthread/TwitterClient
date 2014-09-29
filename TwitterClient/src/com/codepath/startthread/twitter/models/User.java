package com.codepath.startthread.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import android.util.Log;

@Table(name = "users")
public class User extends Model {
	private static final String TAG = "User";
	
	@Column(name="name")
	private String name;
	
	@Column(name="userId", unique = true)
	private long userId;
	
	@Column(name="screen_name")
	private String screenName;
	
	@Column(name="profile_image_url")
	private String profileImageUrl;

	public static User fromJSON(JSONObject jsonObject) {
		final User user = new User();

		try {
			user.name = jsonObject.getString("name");
			user.userId = jsonObject.getLong("id");
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

	public long getUserId() {
		return userId;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

}
