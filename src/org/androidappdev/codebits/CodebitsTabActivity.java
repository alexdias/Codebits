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

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost;

public class CodebitsTabActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources();
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, UnratedActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("unrated")
				.setIndicator(res.getString(R.string.unrated))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, RatedActivity.class);
		spec = tabHost
				.newTabSpec("rated")
				.setIndicator(res.getString(R.string.rated))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ApprovedActivity.class);
		spec = tabHost
				.newTabSpec("approved")
				.setIndicator(res.getString(R.string.approved))
				.setContent(intent);
		tabHost.addTab(spec);

		// This is a hack for now
		tabHost.getTabWidget().getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(150, 50));
	    tabHost.getTabWidget().getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(150, 50));
	    tabHost.getTabWidget().getChildAt(2).setLayoutParams(new LinearLayout.LayoutParams(150, 50));
	    
	}
}
