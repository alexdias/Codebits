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

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class CodebitsActivity extends ListActivity {
	public static final String TAG = "CODEBITS";
	public static final String TALK_POSITION = "TALK_POSITION";
	private static Talk[] talks = null;

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

	/**
	 * Check if we're connected
	 * 
	 * @param context
	 * @return true if we're connected, false otherwise
	 */
	private static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (cm != null) {
			networkInfo = cm.getActiveNetworkInfo();
		}
		return networkInfo == null ? false : networkInfo.isConnected();
	}

	private void showConnectionError(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				context.getText(R.string.network_error) + "\n"
						+ context.getText(R.string.failed_load))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						CodebitsActivity.this.finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!isConnected(this)) {
			showConnectionError(this);
		} else {
			createView();
		}
	}

	private void createView() {
		setContentView(R.layout.callfortalks);
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

		try {
			String text = builder.toString();
			builder = new StringBuilder();
			JSONArray jsonArray = new JSONArray(text);
			CodebitsActivity.talks = new Talk[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				CodebitsActivity.talks[i] = new Talk(jsonArray.getJSONObject(i));
			}
		} catch (JSONException e) {
			Log.d(TAG, "EXCEPTION", e);
		}

		setListAdapter(new TalkArrayAdapter(this, talks));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(getApplicationContext(),
				TalkViewerActivity.class);
		intent.putExtra(CodebitsActivity.TALK_POSITION, position);
		startActivity(intent);
	}
}