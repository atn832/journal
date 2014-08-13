package com.thousandapps.journal.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.parse.ParseObject;
import com.thousandapps.journal.models.Summary.Duration;
	
@ParseClassName("Summary")
/**
 * Group of events by tag.
 * @author anhtuan
 *
 */
public class Summary extends ParseObject {
	public enum Duration {
		DAY, WEEK, MONTH, YEAR
	}
	
	public void setUser(ParseUser user) {
		put("owner", user);
	}
	
	public ParseUser getUser() {
		return getParseUser("owner");
	}

	public void setStartDate(Date date) {
		put("startDate", date);
	}
	
	public Date getStartDate() {
		return getDate("startDate");
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
	
	public void setDuration(Duration d) {
		put("duration", d.name());
	}
	
	public Duration getDuration() {
		return Duration.valueOf(getString("duration"));
	}

	public static List<Summary> get(final Date startDate, final Date endDate, final Duration duration,
			boolean autoGenerateSummaries) {
		ParseQuery<Summary> summaryQuery = ParseQuery.getQuery("Summary");
		summaryQuery.whereGreaterThanOrEqualTo("startDate", startDate);
		summaryQuery.whereLessThanOrEqualTo("startDate", endDate);
		summaryQuery.whereEqualTo("duration", duration.name());
		List<Summary> summaries = null;
		try {
			summaries = summaryQuery.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<Summary>();
		}
		/*
		// for each tag that does not have a summary, generate one so we can edit it later
		if (autoGenerateSummaries) {
			List<Summary> existingSummaries = summaries;
			// compute used tags
			final HashSet<Tag> usedTags = new HashSet<Tag>();
			for (Summary s : existingSummaries) {
				if (s.getTag() != null)
					usedTags.add(s.getTag());
			}
			
			// find all tags from daily or lower level summaries
			Duration shorterDuration = Duration.values()[duration.ordinal() - 1];
			// lower level is daily events
			List<Tag> tags = Tag.get(startDate, endDate, shorterDuration);
			// find missing tags
			HashSet<Tag> missingTags = new HashSet<Tag>();
			for (Tag t: tags) {
				if (!usedTags.contains(t))
					missingTags.add(t);
			}
			
			// create summaries for these
			for (Tag missingTag : missingTags) {
				Summary s = new Summary();
				s.setStartDate(startDate);
				s.setTag(missingTag);
				s.setDuration(duration);
//				s.setUser(ParseUser.getCurrentUser());
				s.saveInBackground();
			}
			
			summaries = Summary.get(startDate, endDate, duration, false);
		}*/
		return summaries;
	}
	
	public static Summary getById(String id) {
		ParseQuery<Summary> query = ParseQuery.getQuery(Summary.class); // Specify the object id
		try {
			return query.get(id);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
