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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CodebitsActivity extends ListActivity {
	public static final String TAG = "CODEBITS";
	public static final String TALK_POSITION = "TALK_POSITION";
	private static Talk[] talks = null; // TODO: refactor to stop needing this array

	static ArrayList<Talk> approvedTalks = new ArrayList<Talk>();
	static ArrayList<Talk> ratedTalks = new ArrayList<Talk>();
	static ArrayList<Talk> unratedTalks = new ArrayList<Talk>();
	
	/**
	 * Talk details
	 * 
	 * @param position
	 *            position where the talk was in the list
	 * @return a Talk object with the talk details
	 */
	public static Talk getTalk(int position) {
		return talks[position];
	}

	public static Talk[] getTalks() {
		return talks;
	}


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createView();

	}

	private void createView() {
		setContentView(R.layout.callfortalks);
		String token = getIntent().getStringExtra("TOKEN");
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		String url = "https://services.sapo.pt/Codebits/calltalks";
		if (token != null) {
			url += "?token=" + token;
		}
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
			Log.d(TAG, "EXCEPTION", e);
		}

		try {
			String text = builder.toString();
			builder = new StringBuilder();
			JSONArray jsonArray = new JSONArray(text);
			CodebitsActivity.talks = new Talk[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				Talk t = new Talk(jsonArray.getJSONObject(i));
				CodebitsActivity.talks[i] = t;
				if (t.isApproved()) {
					approvedTalks.add(t);
				}
				else if (t.isRated()) {
					ratedTalks.add(t);
				}
				else {
					unratedTalks.add(t);
				}
			}
		} catch (JSONException e) {
			Log.d(TAG, "EXCEPTION", e);
		}
		Intent intent = new Intent(getApplicationContext(), 
				CodebitsTabActivity.class);
		startActivity(intent);
	}

}