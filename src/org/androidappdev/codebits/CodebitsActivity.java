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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class CodebitsActivity extends ListActivity {
	public static final String TAG = "CODEBITS";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"https://services.sapo.pt/Codebits/calltalks");
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

		Talk[] talks = null;
		try {
			String text = builder.toString();
			builder = new StringBuilder();
			JSONArray jsonArray = new JSONArray(text);
			talks = new Talk[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				talks[i] = new Talk(jsonArray.getJSONObject(i));
			}
		} catch (JSONException e) {
			Log.d(TAG, "EXCEPTION", e);
		}

		setListAdapter(new TalkArrayAdapter(this, talks));
	}
}