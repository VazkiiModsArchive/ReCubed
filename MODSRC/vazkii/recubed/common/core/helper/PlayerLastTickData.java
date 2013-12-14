/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 14, 2013, 5:12:41 PM (GMT)]
 */
package vazkii.recubed.common.core.helper;

import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.common.lib.LibCategories;
import net.minecraft.entity.player.EntityPlayer;

public final class PlayerLastTickData {

	int xp;
	
	public void tickPlayer(EntityPlayer player) {
		if(player.experienceTotal > xp) {
			int extra = player.experienceTotal - xp;
			ReCubedAPI.addValueToCategory(LibCategories.EXPERIENCE_GATHERED, player.username, "recubed.misc.experience", extra);
		}
		
		setData(player);
	}
	
	public void setData(EntityPlayer player) {
		xp = player.experienceTotal;
	}
	
}
