package rit.se356.phonesawand;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

/**
 * Implements the Android RecognitionListner
 * @author toffer
 *
 */
public class SpeechListener extends Activity implements RecognitionListener{
	
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

	@Override
	public void onResults(Bundle data) {
		ArrayList<String> magicWords = data.getStringArrayList(
				SpeechRecognizer.RESULTS_RECOGNITION);

		Log.d(" on results: ", magicWords.get(0));
		
	}

	@Override
	public void onRmsChanged(float arg0) {
		// TODO Auto-generated method stub
		
	}

}
