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
import java.util.Set;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;

public final class PlayerCategoryData {

	public final HashMap<String, Integer> stats = new HashMap();
	public final String name;

	public PlayerCategoryData(String name) {
		this.name = name;
	}

	public int getTotalValue() {
		int val = 0;
		for(int i : stats.values())
			val += i;

		return val;
	}

	public void loadFromNBT(NBTTagCompound cmp) {
		stats.clear();

		Set<String> names = cmp.func_150296_c();
		for(String name : names) {
			NBTBase nbt = cmp.getTag(name);
			if(nbt instanceof NBTTagInt) {
				int val = ((NBTTagInt) nbt).func_150287_d();
				stats.put(name, val);
			}
		}
	}

	public void writeToNBT(NBTTagCompound cmp) {
		for(String s : stats.keySet())
			cmp.setInteger(s, stats.get(s));
	}
}
