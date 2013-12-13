/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:36:06 PM (GMT)]
 */
package vazkii.recubed.api;

import java.util.LinkedHashSet;
import java.util.Set;

public final class ReCubedAPI {
	
	public static final Set<String> categories = new LinkedHashSet();
	
	public static void registerCategory(String category) {
		categories.add(category);
	}

}
