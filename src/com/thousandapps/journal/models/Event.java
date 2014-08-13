package com.thousandapps.journal.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.thousandapps.journal.models.Summary.Duration;

/**
 * @author linz
 *
 */
@ParseClassName("Event")
public class Event extends ParseObject {
	public enum Time {
		MORNING, AFTERNOON, EVENING, NIGHT, ALL_DAY
	}
	
	public void setUser(ParseUser user) {
		put("owner", user);
	}
	
	public ParseUser getUser() {
		return getParseUser("owner");
	}
	
	public void setDate(Date date) {
		put("date", date);
	}
	
	public Date getDate() {
		return getDate("date");
	}
	
	public void setTime(Time time) {
		put("time", time.name());
	}
	
	public Time getTime() {
		return Time.valueOf(getString("time"));
	}
	
	public void setTag(Tag tag) {
		if (tag == null)
			return;
		put("tag", tag);
	}
	
	public Tag getTag() {
		return (Tag)getParseObject("tag");
	}
	
	public void setContent(String content) {
		put("content", content);
	}
	
	public String getContent() {
		return getString("content");
	}

	
	public static Event getById(String id) {
		ParseQuery<Event> query = ParseQuery.getQuery(Event.class); // Specify the object id
		try {
			return query.get(id);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}