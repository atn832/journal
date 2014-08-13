package com.thousandapps.journal.models;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseQuery;
import com.thousandapps.journal.models.Summary.Duration;

@ParseClassName("Tag")
public class Tag extends ParseObject {
	public void setName(String name) {
		put("name", name);
	}
	
	public String getName() {
		try {
			return fetchIfNeeded().getString("name");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a Tag instance that has the matching name or creates it if missing (optional).
	 * Returns null if name is null;
	 * @param name
	 * @param createIfMissing
	 * @return
	 */
	public static Tag getBy(String name, boolean createIfMissing) {
		if (name == null)
			return null;
		ParseQuery<Tag> query = ParseQuery.getQuery("Tag");
		query.whereEqualTo("name", name);
		List<Tag> tags;
		try {
			tags = query.find();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		if (tags.size() > 0)
			return tags.get(0);
		if (createIfMissing) {
			Tag t = new Tag();
			t.setName(name);
			try {
				t.save();
				return t;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		// not found
		return null;
	}
	
	/**
	 * Returns the tags used for a certain type of events/summaries
	 * @param startDate start date, inclusive
	 * @param endDate end date, inclusive
	 * @param findCallback fallback that will be returned the tags used
	 */
	public static List<Tag> get(Date startDate, Date endDate, Duration duration) {
		final HashSet<Tag> tags = new HashSet<Tag>();
		if (duration == Duration.DAY) {
			ParseQuery<Event> eventQuery = ParseQuery.getQuery("Event");
			eventQuery.whereGreaterThanOrEqualTo("date", startDate);
			eventQuery.whereLessThanOrEqualTo("date", endDate);
			List<Event> events;
			try {
				events = eventQuery.find();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return new ArrayList<Tag>();
			}
			for (Event e : events) {
				if (e.getTag() != null)
					tags.add(e.getTag());
			}
		}
		else {
			List<Summary> summaries = Summary.get(startDate, endDate, duration, false);
			for (Summary s : summaries) {
				if (s.getTag() != null)
					tags.add(s.getTag());
			}
		}
		return new ArrayList<Tag>(tags);
	}
}
