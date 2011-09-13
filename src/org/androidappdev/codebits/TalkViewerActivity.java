package org.androidappdev.codebits;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Color;

public class TalkViewerActivity extends Activity {
	private static final String TAG = "TalkViewerActivity";
	
	/**
	 * Perform an HTTP request
	 * 
	 * @param url
	 *            the URL for the request
	 * @return a String with the result of the request
	 */
	private static String performHttpRequest(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			Log.d(TAG, "Exception", e);
		}
		return builder.toString();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.talk_viewer);

		String id = getIntent().getStringExtra(CodebitsActivity.TALK_ID);
		String source = getIntent().getStringExtra("source");
		final String token = getIntent().getStringExtra("token");
		
		final Talk talk = CodebitsActivity.getTalk(id);
		Log.d(TAG, "Talk.getId(): " + talk.getId());
		Log.d(TAG, "Token: " + getIntent().getStringExtra("token"));

		TextView textView = (TextView) findViewById(R.id.talk_title);
		textView.setText(talk.getTitle());
		textView = (TextView) findViewById(R.id.talk_description);
		textView.setText(talk.getDescription());
		textView.setMovementMethod(new ScrollingMovementMethod());
		
		if(source.equals("rated") || token == null ) {
			RelativeLayout l = (RelativeLayout) findViewById(R.id.relativeLayout1);
			l.setVisibility(View.GONE);
		}
		
		final ImageButton thumbsup = (ImageButton) findViewById(R.id.imageButton1);
		final ImageButton thumbsdown = (ImageButton) findViewById(R.id.imageButton2);

		thumbsup.setBackgroundColor(Color.WHITE);
		thumbsdown.setBackgroundColor(Color.WHITE);

		thumbsup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String url = "https://services.sapo.pt/Codebits/calluptalk/"+talk.getId();
				if (token != null) {
					url += "?token=" + token;
				}
				performHttpRequest(url);
				thumbsup.setBackgroundColor(Color.GREEN);
				thumbsdown.setBackgroundColor(Color.WHITE);
			}
		});

		thumbsdown.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String url = "https://services.sapo.pt/Codebits/calldowntalk/"+talk.getId();
				if (token != null) {
					url += "?token=" + token;
				}
				performHttpRequest(url);
				thumbsdown.setBackgroundColor(Color.RED);
				thumbsup.setBackgroundColor(Color.WHITE);
			}
		});


	}
	
	
	
}
