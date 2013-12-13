/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:36:40 PM (GMT)]
 */
package vazkii.recubed.api.internal;

import java.io.Serializable;
import java.util.HashMap;

public final class Category implements Serializable {

	public final HashMap<String, PlayerCategoryData> playerData = new HashMap();
	public final String name;
	
	public Category(String name) {
		this.name = name;
	}
	
	public static void writeFromNBT() {
		
	}
	
	public static void writeToNBT() {
		
	}
	
}
