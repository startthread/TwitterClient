package com.codepath.startthread.twitter.fragments;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.startthread.twitter.R;
import com.codepath.startthread.twitter.Twitter;
import com.codepath.startthread.twitter.TwitterApplication;
import com.codepath.startthread.twitter.TwitterClient;
import com.codepath.startthread.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeDialogFragment extends DialogFragment {

	private Button btnTweet;
	private EditText etBody;
	private TextView tvCount;
	private TwitterClient client;
	
	private ComposerListener listener;

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
	
	public void setListener(ComposerListener listener) {
		this.listener = listener;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light);
		client = TwitterApplication.getRestClient();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_fragment_compose,
				container);

		btnTweet = (Button) view.findViewById(R.id.btnTweet);
		tvCount = (TextView) view.findViewById(R.id.tvCount);
		etBody = (EditText) view.findViewById(R.id.etBody);

		setupViews();
		return view;
	}

	private void setupViews() {
		etBody.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				final int length = etBody.length();
				btnTweet.setEnabled(length <= Twitter.MESSAGE_SIZE);
				tvCount.setText(Integer.toString(Twitter.MESSAGE_SIZE - length));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {				
			}
		});
		
//		etBody.setOnKeyListener(new View.OnKeyListener() {
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				final int count = etBody.length();
//				btnTweet.setEnabled(count <= Twitter.MESSAGE_SIZE);
//				tvCount.setText(Integer.toString(Twitter.MESSAGE_SIZE - count));
//				return false;
//			}
//		});

		btnTweet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				client.postTweet(etBody.getText().toString(),
					new JsonHttpResponseHandler() {
	
						@Override
						protected void handleFailureMessage(Throwable e, String nessage) {
							Toast.makeText(getActivity(), R.string.failed_to_post_tweet, 
									Toast.LENGTH_SHORT).show();
							getActivity().getSupportFragmentManager().popBackStack();
						}
	
						@Override
						public void onFailure(Throwable e, JSONObject arg1) {
							getActivity().getSupportFragmentManager().popBackStack();
						}
	
						@Override
						public void onSuccess(int statusCode, JSONObject response) {
							Toast.makeText(getActivity(), R.string.tweet_posted, 
									Toast.LENGTH_SHORT).show();
							
							final Tweet tweet = Tweet.fromJSON(response);
							if (tweet != null && listener != null) {
								listener.onTweetSent(tweet);
							}
							getActivity().getSupportFragmentManager().popBackStack();
						}
					});
			}

		});
	}

	private boolean validate() {
		if (etBody.getText().toString().trim().length() > Twitter.MESSAGE_SIZE) {
			return false;
		}
		return true;
	}
	
	public static interface ComposerListener {
		void onTweetSent(Tweet tweet);
	}
}
