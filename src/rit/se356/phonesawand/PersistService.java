package rit.se356.phonesawand;

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
			FileOutputStream fos;
			
			// Needs to be updated when spell changes
			input += toSave.voice + "#";
			input += toSave.motion;
			
			try {
				fos = appContext.openFileOutput(toSave.spellName, Context.MODE_PRIVATE);
				fos.write(input.getBytes());
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			    fis = appContext.openFileInput( spellname );
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
			Spell internalSpell = new Spell();
			
			// Needs to be updated when spell changes
			internalSpell.voice = params[0];
			internalSpell.motion = params[1];
			
			return internalSpell;
		}
		
		/**
		 * Gets all the spells from the directory.
		 * 
		 * @return The list of spells
		 */
		List<Spell> getSpells() {
			File[] filesList = appContext.getFilesDir().listFiles();
			List<Spell> spellList = new ArrayList<Spell>();
			
			for (File f : filesList) {
				spellList.add(loadSpell(f.getName()));
			}
			
			return new ArrayList<Spell>();
		}
}