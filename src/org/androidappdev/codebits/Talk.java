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

}
