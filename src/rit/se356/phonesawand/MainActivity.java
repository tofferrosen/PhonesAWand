package rit.se356.phonesawand;


import java.util.ArrayList;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

public class MainActivity extends Activity {

	protected static final int RESULT_SPEECH = 1;
	private SpeechRecognizer speech;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Create default spell
		Spell defaultSpell = new Spell();
		defaultSpell.spellName = "Default";
		defaultSpell.voice = "fireball";
		PersistService pervService = new PersistService(getBaseContext());
		pervService.saveSpell(defaultSpell);
		
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
		startRecordingMotion();
	}

	public void startRecordingVoice() {
		
		SpeechListener listener = new SpeechListener();
		
		speech = SpeechRecognizer.createSpeechRecognizer(this);
		speech.setRecognitionListener(listener);
	
		
		Intent intent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
		        this.getPackageName());
		
		try {
			speech.startListening(intent);
		} catch (ActivityNotFoundException a) {
			// device doesn't support speech to text
			Log.d("Error:", "listening to voice failed");
		}
	}
	
	public void startRecordingMotion() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		
	}
	
	public void onSensorChanged(SensorEvent event){
		Log.d("x-acceleration: ", Float.toString(event.values[0]));
		Log.d("y-acceleration: ", Float.toString(event.values[1]));
		
	}
	
	/**
	 * stopRecordingMagic
	 * Called when the Cast Spell button is released
	 * Stop recording the user's motion and voice.
	 */
	public void stopRecordingMagic() {
		Log.d("Released", "Stop Recording Magic!");
		
		// stop listening to voice commands
		speech.stopListening();
	}
	
	/**
	 * Opens the Grimoire spellbook 
	 * onClick handler for the Grimoire button
	 */
	public void gotoGrimoire(View view){
		Intent myIntent = new Intent(MainActivity.this, GrimoireActivity.class);
		MainActivity.this.startActivity(myIntent);
	}

}
