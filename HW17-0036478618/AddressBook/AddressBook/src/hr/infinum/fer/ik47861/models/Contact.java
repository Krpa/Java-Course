package hr.infinum.fer.ik47861.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Class representing a single contact.
 * Class holds private strings: name, phone, email, note and a link to facebook profile
 * @author Ivan Krpelnik
 *
 */
public class Contact {

	private String name;
	private String phone;
	private String email;
	private String note;
	private String facebookProfile;
	
	public void fillFromJSONObject(JSONObject jsonObject) {
		try {
			this.name = jsonObject.getString("name");
			this.phone = jsonObject.getString("phone");
			this.email = jsonObject.getString("email");
			this.note = jsonObject.getString("note");
			this.facebookProfile = jsonObject.getString("facebook_profile");
		} catch (JSONException e) {
			Log.e("CONTACT", "Error while parsing jsonObject: " + jsonObject + " " + e.getMessage());
		}
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFacebookProfile() {
		return facebookProfile;
	}

	public void setFacebookProfile(String facebookProfile) {
		if(facebookProfile.isEmpty()) {
			this.facebookProfile = "https://facebook.com";
		} else {
			this.facebookProfile = facebookProfile;
		}
	}
	
}
