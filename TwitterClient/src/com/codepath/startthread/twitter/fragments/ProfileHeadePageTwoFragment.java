package com.codepath.startthread.twitter.fragments;

import com.codepath.startthread.twitter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileHeadePageTwoFragment extends Fragment {
	
	private static final String ARG_TAGLINE = "tagline";
	private static final String ARG_LOCATION = "location";
	
	public static ProfileHeadePageTwoFragment newInstance(String description, String location) {
		ProfileHeadePageTwoFragment fragment = new ProfileHeadePageTwoFragment();
		Bundle args = new Bundle();
		args.putString(ARG_TAGLINE, description);
		args.putString(ARG_LOCATION, location);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final Bundle args = getArguments();
		final View view = inflater.inflate(R.layout.fragment_profile_header_page_two, container, false);
		
		final TextView tvTagline = (TextView) view.findViewById(R.id.tvTagline);
		tvTagline.setText(args.getString(ARG_TAGLINE));
		
		final TextView tvLocation = (TextView) view.findViewById(R.id.tvLocation);
		tvLocation.setText(args.getString(ARG_LOCATION));
		
		return view;
	}
}
