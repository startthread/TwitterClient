package com.codepath.startthread.imagesearch.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.util.Log;


public class DateUtils {

	private static final String TAG = "DateUtils";
	
	private static String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
	private static SimpleDateFormat twitterSdf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);

	
	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public static String getRelativeTimeAgo(String rawJsonDate) {
		twitterSdf.setLenient(true);

		String relativeDate = "";
		try {
			long dateMillis = twitterSdf.parse(rawJsonDate).getTime();
			relativeDate = android.text.format.DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), android.text.format.DateUtils.SECOND_IN_MILLIS)
					.toString();
		} catch (ParseException e) {
			Log.e(TAG, "error while parsing date", e);
		}

		return relativeDate;
	}
}
