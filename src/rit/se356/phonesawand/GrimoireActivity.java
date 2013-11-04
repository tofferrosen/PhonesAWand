package rit.se356.phonesawand;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;


public class GrimoireActivity extends Activity {
	
	private PersistService persistance = null;
	SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grimoire);
		
		/*persistance = new PersistService(this.getBaseContext());
		
		// Create a progress bar to display while lists loads
		ProgressBar progressBar = new ProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);
        
        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar); */
        
        // TODO
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
