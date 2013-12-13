/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:36:47 PM (GMT)]
 */
package vazkii.recubed.api.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;

public final class PlayerCategoryData implements Serializable {

	public final HashMap<String, Integer> stats = new HashMap();
	public final String name;
	
	public PlayerCategoryData(String name) {
		this.name = name;
	}
	
	public void loadFromNBT(NBTTagCompound cmp) {
		stats.clear();
		
		Collection<NBTBase> tags = cmp.getTags();
		for(NBTBase nbt : tags) {
			if(nbt instanceof NBTTagInt) {
				String name = nbt.getName();
				int val = ((NBTTagInt) nbt).data;
				stats.put(name, val);
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound cmp) {
		for(String s : stats.keySet())
			cmp.setInteger(s, stats.get(s));
	}
}
