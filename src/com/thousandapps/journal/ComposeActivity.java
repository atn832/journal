package com.thousandapps.journal;

import java.util.Date;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.thousandapps.journal.models.Event;
import com.thousandapps.journal.models.Summary;
import com.thousandapps.journal.models.Tag;
import com.thousandapps.journal.models.Event.Time;
import com.thousandapps.journal.models.Summary.Duration;
import com.thousandapps.journal.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ComposeActivity extends Activity {
	
	private ActionBar actionBar;
	private EditText tagEdit;
	private EditText contentEdit;
	
	public void save(View view) {
		// Get the message from the intent
	    Intent intent = getIntent();
	    
	    Tag tag = Tag.getBy(tagEdit.getText().toString(), true);
	    String content = contentEdit.getText().toString();
	    
	    String eventId = intent.getStringExtra("eventId");
	    String summaryId = intent.getStringExtra("summaryId");
	    // TODO: should make those two methods part of Displayable
	    if (eventId != null) {
	    	Event event = Event.getById(eventId);
	    	event.setContent(content);
	    	event.setTag(tag);
	    	event.setTime(Time.MORNING);
	    	event.setDate(new Date());
	    	event.setUser(ParseUser.getCurrentUser());
	    	try {
				event.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    else if (summaryId != null) {
	    	Summary summary = Summary.getById(summaryId);
	    	summary.setContent(content);
	    	summary.setTag(tag);
	    	try {
				summary.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    else {
	    	throw new IllegalStateException();
	    }
	    finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		actionBar = getActionBar();
		actionBar.hide();
		
		tagEdit = (EditText) findViewById(R.id.tag_edit);
		contentEdit = (EditText) findViewById(R.id.event_description);

		// Get the message from the intent
		Intent intent = getIntent();
	    String eventId = intent.getStringExtra("eventId");
	    String summaryId = intent.getStringExtra("summaryId");
	    // TODO: should make those two methods part of Displayable
	    if (eventId != null) {
	    	Event event = Event.getById(eventId);
	    	if (event.getTag() != null)
	    		tagEdit.setText(event.getTag().getName());
	    	if (event.getContent() != null)
	    		contentEdit.setText(event.getContent());
	    }
	    else if (summaryId != null) {
	    	Summary summary = Summary.getById(summaryId);
	    	if (summary.getTag() != null)
	    		tagEdit.setText(summary.getTag().getName());
	    	if (summary.getContent() != null)
	    		contentEdit.setText(summary.getContent());
	    }
	    else {
	    	throw new IllegalStateException();
	    }
	    Selection.setSelection(tagEdit.getText(), tagEdit.getText().length());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
