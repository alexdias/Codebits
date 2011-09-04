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
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

public class CodebitsActivity extends ListActivity {
	public static final String TAG = "CODEBITS";
	public static final String TALK_ID = "TALK_ID";

	static ArrayList<Talk> approvedTalks = new ArrayList<Talk>();
	static ArrayList<Talk> ratedTalks = new ArrayList<Talk>();
	static ArrayList<Talk> unratedTalks = new ArrayList<Talk>();

	private boolean talksLoaded = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createView();
	}

	private void createView() {
		setContentView(R.layout.callfortalks);
		String token = getIntent().getStringExtra("TOKEN");
		if (!this.talksLoaded) {
			List<Talk> talks = CodebitsApi.listSubmissions(token);
			for (Talk t : talks) {
				if (t.isApproved()) {
					approvedTalks.add(t);
				} else if (t.isRated()) {
					ratedTalks.add(t);
				} else {
					unratedTalks.add(t);
				}
			}
			talks = null; // we don't need the talks list anymore.
			this.talksLoaded = true;
		}
		Intent intent = new Intent(getApplicationContext(),
				CodebitsTabActivity.class);
		startActivity(intent);
	}

	public static Talk getTalk(String id) {
		Talk result = null;
		result = findTalk(unratedTalks, id);
		if (result == null) {
			result = findTalk(ratedTalks, id);
		}
		if (result == null) {
			result = findTalk(approvedTalks, id);			
		}
		return result;
	}

	private static Talk findTalk(List<Talk> talks, String id) {
		for (Talk talk : talks) {
			if (talk.getId().equals(id)) {
				return talk;
			}
		}
		return null;
	}
}