package rit.se356.phonesawand;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button doMagic = (Button) findViewById(R.id.perform_magic);
		doMagic.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                	startRecordingMagic();
                else if (event.getAction() == MotionEvent.ACTION_UP)
                	stopRecordingMagic();
                return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * startRecordingMagic
	 * Called when the Cast Spell button is pressed down.
	 * Start recording user's motion and voice.
	 */
	public void startRecordingMagic() {
		Log.d("Pressed", "Start Recording Magic!");
	}
	
	/**
	 * stopRecordingMagic
	 * Called when the Cast Spell button is released
	 * Stop recording the user's motion and voice.
	 */
	public void stopRecordingMagic() {
		Log.d("Released", "Stop Recording Magic!");
	}

}
