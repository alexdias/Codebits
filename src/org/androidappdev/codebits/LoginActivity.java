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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private static final String TAG = "LoginActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!isConnected(this)) {
			showConnectionError(this);
		} else {
			createView();
		}
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
						LoginActivity.this.finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void createView() {
		setContentView(R.layout.login);

		// "ENTER WITHOUT LOGIN" button.
		Button b = (Button) findViewById(R.id.nologin);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						CodebitsActivity.class);
				startActivity(intent);
			}
		});

		// "LOGIN" button.
		b = (Button) findViewById(R.id.login);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText editText = (EditText) findViewById(R.id.email);
				String email = editText.getText().toString();
				Log.d(TAG, "email: " + email);
				editText = (EditText) findViewById(R.id.password);
				String password = editText.getText().toString();
				Log.d(TAG, "password: " + password);
				String token = getToken(email, password);
				Log.d(TAG, "token: " + token);
				if (token != null) {
					Intent intent = new Intent(getApplicationContext(),
							CodebitsActivity.class);
					intent.putExtra("TOKEN", token);
					startActivity(intent);
				}
				else {
					Toast.makeText(getApplicationContext(), "Login Failed", 3000).show();
				}
			}
		});
	}


	private String getToken(String user, String password) {
		return CodebitsApi.getToken(user, password);
	}
}
