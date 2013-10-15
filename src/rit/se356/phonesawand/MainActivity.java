package rit.se356.phonesawand;


import java.util.ArrayList;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.speech.RecognizerIntent;

public class MainActivity extends Activity {

	protected static final int RESULT_SPEECH = 1;
	
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
		startRecordingVoice();
	}

	public void startRecordingVoice() {
		Intent intent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
		
		try {
			startActivityForResult(intent, RESULT_SPEECH);
		} catch (ActivityNotFoundException a) {
			// device doesn't support speech to text
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case RESULT_SPEECH: {
			if ( resultCode == RESULT_OK && null != data ) {
				ArrayList<String> magicWords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				Log.d(" VOICE SIZE: ", "" + magicWords.size());
				Log.d(" VOICE: ", magicWords.get(0));
			}
			break;
		}
		}
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
