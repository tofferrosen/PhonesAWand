package rit.se356.phonesawand;

import java.util.ArrayList;

/**
 * 
 * @author Nicholas Weller
 *
 */
// wow
// amazing
// so stubbed
// so doge
// such comments
public class Spell implements Cloneable{	
	protected String spellName;
	protected String voice;
	protected ArrayList<float[]> motion;
	protected int damage;
	protected int speed;
	protected int DoT;
	protected String effect;
	protected String type;
	protected String school;
	//to be implemented later
	//this is the abstract number which
	//is divvied up based on the values
	//of other components
	protected int spellPower;
	
	public Spell(String name, String v, ArrayList<float[]> m, int d, int sp,
			int dot, String e, String t, String s){
		
		spellName = name;
		voice = v;
		motion = m;
		damage = d;
		speed = sp;
		DoT = dot;
		effect = e;
		type = t;
		school = s;
		
	}
	
	public Spell(){}
	
	public Spell cloneSpell(){
		Spell copiedSpell = this;
		copiedSpell.setSpellName("Copy of " + this.spellName);
		return copiedSpell;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}


	public void setVoice(String voice) {
		this.voice = voice;
	}


	public void setMotion(ArrayList<float[]> motion) {
		this.motion = motion;
	}


	public void setDamage(int damage) {
		this.damage = damage;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public void setDoT(int doT) {
		DoT = doT;
	}


	public void setEffect(String effect) {
		this.effect = effect;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setSchool(String school) {
		this.school = school;
	}


	public void setSpellPower(int spellPower) {
		this.spellPower = spellPower;
	}
	
	public String toStringMotion(){
		String motionString = "";
		for(float[] s: motion){
			int count = 0;
			for(Float st: s){
				motionString += st;
				count++;
				if(count % 3 == 0){
					motionString += "/";
				}else{
					motionString += ",";
				}
			}
		}
		return motionString;
	}
	
}
