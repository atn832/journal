package com.thousandapps.journal;

import java.util.Date;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.thousandapps.journal.fragment.EventListFragment;
import com.thousandapps.journal.fragment.SummaryListFragment;
import com.thousandapps.journal.models.Event;
import com.thousandapps.journal.models.Summary;
import com.thousandapps.journal.models.Tag;
import com.thousandapps.journal.models.Summary.Duration;
import com.thousandapps.journal.R;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
 
    private ViewPager viewPager;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Daily", "Monthly", "Yearly" };
	FragmentPagerAdapter adapterViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "BF5dEJkCTT8ilrAsrYsAQWRqHEp6RV5FA5kdYtzi", "MVNc6hn457IioGBZDRcbi43pb4jQ2L08uGseCX32");
        testBackEnd();
		
		setContentView(R.layout.activity_main);
		ParseObject.registerSubclass(Event.class);
		ParseObject.registerSubclass(Summary.class);
		ParseObject.registerSubclass(Tag.class);
		adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.vpPager);
		viewPager.setAdapter(adapterViewPager);
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
        // Adding Tabs
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View tabView = inflater.inflate(R.layout.custom_tab, null);
        tabView.setBackgroundColor(0xfe6968);
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this)
            );
        }
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
	    getMenuInflater().inflate( R.menu.main, menu );
	    View v = (View) menu.findItem(R.id.menu_compose).getActionView();
	    EditText mCompose = (EditText) v.findViewById(R.id.etComposeBar );
	    mCompose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Always use a TextKeyListener when clearing a TextView to prevent android
                // warnings in the log
                	Intent i = new Intent(MainActivity.this, ComposeActivity.class);
                	// new event
                	Event e = new Event();
                	try {
						e.save();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	i.putExtra("eventId", e.getObjectId());
            		startActivity(i);
            }
        });
	    return true;
	}
	
	public static class MyPagerAdapter extends FragmentPagerAdapter {
		public MyPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }
	 
	    @Override
	    public Fragment getItem(int index) {
	 
	        switch (index) {
	        case 0:
	            // Top Rated fragment activity
	            return new EventListFragment();
	        case 1:
	            // Games fragment activity
	            return new SummaryListFragment(Summary.Duration.MONTH);
	        case 2:
	            // Movies fragment activity
	            return new SummaryListFragment(Summary.Duration.YEAR);
	        }
	 
	        return null;
	    }
	 
	    @Override
	    public int getCount() {
	        // get item count - equal to number of tabs
	        return 3;
	    }
	 
	    }


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		 viewPager.setCurrentItem(tab.getPosition());
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}
	
	private void testBackEnd() {

		/*
		ParseUser u = new ParseUser();
		u.setUsername("andrew");
		u.setPassword("andrew2014");
		try {
			u.signUp();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		try {
			ParseUser.logIn("andrew", "andrew2014");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		ParseUser.logInInBackground("andrew", "andrew2014", new LogInCallback() {
//			@Override
//			public void done(ParseUser u, ParseException ex) {
//				/*
//				Tag t = new Tag();
//				t.setName("jog");
//				t.saveInBackground();
//				String[] contents = new String[] {"2mi", "3mi", "25min", "35min", "4mi"};
//				for (int i = 0 ; i < 50; i++) {
//					Event e = new Event();
//					e.setUser(u);
//					e.setDate(new Date(113, (int)(Math.random() * 12), (int)(Math.random() * 30)));
//					e.setContent(contents[(int)(Math.random() * contents.length)]);
//					e.setTag(t);
//					e.setTime(Event.Time.EVENING);
//					e.saveInBackground();
//				}
//				*/
//				for (int i = 0; i < 10; i++) {
//					Tag t = Tag.getBy("jog", false);
//					Summary s = new Summary();
//					s.setUser(u);
//					s.setTag(t);
//					s.setDuration(Summary.Duration.MONTH);
//					s.setStartDate(new Date(113, (int)(Math.random() * 12), (int)(Math.random() * 30)));
//					s.setContent("can jog " + (int)(2 + Math.random() * 2) + " more miles");
//					s.saveInBackground();
//				}
//			}
//		});
//		
		
		/*
		 * Retrieve objects. Implement static utility getters
		 */
		
		/*
		Event.get(date, Duration.MONTH, new FindCallback<Event>() {
		public void done(List<Event> itemList, ParseException e) { if (e == null) {
			// Access the array of results here
			String firstItemId = itemList.get(0).getObjectId(); Toast.makeText(TodoItemsActivity.this, firstItemId, Toast.LENGTH_SHORT).show();
			} else {
			Log.d("item", "Error: " + e.getMessage());
			} }
		}););
		 */
	}
}

