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

public class MainActivity extends Activity {

	protected static final int RESULT_SPEECH = 1;
	private SpeechListener listener = null;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Create default spell
		Log.d("Notification", "App Directory: " + getBaseContext().getFilesDir().getPath());
		Spell defaultSpell = new Spell();
		defaultSpell.spellName = "Default";
		defaultSpell.voice = "fireball";
		PersistService pervService = new PersistService(getBaseContext());
		pervService.saveSpell(defaultSpell);
		Log.d("Notification", "# of spells: " + pervService.getSpells().size());
		
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
	 * This method is called by the SpeechListener when done listening for voice
	 * @param voiceResult			string of the voice results captured by SpeechListener
	 */
	public void voiceFinished(String voiceResult){
		Log.d("MAIN: ", voiceResult);
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

	/**
	 * Starts recording voice.
	 */
	public void startRecordingVoice() {
		listener = new SpeechListener(this.getApplicationContext(), this); 
		listener.startRecordingVoice();
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
	 * @throws InterruptedException 
	 */
	public void stopRecordingMagic() {
		Log.d("Released", "Stop Recording Magic!");
		
		// stop listening to voice commands
		listener.stopRecordingVoice();
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
