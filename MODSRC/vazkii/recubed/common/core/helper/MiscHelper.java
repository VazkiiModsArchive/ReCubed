/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 4:18:48 PM (GMT)]
 */
package vazkii.recubed.common.core.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public final class MiscHelper {

	public static String getEntityString(Entity entity) {
		return "entity." + EntityList.getEntityString(entity) + ".name";
	}
	
}
