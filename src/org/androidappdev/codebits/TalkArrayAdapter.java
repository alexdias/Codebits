/*  
 *  Codebits - Comment the talk proposals.
 *  Copyright (C) 2011 Henrique Rocha <hmrocha@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.androidappdev.codebits;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TalkArrayAdapter extends ArrayAdapter<Talk> {
	private Activity context;

	static class ViewHolder {
		TextView title;
		TextView speaker;
		TextView upVotes;
		TextView downVotes;
		ImageView rated;
	}

	public TalkArrayAdapter(Activity context, Collection<Talk> talks) {
		super(context, R.layout.row_layout, new ArrayList<Talk>(talks));
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder will buffer the access to the
		// individual fields of the row layout.
		ViewHolder holder = null;
		// Recycle existing view if passed as parameter.
		// This will save memory and time on Android.
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = this.context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_layout, null, true);
			holder = new ViewHolder();
			holder.title = (TextView) rowView.findViewById(R.id.title);
			holder.speaker = (TextView) rowView.findViewById(R.id.speaker);
			holder.upVotes = (TextView) rowView.findViewById(R.id.up_votes);
			holder.downVotes = (TextView) rowView.findViewById(R.id.down_votes);
			holder.rated = (ImageView) rowView.findViewById(R.id.rated);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		Talk talk = getItem(position);
		holder.title.setText(talk.getTitle());
		holder.speaker.setText("(" + this.context.getString(R.string.by) + " "
				+ talk.getSpeaker() + ")");
		holder.upVotes.setText(new Integer(talk.getUpVotes())
				.toString());
		holder.downVotes.setText(new Integer(talk
				.getDownVotes()).toString());
		if (talk.isRated()) {
			holder.rated.setVisibility(View.VISIBLE);
			setRatedImage(context, talk, holder);
		}
		return rowView;
	}

	private void setRatedImage(Context context, Talk talk, ViewHolder holder) {
		Resources res = context.getResources();
		if (talk.getRated().equals(Talk.UP)) {
			holder.rated.setImageDrawable(res.getDrawable(R.drawable.thumbsup));
		}
		else if (talk.getRated().equals(Talk.DOWN)) {
			holder.rated.setImageDrawable(res.getDrawable(R.drawable.thumbsdown));			
		}		
	}
}
