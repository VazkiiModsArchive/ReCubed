/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:36:31 PM (GMT)]
 */
package vazkii.recubed.api.internal;

import java.io.Serializable;
import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import vazkii.recubed.api.ReCubedAPI;

public final class ServerData implements Serializable {

	public static final HashMap<String, Category> categories = new HashMap();
	
	public static void reset() {
		categories.clear();
	}
	
	public static void init() {
		for(String cat : ReCubedAPI.categories)
			registerCategory(cat);
	}
	
	public static void registerCategory(String name) {
		categories.put(name, new Category(name));
	}
	
	public static void onPlayerLogin(String name) {
		for(Category cat : categories.values()) {
			HashMap<String, PlayerCategoryData> data = cat.playerData;
			if(!data.containsKey(name))
				data.put(name, new PlayerCategoryData(name));
		}
	}
	
	public static void loadFromNBT(NBTTagCompound cmp) {
		
	}
	
	public static void writeToNBT(NBTTagCompound cmp) {
		
	}
	
}
