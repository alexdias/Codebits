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

import org.json.JSONException;
import org.json.JSONObject;

public class Talk {
	
	/** Title */
	private String title;
	
	/** Speaker */
	private String speaker;

	/** Number of up votes */
	private int upVotes;

	/** Number of down votes */
	private int downVotes;

	/** Description (abstract) */
	private String description;

	/**
	 * Create a Talk from a JSON object.
	 * @param jsonObject the JSON object with talk fields
	 * @throws JSONException
	 */
	public Talk(JSONObject jsonObject) throws JSONException {
		this.title = jsonObject.getString("title");
		this.speaker = jsonObject.getString("user");
		this.upVotes = Integer.valueOf(jsonObject.getString("up"));
		this.downVotes = Integer.valueOf(jsonObject.getString("down"));
		this.description = jsonObject.getString("description");
	}
	
	/**
	 * Title
	 * @return talk title.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Speaker
	 * @return the name of the speaker
	 */
	public String getSpeaker() {
		return this.speaker;
	}

	/**
	 * Up Votes
	 * @return number of up votes
	 */
	public int getUpVotes() {
		return this.upVotes;
	}

	/**
	 * Down Votes
	 * @return number of down votes
	 */
	public int getDownVotes() {
		return this.downVotes;
	}

	/**
	 * Description (abstract)
	 * @return talk's description (abstract)
	 */
	public String getDescription() {
		return this.description;
	}

}
