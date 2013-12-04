package rit.se356.phonesawand;


import java.util.ArrayList;
import java.util.Arrays;

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
import android.widget.Toast;

public class MainActivity extends Activity {

	protected static final int RESULT_SPEECH = 1;
	private SpeechListener listener = null;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private PersistService persistance = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get an instance of the SensorManager
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		// Create default spell
		Log.d("Notification", "App Directory: " + getBaseContext().getFilesDir().getPath());
		Spell defaultSpell = new Spell();
		defaultSpell.spellName = "Default";
		defaultSpell.voice = "fireball";
		persistance = new PersistService(getBaseContext());
		persistance.saveSpell(defaultSpell);
		Log.d("Notification", "# of spells: " + persistance.getSpells().size());
		
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
	 * Finds motion matches given an array of spells and a spell to match
	 * @param voiceMatches		An array of spells that matched voice
	 * @param spellToMatch		The spell to match
	 * @return					Returns the spell or null if no match found
	 */
	public Spell findMotionMatch(ArrayList<Spell> voiceMatches, ArrayList<Spell> allSpells){
		int range = 2;
		int perAcceptance = 60;
		
		for(Spell spellToMatch : allSpells){
			ArrayList<float[]> motionsToMatch = spellToMatch.motion;

			// check all possible spells
			for(Spell spell: voiceMatches){
				int matches = 0;
				int total = 0;

				// check all the motion values
				for(float[] motion: spell.motion){
					// check for match
					for(float[] motionToMatch: motionsToMatch){
						if( (motion[0] > motionToMatch[0] - range) && (motion[1] < motionToMatch[1] + range)){
							matches++;
						}
						total++;
					}
				}

				// does it pass our acceptance criteria?
				if( (matches/total * 100) >= perAcceptance){
					return spellToMatch;
				}
			}
		}
		return null; // Not found
	}
	
	/**
	 * This method is called by the SpeechListener when done listening for voice
	 * @param voiceResult			string of the voice results captured by SpeechListener
	 */
	public void voiceFinished(String voiceResult, ArrayList<float[]> motionResult){
		Log.d("MAIN: ", voiceResult);
		Log.d("MAIN: ", Arrays.deepToString(motionResult.toArray()));
		
		/* Check to see if it matches a spell */
		ArrayList<Spell> spells = (ArrayList<Spell>) persistance.getSpells();
		ArrayList<Spell> voiceMatches = new ArrayList<Spell>();
		for(Spell spell: spells){
			if(spell.voice == voiceResult){
				voiceMatches.add(spell); // yay
			} 
		}
		
		if(voiceMatches.size() == 0){
			Toast toast = Toast.makeText(this.getApplicationContext(), "spell not found", Toast.LENGTH_SHORT);
			toast.show();
		} else {
			
			//Toast toast = Toast.makeText(this.getApplicationContext(), "Looking for a motion match", 10);
			Toast toast = null;
			Spell matchSpell = this.findMotionMatch(voiceMatches, spells);
			
			if(matchSpell == null){
				toast = Toast.makeText(this.getApplicationContext(), "spell not found" ,Toast.LENGTH_SHORT);
			} else{
				toast = Toast.makeText(this.getApplicationContext(), "Casting spell " + matchSpell.spellName,Toast.LENGTH_LONG);
			}
			toast.show();
		}
		
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

	/**
	 * Starts recording voice.
	 */
	public void startRecordingVoice() {
		listener = new SpeechListener(this.getApplicationContext(), this);
		mSensorManager.registerListener(listener, mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
		listener.startRecordingVoice();
	}
	
	/**
	 * stopRecordingMagic
	 * Called when the Cast Spell button is released
	 * Stop recording the user's motion and voice.
	 * @throws InterruptedException 
	 */
	public void stopRecordingMagic() {
		Log.d("Released", "Stop Recording Magic!");
		//stop listening for motion
		mSensorManager.unregisterListener(listener);
		// stop listening to voice commands
		listener.stopRecordingVoice();
	}
	
	/**
	 * Opens the Grimoire spellbook 
	 * onClick handler for the Grimoire button
	 */
	public void gotoGrimoire(View view){
		Intent myIntent = new Intent(this, GrimoireActivity.class);
		startActivity(myIntent);
	}

}
