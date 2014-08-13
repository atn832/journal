package com.thousandapps.journal;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.thousandapps.journal.models.Event;
import com.thousandapps.journal.models.Summary;
import com.thousandapps.journal.models.Summary.Duration;
import com.thousandapps.journal.R;

public class SummaryItemAdapter extends ArrayAdapter{
	public SummaryItemAdapter(Context context, List<Summary> summaries){
		super(context, 0, summaries);
	}
	
	@Override
	public View getView(int position, View convertView, final ViewGroup parent){
		final Summary summary = (Summary) getItem(position);
		View v;
		if(convertView ==null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.summary_item, parent, false);
		}
		else {
			v = convertView;
		}

		TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
		SimpleDateFormat dt1 = new SimpleDateFormat("MMM");
        
		String date = summary.getDuration() == Duration.MONTH? 
				dt1.format(summary.getStartDate())
				: "" + (1900 + summary.getStartDate().getYear());
		tvDate.setText(date);

		TextView tvTagName = (TextView) v.findViewById(R.id.tvTagName);
		tvTagName.setText(summary.getTag() == null? "": summary.getTag().getName());

		TextView tvCount = (TextView) v.findViewById(R.id.tvCount);
		tvCount.setText(" (" + (int)(Math.random() * 8 + 2) + ")");

		TextView tvEventContent = (TextView) v.findViewById(R.id.tvContent);
		tvEventContent.setText(summary.getContent());

		return v;
	}
}
