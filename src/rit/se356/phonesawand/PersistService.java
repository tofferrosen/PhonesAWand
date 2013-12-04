package rit.se356.phonesawand;

//import Spell.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class PersistService {

		private Context appContext;
		
		PersistService(Context c) {
			appContext = c;
		}
		
		/**
		 * Saves a spell to the internal directory.
		 * 
		 * @param toSave - The spell to save
		 */
		void saveSpell(Spell toSave) {
			String input = "";
//			ArrayList<Spell> tempList = new ArrayList<Spell>();
//			boolean isEqual = true;
//			for(Spell s: tempList){
//				isEqual = true;
//				if(s.motion.size() == toSave.motion.size()){
//					for(int i = 0; i < s.motion.size(); i++){
//						float[] check1 = s.motion.get(i);
//						float[] check2 = toSave.motion.get(i);
//						if(!check1.equals(check2)){
//							isEqual = false;
//							break;
//						}
//					}
//				} else{
//					isEqual = false;
//				}
//			}
//			
//			if(!isEqual){
				FileOutputStream fos;
			
				// Needs to be updated when spell changes
				input += toSave.voice + "#";
				input += toSave.toStringMotion() + "#";
				input += toSave.damage + "#";
				input += toSave.speed + "#";
				input += toSave.DoT + "#";
				input += toSave.effect + "#";
				input += toSave.type + "#";
				input += toSave.school;
			
				try {
					fos = appContext.openFileOutput("spells\\" + toSave.spellName, Context.MODE_PRIVATE);
					fos.write(input.getBytes());
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
//			}
		}
		
		/**
		 * Gets a spell from the internal directory. Filenames are the spellnames.
		 * 
		 * @param spellname - The name of the spell
		 * @return The spell
		 */
		Spell loadSpell(String spellname) {
			int ch;
			StringBuffer fileContent = new StringBuffer("");
			FileInputStream fis;
			try {
			    fis = appContext.openFileInput( "spells\\" + spellname );
			    try {
			        while( (ch = fis.read()) != -1)
			            fileContent.append((char)ch);
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			} catch (Exception e) {
			    e.printStackTrace();
			}

			String data = new String(fileContent);
			String[] params = data.split("#");
			//Spell internalSpell = new Spell();
			
			
			// Needs to be updated when spell changes
			//internalSpell.voice = params[0];
			String[] motionChunk = params[1].split("/");
			int mSize = motionChunk.length;
			ArrayList<float[]> motionList = new ArrayList<float[]>();
			for(int i = 0; i < mSize; i++){
				String coord = motionChunk[i];
				coord.replaceAll("\\s+", "");
				//System.out.println(coord);
				String[] coordArray = coord.split(",");
				float[]	floatArray = new float[3];
				for(int j= 0; j < 3; j++){
					floatArray[j] = Float.parseFloat(coordArray[j]);
				}
				motionList.add(floatArray);
			}
			
			Spell internalSpell = new Spell(spellname, params[0], motionList,
					Integer.parseInt(params[2]), Integer.parseInt(params[3]), 
					Integer.parseInt(params[4]), params[5], params[6], params[7]);
			//internalSpell.motion = motionMatrix;
			
			
			return internalSpell;
		}
		
		/**
		 * Gets all the spells from the directory.
		 * 
		 * @return The list of spells
		 */
		List<Spell> getSpells() {
			File spellsDir = new File(appContext.getFilesDir().getPath() + "spells\\");
			List<Spell> spellList = new ArrayList<Spell>();
			
			for (File f : spellsDir.listFiles()) {
				spellList.add(loadSpell(f.getName()));
			}
			
			return spellList;
		}
}
