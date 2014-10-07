package com.codepath.startthread.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "users")
public class User extends Model {
	private static final String TAG = "User";

	@Column(name = "name")
	private String name;

	@Column(name = "userId", unique = true, onUniqueConflict = ConflictAction.REPLACE)
	private long userId;

	@Column(name = "screen_name")
	private String screenName;
	
	@Column(name = "tagline")
	private String tagline;

	@Column(name = "profile_image_url")
	private String profileImageUrl;
	
	@Column(name = "followers_count")
	private long followersCount;
	
	@Column(name = "following_count")
	private long followingCount;
	
	@Column(name = "statuses_count")
	private long statusesCount;

	public User() {
		super();
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
	
	public long getFollowersCount() {
		return followersCount;
	}

	public long getFollowingCount() {
		return followingCount;
	}
	
	public String getTagline() {
		return tagline;
	}	

	public long getStatusesCount() {
		return statusesCount;
	}

	public static User getUser(User user) {
	    return new Select()
	        .from(User.class)
	        .where("userId = ?", user.getUserId())
	        .executeSingle();
	}
	
	public static User fromJSON(JSONObject jsonObject) {
		final User user = new User();

		try {
			user.name = jsonObject.getString("name");
			user.userId = jsonObject.getLong("id");
			user.screenName = jsonObject.getString("screen_name");
			user.tagline = jsonObject.getString("description");
			user.profileImageUrl = jsonObject.getString("profile_image_url");
			user.followersCount = jsonObject.getLong("followers_count");
			user.followingCount = jsonObject.getLong("friends_count");
			user.statusesCount = jsonObject.getLong("statuses_count");
		} catch (JSONException e) {
			Log.w(TAG, "could not parse json", e);
			return null;
		}
		return user;
	}
}
