package com.codepath.startthread.twitter.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.startthread.twitter.R;

public class ComposeDialogFragment extends DialogFragment {

	
	public ComposeDialogFragment() {
		super();
	}
	
	public static ComposeDialogFragment newInstance(String title) {
		ComposeDialogFragment frag = new ComposeDialogFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_fragment_compose, container);
		
		return view;
	}
}
