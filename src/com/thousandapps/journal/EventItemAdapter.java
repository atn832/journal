package com.thousandapps.journal;

import java.text.SimpleDateFormat;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.thousandapps.journal.models.Event;
import com.thousandapps.journal.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventItemAdapter extends ArrayAdapter{
	 public EventItemAdapter(Context context, List<Event> events){
	        super(context, 0, events);
	    }

	 @Override
	    public View getView(int position, View convertView, final ViewGroup parent){
	        final Event event = (Event) getItem(position);
	        View v;
	        if(convertView ==null){
	            LayoutInflater inflater = LayoutInflater.from(getContext());
	            v = inflater.inflate(R.layout.event_item, parent, false);
	        }
	        else {
	            v = convertView;
	        }
	        
	        SimpleDateFormat dt1 = new SimpleDateFormat("MM-dd");
	        TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
	        tvDate.setText(dt1.format(event.getDate())+" ");
	        
	        TextView tvTagName = (TextView) v.findViewById(R.id.tvTagName);
	        tvTagName.setText(event.getTag() == null? "": event.getTag().getName());
	        
	        TextView tvEventContent = (TextView) v.findViewById(R.id.tvEventContent);
	        tvEventContent.setText(event.getContent());
	        return v;
	    }
}
