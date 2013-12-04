package rit.se356.phonesawand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;

import android.util.Log;

public class MotionRecordTask extends TimerTask{

	private ArrayList<float[]> xyzValues = new ArrayList<float[]>();
	private float[] xyz = new float[3];
	
	public MotionRecordTask(){
		
	}
	
	@Override
	public void run() {
		xyzValues.add(xyz);
		Log.d("xyz: ", Arrays.toString(xyz));
	}

	public void setXYZ(float[] xyz) {
		this.xyz = xyz;
	}
	
	public ArrayList<float[]> getXyzValues(){
		return xyzValues;
	}
}
