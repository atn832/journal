package com.thousandapps.journal.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.thousandapps.journal.ComposeActivity;
import com.thousandapps.journal.EventItemAdapter;
import com.thousandapps.journal.SummaryItemAdapter;
import com.thousandapps.journal.models.Event;
import com.thousandapps.journal.models.Summary;
import com.thousandapps.journal.models.Summary.Duration;
import com.thousandapps.journal.R;

public class SummaryListFragment extends Fragment{
	private ArrayList<Summary> summaries;
    private ArrayAdapter<Event> aSummaries;
    private ListView lvEvents;
	private Duration duration;
    
    public SummaryListFragment(Duration duration) {
    	this.duration = duration;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        summaries = new ArrayList<Summary>();
        aSummaries = new SummaryItemAdapter(getActivity(), summaries);
        populateList(aSummaries);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	final View v = inflater.inflate(R.layout.fragment_list, container, false);
    	lvEvents = (ListView) v.findViewById(R.id.lvItems);
    	lvEvents.setAdapter(aSummaries);
    	
    	lvEvents.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				SummaryItemAdapter a = (SummaryItemAdapter)arg0.getAdapter();
				Summary summary = (Summary)a.getItem(pos);
				
				// load compose activity
			    Intent intent = new Intent(v.getContext(), ComposeActivity.class);
			    String id = summary.getObjectId();
			    intent.putExtra("summaryId", id);
			    startActivity(intent);
			}
		});
    	/*
    	lvEvents.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("selected");
//				if (adapter instanceof EventItemAdapter) {
//					
//				}
//				else if (adapter instanceof SummaryItemAdapter) {
//					
//				}
//				else {
//					throw new IllegalStateException();
//				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
    		
		});
		*/
    	return v;
    }

    public void populateList(final ArrayAdapter a){
    	Date start = new Date(0);
    	Date end = new Date();
    	summaries.addAll(Summary.get(start, end, duration, false));
//    	Collections.sort(summaries, new MyComparator());
//    	a.notifyDataSetChanged();
    }
    
    private class MyComparator implements Comparator<Summary> {
		@Override
		public int compare(Summary arg0, Summary arg1) {
			return -1 * arg0.getStartDate().compareTo(arg1.getStartDate());
		}
    	
    }
}
