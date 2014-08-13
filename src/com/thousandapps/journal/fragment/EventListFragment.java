package com.thousandapps.journal.fragment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.thousandapps.journal.EventItemAdapter;
import com.thousandapps.journal.models.Event;
import com.thousandapps.journal.models.Summary;
import com.thousandapps.journal.R;

public class EventListFragment extends Fragment {
	private ArrayList<Event> events;
    private ArrayAdapter<Event> aEvents;
    private ListView lvEvents;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = new ArrayList<Event>();
        aEvents = new EventItemAdapter(getActivity(), events);
        populateList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.fragment_list, container, false);
    	lvEvents = (ListView) v.findViewById(R.id.lvItems);
    	lvEvents.setAdapter(aEvents);
    	return v;
    }
    
    public void populateList(){
    	Date start = new Date(0);
    	Date end = new Date();
    	ParseQuery<Event> eventQuery = ParseQuery.getQuery("Event");
		eventQuery.whereGreaterThanOrEqualTo("date", start);
		eventQuery.whereLessThanOrEqualTo("date", end);
		eventQuery.findInBackground(new FindCallback<Event>() {
		    public void done(List<Event> itemList, ParseException e) {
		        if (e == null) {
		            // Access the array of results here
		            String firstItemId = itemList.get(0).getObjectId();
		            Log.d("debug", firstItemId);
		            events.addAll(itemList);
		        } else {
		            Log.d("item", "Error: " + e.getMessage());
		        }
		    }
		});
    }
    
    private class MyComparator implements Comparator<Event> {
		@Override
		public int compare(Event arg0, Event arg1) {
			return -1 * arg0.getDate().compareTo(arg1.getDate());
		}
    	
    }
}
