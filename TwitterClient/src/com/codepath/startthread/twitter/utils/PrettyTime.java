package com.codepath.startthread.twitter.utils;

import android.text.format.DateUtils;

public class PrettyTime {
	public static String getTwitterTime(long tweetTime) {
		final long now = System.currentTimeMillis();
		long msGap = now - tweetTime;
		
		if ( msGap / DateUtils.WEEK_IN_MILLIS > 0) {
			return com.codepath.startthread.twitter.utils.DateUtils.getRelativeTimeAgo(tweetTime);
		} else if ( msGap / DateUtils.DAY_IN_MILLIS > 0) {
			long days = msGap / DateUtils.DAY_IN_MILLIS;
			return days + "d";
		} else if ( msGap / DateUtils.HOUR_IN_MILLIS > 0) {
			long hrs = msGap / DateUtils.HOUR_IN_MILLIS;
			return hrs + "h";
		}  if ( msGap / DateUtils.MINUTE_IN_MILLIS > 0) {
			long mins = msGap / DateUtils.MINUTE_IN_MILLIS;
			return mins + "m";
		} else {
			long secs = msGap;
			return secs + "s";
		}
	}
}
