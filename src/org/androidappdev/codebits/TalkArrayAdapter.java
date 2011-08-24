package org.androidappdev.codebits;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TalkArrayAdapter extends ArrayAdapter<Talk> {
	private Activity context;
	private Talk[] talks;

	public TalkArrayAdapter(Activity context, Talk[] talks) {
		super(context, R.layout.row_layout, talks);
		this.context = context;
		this.talks = talks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.row_layout, null, true);
		// Title
		TextView textView = (TextView) rowView.findViewById(R.id.title);
		textView.setText(this.talks[position].getTitle());
		// Speaker
		textView = (TextView) rowView.findViewById(R.id.speaker);
		textView.setText("(" + this.context.getString(R.string.by) + " "
				+ this.talks[position].getSpeaker() + ")");
		// // Up votes
		textView = (TextView) rowView.findViewById(R.id.up_votes);
		textView.setText(new Integer(this.talks[position].getUpVotes())
				.toString());
		// // Down votes
		textView = (TextView) rowView.findViewById(R.id.down_votes);
		textView.setText(new Integer(this.talks[position].getDownVotes())
				.toString());
		return rowView;
	}
}
