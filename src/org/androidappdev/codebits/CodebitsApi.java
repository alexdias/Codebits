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
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Methods of the Codebits API https://services.sapo.pt/Codebits
 * 
 * @author Henrique Rocha
 */
public class CodebitsApi {
	public static final String TAG = "CodebitsApi";

	public static String getToken(String user, String password) {
		try {
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(
					"https://services.sapo.pt/Codebits/gettoken?" + "user="
							+ user + "&" + "password=" + password);
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject jsonObject = new JSONObject(builder.toString());
			return jsonObject.getString("token");
			
		} catch (JSONException e) {
			Log.d(TAG, "JSONException", e);
		} catch (Exception e) {
			Log.d(TAG, "Exception", e);
		}
		return null;
	}
}
