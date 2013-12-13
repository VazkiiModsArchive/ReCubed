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
import java.util.Collection;
import java.util.HashMap;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public final class Category implements Serializable {

	public final HashMap<String, PlayerCategoryData> playerData = new HashMap();
	public final String name;
	
	public Category(String name) {
		this.name = name;
	}
	
	public void loadFromNBT(NBTTagCompound cmp) {
		Collection<NBTBase> tags = cmp.getTags();
		for(NBTBase nbt : tags) {
			String name = nbt.getName();
			if(cmp.hasKey(name)) {
				NBTTagCompound cmp1 = cmp.getCompoundTag(name);
				if(!playerData.containsKey(name))
					playerData.put(name, new PlayerCategoryData(name));
				
				playerData.get(name).loadFromNBT(cmp1);
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound cmp) {
		for(String s : playerData.keySet()) {
			NBTTagCompound cmp1 = new NBTTagCompound();
			playerData.get(s).writeToNBT(cmp1);
			cmp.setCompoundTag(s, cmp1);
		}
	}
	
}
