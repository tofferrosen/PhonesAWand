package rit.se356.phonesawand;

import java.util.ArrayList;
import java.util.Timer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

/**
 * Wrapper around the Android RecognitionListner to enable recording the user's voice.
 * 
 * Purpose: Avoid the voice interface pop up provided by default voice listener
 * Function: Can start and stop recording voice, returning a String of the voice
 * 			 recorded
 * 
 * @author toffer
 *
 */
public class SpeechListener implements RecognitionListener, SensorEventListener{
	
	private SpeechRecognizer speech = null;		/* Android Speech Recognizer */
	private Context context = null;				/* Context of calling activity */
	MainActivity mainActivity = null;			/* Main Activity to call voiceFinished method when 
													recognitionlistener finishes */
	private Timer timer;
	private MotionRecordTask motionTask;
	
	public SpeechListener(Context c, MainActivity mainActivity){
		this.context = c;
		this.mainActivity = mainActivity;
	}
	
	/** 
	 * Start recording the user's voice
	 * DOES NOT STOP VOICE until stopRecordingVoice is called
	 */
	public void startRecordingVoice(){
		timer = new Timer();
		motionTask = new MotionRecordTask();
		//schedule motion task to record motion every 100 milliseconds
		timer.schedule(motionTask,0, 200);
		
		speech = SpeechRecognizer.createSpeechRecognizer(context);
		speech.setRecognitionListener(this);
		
		Intent intent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
		//intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
		  //      this.getPackageName());
		
		try {
			speech.startListening(intent); /* Start listening to user */
		} catch (ActivityNotFoundException a) {
			// device doesn't support speech to text
			Log.d("Error:", "listening to voice failed");
		}
	}
	
	
	/** 
	 * Stops recording voice 
	 * @return			String of the voice captured
	 */
	public void stopRecordingVoice(){
		speech.stopListening();
		//stop timer
		timer.cancel();
	}
	
	/* 
	 * Many not implemented, but required to wrap the Android RecognitionListener.
	 * Only onReceive implemented		
	 */
	
	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBufferReceived(byte[] arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPartialResults(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadyForSpeech(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when the Recognition Listener has received results.
	 * Calls voiceFinished method in the main activity with the voice 
	 * results as a string parameter.
	 */
	public void onResults(Bundle data) {
		ArrayList<String> magicWords = data.getStringArrayList(
				SpeechRecognizer.RESULTS_RECOGNITION);
		
		Log.d("SpeechListener results: ", magicWords.get(0));
		String voiceResults = magicWords.get(0);
		mainActivity.voiceFinished(voiceResults,motionTask.getXyzValues());
	}


	@Override
	public void onRmsChanged(float arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		motionTask.setXYZ(event.values);
		
	}

}
