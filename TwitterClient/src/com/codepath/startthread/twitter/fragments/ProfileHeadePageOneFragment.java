package com.codepath.startthread.twitter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.Twitter;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileHeadePageOneFragment extends Fragment {

	private static final String ARG_SCREEN_NAME = "screen_name";
	private static final String ARG_NAME = "name";
	private static final String ARG_IMAGE_URL = "image_url";

	public static ProfileHeadePageOneFragment newInstance(String imageUrl,
			String name, String screenName) {
		ProfileHeadePageOneFragment fragment = new ProfileHeadePageOneFragment();
		Bundle args = new Bundle();
		args.putString(ARG_IMAGE_URL, imageUrl);
		args.putString(ARG_NAME, name);
		args.putString(ARG_SCREEN_NAME, screenName);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final Bundle args = getArguments();
		final View view = inflater.inflate(R.layout.fragment_profile_header_page_one, container, false);
		
		final TextView tvName = (TextView) view.findViewById(R.id.tvName);
		tvName.setText(args.getString(ARG_NAME));
		
		final TextView tvScreenName = (TextView) view.findViewById(R.id.tvScreenName);
		tvScreenName.setText(Twitter.AT + args.getString(ARG_SCREEN_NAME));
		
		final ImageView ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
		ivProfile.setImageResource(R.drawable.background_transparent);
		ImageLoader.getInstance().displayImage(args.getString(ARG_IMAGE_URL), ivProfile);
		
		return view;
	}
}
