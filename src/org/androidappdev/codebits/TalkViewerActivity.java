package org.androidappdev.codebits;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class TalkViewerActivity extends Activity {
	private static final String TAG = "TalkViewerActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.talk_viewer);

		String id = getIntent().getStringExtra(CodebitsActivity.TALK_ID);
		Talk talk = CodebitsActivity.getTalk(id);
		Log.d(TAG, "Talk.getId(): " + talk.getId());

		TextView textView = (TextView) findViewById(R.id.talk_title);
		textView.setText(talk.getTitle());
		textView = (TextView) findViewById(R.id.talk_description);
		textView.setText(talk.getDescription());
		textView.setMovementMethod(new ScrollingMovementMethod());
	}
}
