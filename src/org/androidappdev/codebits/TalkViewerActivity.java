package org.androidappdev.codebits;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class TalkViewerActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.talk_viewer);
		
		int position = getIntent().getIntExtra(CodebitsActivity.TALK_POSITION, 0);
		Talk talk = CodebitsActivity.getTalk(position);
		
		TextView textView = (TextView) findViewById(R.id.talk_title);
		textView.setText(talk.getTitle());
		textView = (TextView) findViewById(R.id.talk_description);
		textView.setText(talk.getDescription());
		textView.setMovementMethod(new ScrollingMovementMethod());
	}
}
